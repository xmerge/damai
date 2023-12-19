package com.xmerge.cache.proxy;

import com.xmerge.cache.core.CacheLoader;


/**
 * @author Xmerge
 */
public interface DistributedCache {


    /**
     * 获取缓存
     * @param key key
     * @param clazz 类型
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * @param key 放入的key
     * @param value key对应value
     */
    void set(String key, Object value, long timeout);

    /**
     * 安全获取缓存，加锁避免缓存击穿，布隆过滤器验证避免缓存穿透
     * @param key key
     * @param clazz 查询类型
     * @param cacheLoader 缓存加载器函数
     * @param timeout 超时时间
     * @return 缓存结果
     */
    <T> T safeGet(String key, Class<T> clazz, CacheLoader<T> cacheLoader, long timeout);

    void safeSet(String key, Object value, long timeout);

}
