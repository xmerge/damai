package com.xmerge.idempotent.core.context;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Xmerge
 */
public final class IdempotentContext {

    private static final ThreadLocal<Map<String, Object>> CONTEXT = new ThreadLocal<>();

    /**
     * 获取上下文
     */
    public static Map<String, Object> getContext() {
        return CONTEXT.get();
    }

    public static Object getKey(String key) {
        Map<String, Object> context = getContext();
        if (context != null) {
            return context.get(key);
        }
        return null;
    }


    /**
     * 保存上下文
     */
    public static void putContext(String key, Object val) {
        Map<String, Object> context = getContext();
        if (CollUtil.isEmpty(context)) {
            context = Maps.newHashMap();
        }
        context.put(key, val);
        put(context);
    }

    /**
     * 保存上下文
     */
    private static void put(Map<String, Object> context) {
        Map<String, Object> threadContext = CONTEXT.get();
        if (CollUtil.isNotEmpty(threadContext)) {
            threadContext.putAll(context);
            return;
        }
        CONTEXT.set(context);
    }

    public static void clean() {
        CONTEXT.remove();
    }
}
