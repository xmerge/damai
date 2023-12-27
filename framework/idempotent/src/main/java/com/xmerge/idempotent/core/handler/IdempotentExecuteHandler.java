package com.xmerge.idempotent.core.handler;

import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.errorcode.ClientErrorCode;
import com.xmerge.idempotent.annotation.Idempotent;
import com.xmerge.idempotent.core.context.IdempotentContext;
import com.xmerge.idempotent.core.wrapper.IdempotentParamWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Xmerge
 */
public interface IdempotentExecuteHandler {

    /**
     * 处理幂等逻辑
     */
    void handle(IdempotentParamWrapper idempotentParamWrapper);

    /**
    * 执行幂等逻辑
    * @param joinPoint  AOP 方法处理
    * @param idempotent 幂等注解
    */
    void execute(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable;

    /**
     * 异常流程处理
     */
    default void exceptionProcessing(Idempotent idempotent) {
        throw new ClientException(idempotent.message());
//        System.out.println("幂等异常处理");
    }

    /**
     * 后置处理
     */
    default void postProcessing() {
        IdempotentContext.clean();
    }
}
