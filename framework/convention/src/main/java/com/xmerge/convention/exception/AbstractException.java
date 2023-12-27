package com.xmerge.convention.exception;

import com.xmerge.convention.exception.errorcode.IErrorCode;
import lombok.Getter;

/**
 * 业务异常基类
 * 客户端异常 {@link ClientException}
 * 服务端异常 {@link ServerException}
 * @author Xmerge
 */
@Getter
public abstract class AbstractException extends RuntimeException {

    /** 错误码 */
    public final String errorCode;
    /** 错误信息 */
    public final String errorMessage;

    public AbstractException(Throwable throwable, IErrorCode errorCode, String message) {
        super(message, throwable);
        this.errorCode = errorCode.getCode();
        this.errorMessage = message;
    }

}
