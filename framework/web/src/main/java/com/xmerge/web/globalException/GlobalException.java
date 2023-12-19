package com.xmerge.web.globalException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Xmerge
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public void test() {
        System.out.println("GlobalException!");
    }
}
