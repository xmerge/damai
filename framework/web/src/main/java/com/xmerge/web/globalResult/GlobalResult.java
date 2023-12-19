package com.xmerge.web.globalResult;

import com.xmerge.constant.result.Result;

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
}
