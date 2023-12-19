package com.xmerge.cache.core;

/**
 * 加载缓存，如果缓存不存在，调用该接口加载缓存
 * @author Xmerge
 */
@FunctionalInterface
public interface CacheLoader<T> {
    T load();
}
