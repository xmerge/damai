package com.xmerge.idempotent.core.aspect;

import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.errorcode.ClientErrorCode;
import com.xmerge.idempotent.annotation.Idempotent;
import com.xmerge.idempotent.core.factory.IdempotentExecuteHandlerFactory;
import com.xmerge.idempotent.core.handler.IdempotentExecuteHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author Xmerge
 */
@Aspect
@Slf4j
public final class IdempotentAspect {

    @Around("@annotation(com.xmerge.idempotent.annotation.Idempotent)")
    public Object idempotentHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        Idempotent idempotent = getIdempotent(joinPoint);
        IdempotentExecuteHandler instance = IdempotentExecuteHandlerFactory.getInstance(idempotent.scene(), idempotent.type());
        Object resultObj = null;
        try {
            // 执行幂等处理逻辑
            instance.execute(joinPoint, idempotent);
            // 执行业务逻辑(幂等处理成功后才执行业务逻辑)
            resultObj = joinPoint.proceed();
        } catch (Throwable ex) {
//            log.warn("幂等处理异常", ex);
            instance.exceptionProcessing(idempotent);
        } finally {
            instance.postProcessing();
        }
        return resultObj;
    }

    /**
     * 获取幂等注解
     * @param joinPoint 切点
     * @return 幂等注解
     * @throws NoSuchMethodException 无法获取方法异常
     */
    public static Idempotent getIdempotent(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());
        return targetMethod.getAnnotation(Idempotent.class);
    }
}
