package com.xmerge.convention.exception;

import com.xmerge.convention.exception.errorcode.IErrorCode;

/**
 * 服务端异常
 * @author Xmerge
 */
public class ServerException extends AbstractException {

    public ServerException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }
    public ServerException(String message) {
        this(message, null, null);
    }
    public ServerException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }
    public ServerException(String message, Throwable throwable, IErrorCode errorCode) {
        super(throwable, errorCode, message);
    }

    @Override
    public String toString() {
        return "ServerException: {" +
                "code: '" + errorCode + "'," +
                "message: '" + errorMessage + "'" +
                '}';
    }
}
