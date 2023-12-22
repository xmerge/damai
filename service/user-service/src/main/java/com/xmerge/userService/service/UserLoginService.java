package com.xmerge.userService.service;

import com.xmerge.biz.dto.UserInfoDTO;
import com.xmerge.convention.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xmerge.userService.dao.entity.UserDO;
import com.xmerge.userService.dto.req.UserDeleteReqDTO;
import com.xmerge.userService.dto.req.UserLoginReqDTO;
import com.xmerge.userService.dto.req.UserRegisterReqDTO;
import com.xmerge.userService.dto.resp.UserLoginRespDTO;
import com.xmerge.userService.dto.resp.UserRegisterRespDTO;

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
    UserRegisterRespDTO register(UserRegisterReqDTO userRegisterReqDTO);

    /**
     * 用户登录
     */
    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);

    /*
    * 检查登录状态
     */
    UserInfoDTO checkLogin();

    /**
     * 退出登录
     */
    boolean logout();

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
