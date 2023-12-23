package com.xmerge.userService.service.impl;

import com.xmerge.userService.dao.entity.UserDO;
import com.xmerge.userService.dao.mapper.UserMapper;
import com.xmerge.userService.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Xmerge
 * @since 2023-12-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

}
