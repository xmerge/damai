package com.xmerge.convention.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Xmerge
 */

@Getter
@AllArgsConstructor
public enum ServerErrorCode implements IErrorCode {

    BASE_ERROR("B10000", "服务端异常"),

    SERVICE_TIMEOUT("B10001", "服务超时"),
    SERVICE_ERROR("B10002", "服务异常"),
    SERVICE_NOT_FOUND("B10003", "服务未找到"),
    SERVICE_NOT_IMPLEMENT("B10004", "服务未实现"),
    SERVICE_NOT_SUPPORT("B10005", "服务不支持"),
    SERVICE_NOT_SUPPORT_METHOD("B10006", "服务不支持该方法"),
    ;

    private final String code;
    private final String message;
}
