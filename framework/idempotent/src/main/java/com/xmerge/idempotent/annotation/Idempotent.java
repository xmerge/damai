package com.xmerge.idempotent.annotation;

import com.xmerge.idempotent.constant.IdempotentSceneConstant;
import com.xmerge.idempotent.constant.IdempotentTypeConstant;

import java.lang.annotation.*;

/**
 * 幂等注解
 * 接口幂等、消息队列幂等
 * @author Xmerge
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {
    /**
     * 幂等Key，只有在 {@link Idempotent#type()} 为 {@link IdempotentTypeConstant#SPEL} 时生效
     */
    String key() default "";

    /**
     * 触发幂等失败逻辑时，返回的错误提示信息
     */
    String message() default "您操作太快，请稍后再试";

    /**
     * 验证幂等类型，支持多种幂等方式
     * RestAPI 建议使用 {@link IdempotentTypeConstant#TOKEN} 或 {@link IdempotentTypeConstant#PARAM}
     * 其它类型幂等验证，使用 {@link IdempotentTypeConstant#SPEL}
     */
    IdempotentTypeConstant type() default IdempotentTypeConstant.PARAM;

    /**
     * 验证幂等场景，支持多种 {@link IdempotentSceneConstant}
     */
    IdempotentSceneConstant scene() default IdempotentSceneConstant.RESTAPI;

    /**
     * 设置防重令牌 Key 前缀，MQ 幂等去重可选设置
     * {@link IdempotentSceneConstant#MQ} and {@link IdempotentTypeConstant#SPEL} 时生效
     */
    String uniqueKeyPrefix() default "";

    /**
     * 设置防重令牌 Key 过期时间，单位秒，默认 1 小时，MQ 幂等去重可选设置
     * {@link IdempotentSceneConstant#MQ} and {@link IdempotentTypeConstant#SPEL} 时生效
     */
    long keyTimeout() default 3600L;
}
