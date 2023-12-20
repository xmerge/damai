package com.xmerge.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xmerge.cache.proxy.DistributedCache;
import com.xmerge.chainHandler.chain.ChainHandlerContext;
import com.xmerge.service.dao.entity.UserDO;
import com.xmerge.service.dao.mapper.UserMapper;
import com.xmerge.service.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Xmerge
 * @since 2023-12-19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RedissonClient redissonClient;
    private final UserMapper userMapper;
    private final DistributedCache distributedCache;
    private final ChainHandlerContext<UserDO> chainHandlerContext;

    @Override
    public <T> T testGet(String key, Class<T> clazz) {
        return null;
    }

    public String myGet(String key) {
        return redissonClient.getBucket(key).get().toString();
    }

    @Override
    public void testSet(String key, Object value) {
        distributedCache.safeSet(key, value, 1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(UserDO userDO) {
        // 责任链验证注册信息是否合法
        chainHandlerContext.handle("register", userDO);
        if (hasUsername(userDO.getUsername())) {
            System.out.println("用户名已存在");
            return false;
        }

        boolean res = save(userDO);
        distributedCache.safeSet(userDO.getUsername(), userDO, 600);
        return res;
    }

    @Override
    public UserDO getByUsername(String username) {
        return distributedCache.safeGet(
                username,
                UserDO.class,
                () -> {
                    LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUsername, username);
                    return userMapper.selectOne(queryWrapper);
                },
                600);
    }

    public boolean hasUsername(String username) {
        return getByUsername(username) != null;
    }
}
