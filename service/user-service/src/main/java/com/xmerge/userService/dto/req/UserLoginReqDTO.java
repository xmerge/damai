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
public class UserLoginReqDTO {

    /**
    * 用户名
    */
    private String username;

    /**
    * 密码
    */
    private String password;
}
