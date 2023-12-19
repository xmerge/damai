package com.xmerge.cache.util;

import com.google.common.base.Strings;

public class CacheUtil {
    public static boolean isNullOrBlank(Object cacheVal) {
        return cacheVal == null || (cacheVal instanceof String && Strings.isNullOrEmpty((String) cacheVal));
    }
}
