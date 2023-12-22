package com.xmerge.biz.core;

import com.xmerge.biz.dto.UserInfoDTO;

import java.util.Optional;

/**
 * 用户信息上下文，用于存储用户信息
 * 登录后前端请求中带有Token时会解析用户信息，将用户信息存储至用户信息上下文中
 * @author Xmerge
 */
public final class UserInfoContext {

    private static final ThreadLocal<UserInfoDTO> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置用户至上下文
     * @param user 用户详情信息
     */
    public static void setUser(UserInfoDTO user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取用户上下文信息
     */
    public static UserInfoDTO getUser() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 获取上下文中用户 ID
     * @return 用户 ID
     */
    public static String getUserId() {
        UserInfoDTO userInfoDTO = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDTO).map(UserInfoDTO::getUserId).orElse(null);
    }

    /**
     * 获取上下文中用户名
     * @return 用户名
     */
    public static String getUsername() {
        UserInfoDTO userInfoDTO = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDTO).map(UserInfoDTO::getUsername).orElse(null);
    }

    /**
     * 获取上下文中用户真实姓名
     * @return 用户真实姓名
     */
    public static String getRealName() {
        UserInfoDTO userInfoDTO = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDTO).map(UserInfoDTO::getRealName).orElse(null);
    }

    /**
     * 获取上下文中用户 Token
     * @return 用户Token
     */
    public static String getAccessToken() {
        UserInfoDTO userInfoDTO = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDTO).map(UserInfoDTO::getAccessToken).orElse(null);
    }

    /**
     * 清除用户信息
     */
    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }
}
