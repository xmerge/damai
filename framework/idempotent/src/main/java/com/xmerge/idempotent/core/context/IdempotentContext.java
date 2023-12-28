package com.xmerge.idempotent.core.context;

import org.redisson.api.RLock;


/**
 * @author Xmerge
 */
public final class IdempotentContext {

    private static final ThreadLocal<RLock> CONTEXT = new ThreadLocal<>();

    /**
     * 获取上下文
     */
    public static RLock getContext() {
        return CONTEXT.get();
    }

    public static RLock getLock() {
        RLock context = getContext();
        if (context != null) {
            return CONTEXT.get();
        }
        return null;
    }


    /**
     * 保存上下文
     */
    public static void putContext(RLock lock) {
        RLock context = getContext();
        if (context != null) {
            context = lock;
        }
        put(lock);
    }

    /**
     * 保存上下文
     */
    private static void put(RLock context) {
        RLock threadContext = CONTEXT.get();
        if (threadContext != null) {
            return;
        }
        CONTEXT.set(context);
    }

    public static void clean() {
        CONTEXT.remove();
    }
}
