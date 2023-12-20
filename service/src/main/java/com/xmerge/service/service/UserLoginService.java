package com.xmerge.service.service;

import com.xmerge.convention.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xmerge.service.dao.entity.UserDO;
import com.xmerge.service.dto.req.UserDeleteReqDTO;
import com.xmerge.service.dto.req.UserLoginReqDTO;
import com.xmerge.service.dto.req.UserRegisterReqDTO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Xmerge
 * @since 2023-12-19
 */
public interface UserLoginService extends IService<UserDO> {

    /**
     * 用户注册
     */
    Result<UserDO> register(UserRegisterReqDTO userRegisterReqDTO);

    /**
     * 用户登录
     */
    Result<UserDO> login(UserLoginReqDTO userLoginReqDTO);

    /**
     * 退出登录
     */
    Result<String> logout(String accessToken);

    /**
     * 用户注销
     */
    Result<String> deleteUser(UserDeleteReqDTO userDeleteReqDTO);

    /**
     * 根据用户名获取用户信息
     */
    UserDO getByUsername(String username);

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean hasUsername(String username);
}
