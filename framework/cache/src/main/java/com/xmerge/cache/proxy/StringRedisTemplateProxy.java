package com.xmerge.cache.proxy;

import com.alibaba.fastjson2.JSON;
import com.xmerge.cache.core.CacheLoader;
import com.xmerge.cache.util.CacheUtil;
import com.xmerge.cache.util.FastJson2Util;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;


/**
 * @author Xmerge
 */
@RequiredArgsConstructor
public class StringRedisTemplateProxy implements DistributedCache {

    @Autowired
    RBloomFilter<String> cachePenetrationBloomFilter;

    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;

    private static final String SAFE_GET_DISTRIBUTED_LOCK_KEY_PREFIX = "safe_get_distributed_lock_get:";

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        T result;
        String value = stringRedisTemplate.opsForValue().get(key);
        if (String.class.isAssignableFrom(clazz)) {
            result = (T) value;
        } else {
            result = JSON.parseObject(value, FastJson2Util.buildType(clazz));
        }
        return result;
    }

    @Override
    public void set(String key, Object value, long timeout) {
        String actual = value instanceof String ? (String) value : JSON.toJSONString(value);
        stringRedisTemplate.opsForValue().set(key, actual, timeout, TimeUnit.SECONDS);

    }

    /**
     * 安全获取缓存，加锁避免缓存击穿，布隆过滤器验证避免缓存穿透
     * @param key key
     * @param clazz 查询类型
     * @param cacheLoader 缓存加载器函数
     * @param timeout 超时时间
     * @return 缓存结果
     */
    @Override
    public <T> T safeGet(String key, Class<T> clazz, CacheLoader<T> cacheLoader, long timeout) {
        T res = get(key, clazz);
        // 缓存结果非空则返回，否则加锁并读取数据库
        if (!CacheUtil.isNullOrBlank(res)) {
            System.out.println("缓存命中"); //
            return res;
        }
        // 加锁保证只能有一个线程进行数据库访问
        RLock lock = redissonClient.getLock(SAFE_GET_DISTRIBUTED_LOCK_KEY_PREFIX + key);
        try {
            if (lock.tryLock(0, 10, TimeUnit.SECONDS)) { // 尝试获取锁，最多等待10秒
                try {
                    // 双重判定
                    res = get(key, clazz);
                    // 布隆过滤器检查
                    if (!cachePenetrationBloomFilter.contains(key)) {
                        System.out.println("缓存未命中，布隆过滤器检查不通过，避免缓存穿透");
                        return null;
                    }
                    if (CacheUtil.isNullOrBlank(res)) {
                        System.out.println("缓存未命中，布隆过滤器检查通过，进行数据库查询");
                        res = loadAndSet(key, cacheLoader, timeout, true);
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return res;
    }

    @Override
    public void safeSet(String key, Object value, long timeout) {
        set(key, value, timeout);
        if (cachePenetrationBloomFilter != null) {
            cachePenetrationBloomFilter.add(key);
        }
    }

    /**
     * 从数据库读取数据，写入缓存
     * @param key key
     * @param cacheLoader cacheLoader 缓存加载器
     * @param timeout 超时时间
     * @param safeFlag 是否安全模式
     * @return 数据库读取结果
     */
    private <T> T loadAndSet(String key, CacheLoader<T> cacheLoader, long timeout, boolean safeFlag) {
        T res = cacheLoader.load();
        // 数据库读取结果为空，直接返回
        if (CacheUtil.isNullOrBlank(res)) {
            return res;
        }
        // 从数据库读到结果，写入缓存
        if (safeFlag) {
            safeSet(key, res, timeout);
        } else {
            set(key, res, timeout);
        }
        return res;
    }
}
