package com.xmerge.idempotent.constant;

/**
 * 幂等类型常量
 * @author Xmerge
 */
public enum IdempotentTypeConstant {

    /**
     * Token 方式
     */
    TOKEN,
    /**
     * Redis 方式
     */
    REDIS,
    /**
     * SpEL 表达式方式
     */
    SPEL,

    /**
     * 基于方法参数方式验证
     */
    PARAM;
}
