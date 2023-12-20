package com.xmerge.convention.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Xmerge
 */
@AllArgsConstructor
@Getter
public enum ClientErrorCode implements IErrorCode {

    BASE_ERROR("C10000", "客户端错误"),

    USERNAME_EXIST("C10001", "用户名已存在"),
    USERNAME_NOT_EXIST("C10002", "用户名不存在"),
    USERNAME_OR_PASSWORD_ERROR("C10003", "用户名或密码错误"),
    USERNAME_OR_PASSWORD_LENGTH("C10004", "用户名或密码长度不符合要求"),
    USERNAME_OR_PASSWORD_FORMAT("C10005", "用户名或密码格式不符合要求"),
    USERNAME_OR_PASSWORD_EMPTY("C10006", "用户名或密码不能为空"),

    PHONE_EXIST("C10007", "手机号已存在"),
    PHONE_NOT_EXIST("C10008", "手机号不存在"),
    PHONE_OR_PASSWORD_ERROR("C10009", "手机号或密码错误"),
    PHONE_OR_PASSWORD_NULL("C10010", "手机号或密码不能为空"),
    PHONE_OR_PASSWORD_LENGTH("C10011", "手机号或密码长度不符合要求"),
    PHONE_OR_PASSWORD_FORMAT("C10012", "手机号或密码格式不符合要求"),
    PHONE_OR_PASSWORD_EMPTY("C10013", "手机号或密码不能为空"),

    EMAIL_EXIST("C10014", "邮箱已存在"),
    EMAIL_NOT_EXIST("C10015", "邮箱不存在"),
    EMAIL_OR_PASSWORD_ERROR("C10016", "邮箱或密码错误"),
    EMAIL_OR_PASSWORD_LENGTH("C10017", "邮箱或密码长度不符合要求"),
    EMAIL_OR_PASSWORD_NULL("C10018", "邮箱或密码不能为空");

    private final String code;
    private final String message;
}
