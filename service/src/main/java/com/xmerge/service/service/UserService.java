package com.xmerge.service.service;

import com.xmerge.service.dao.entity.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;

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

    boolean register(UserDO userDO);

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserDO getByUsername(String username);
}
