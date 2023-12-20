package com.xmerge.service.service;

import com.xmerge.convention.result.Result;
import com.xmerge.service.dao.entity.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xmerge.service.dto.req.UserRegisterReqDTO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Xmerge
 * @since 2023-12-19
 */
public interface UserService extends IService<UserDO> {

    String myGet(String key);
    <T> T testGet(String key, Class<T> clazz);

    void testSet(String key, Object value);

    Result<UserDO> register(UserRegisterReqDTO userRegisterReqDTO);

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserDO getByUsername(String username);

    boolean hasUsername(String username);
}
