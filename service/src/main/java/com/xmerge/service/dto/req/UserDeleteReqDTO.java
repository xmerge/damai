package com.xmerge.service.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注销请求DTO
 * @author Xmerge
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDeleteReqDTO {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
