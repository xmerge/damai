package com.xmerge.idempotent.core.handler;

import com.xmerge.idempotent.annotation.Idempotent;
import com.xmerge.idempotent.core.wrapper.IdempotentParamWrapper;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 幂等执行处理器抽象类
 * @author Xmerge
 */
public abstract class AbstractIdempotentExecuteHandler implements IdempotentExecuteHandler {

    /**
     * 构建幂等验证过程中所需要的参数包装器
     * @param joinPoint  AOP 切点
     */
    protected abstract IdempotentParamWrapper buildWrapper(ProceedingJoinPoint joinPoint);

    /**
     * 处理幂等逻辑
     */
    public void execute(ProceedingJoinPoint joinPoint, Idempotent idempotent) {
        IdempotentParamWrapper wrapper = buildWrapper(joinPoint).setIdempotent(idempotent);
        handle(wrapper);
    }
}
