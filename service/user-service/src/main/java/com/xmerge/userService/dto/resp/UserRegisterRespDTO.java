package com.xmerge.userService.dto.resp;

import lombok.Data;

/**
 * 用户注册响应参数
 * @author Xmerge
 */
@Data
public class UserRegisterRespDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phoneNumber;
}
