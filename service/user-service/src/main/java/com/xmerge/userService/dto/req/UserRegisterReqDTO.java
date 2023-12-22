package com.xmerge.userService.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册请求DTO
 * @author Xmerge
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterReqDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 地区
     */
    private String region;

    /**
     * 证件类型
     */
    private Integer idType;

    /**
     * 证件号码
     */
    private String idCard;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 用户类型
     */
    private Integer userType;
}
