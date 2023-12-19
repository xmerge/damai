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

    <T> T safeGet(String key, Class<T> clazz, CacheLoader<T> cacheLoader, long timeout);

    void safeSet(String key, Object value, long timeout);

}
