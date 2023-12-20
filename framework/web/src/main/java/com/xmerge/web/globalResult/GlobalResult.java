package com.xmerge.web.globalResult;

import com.xmerge.convention.exception.AbstractException;
import com.xmerge.convention.exception.ServerException;
import com.xmerge.convention.exception.errorcode.ServerErrorCode;
import com.xmerge.convention.result.Result;

/**
 * @author Xmerge
 */
public final class GlobalResult {
    /**
     * 构造成功响应（无响应数据）
     */
    public static Result<Void> success() {
        return new Result<Void>().setCode(Result.SUCCESS_CODE);
    }

    /**
     * 构造成功响应（有响应数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>().setCode(Result.SUCCESS_CODE).setData(data);
    }


    public static Result<Object> failure() {
        return new Result<Object>()
                .setCode(ServerErrorCode.BASE_ERROR.getCode())
                .setMessage(ServerErrorCode.BASE_ERROR.getMessage());
    }
    public static <T> Result<T> failure(AbstractException exception) {
        return new Result<T>()
                .setCode(exception.getErrorCode())
                .setMessage(exception.getErrorMessage());
    }

}
