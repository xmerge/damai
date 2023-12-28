package com.xmerge.web.globalException;

import com.xmerge.convention.exception.AbstractException;
import com.xmerge.convention.result.Result;
import com.xmerge.web.globalResult.GlobalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Xmerge
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 拦截抽象类异常
     * @param request 请求
     * @param exception 异常
     * @return 错误结果
     */
    @ExceptionHandler(AbstractException.class)
    public Result<Object> abstractExceptionHandler(HttpServletRequest request, AbstractException exception) {
        // log
        exception.getCause();
        log.warn("捕获异常，请求地址: {} 异常信息: {}", request.getRequestURI(), exception.toString());
        return GlobalResult.failure(exception);
    }

    @ExceptionHandler(value = Throwable.class)
    public Result<Object> defaultErrorHandler(HttpServletRequest request, Throwable throwable) {
        log.warn("[{}] {} ", request.getMethod(), getUrl(request), throwable);
        return GlobalResult.failure();
    }

    private String getUrl(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getQueryString())) {
            return request.getRequestURL().toString();
        }
        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }
}
