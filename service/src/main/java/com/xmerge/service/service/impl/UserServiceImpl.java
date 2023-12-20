package com.xmerge.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xmerge.cache.proxy.DistributedCache;
import com.xmerge.chainHandler.chain.ChainHandlerContext;
import com.xmerge.convention.result.Result;
import com.xmerge.service.dao.entity.UserDO;
import com.xmerge.service.dao.mapper.UserMapper;
import com.xmerge.service.dto.req.UserRegisterReqDTO;
import com.xmerge.service.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmerge.service.service.convertor.UserConvertor;
import com.xmerge.web.globalResult.GlobalResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RedissonClient redissonClient;
    private final UserMapper userMapper;
    private final UserConvertor userConvertor;
    private final DistributedCache distributedCache;
    private final ChainHandlerContext<UserRegisterReqDTO> userRegisterChainHandlerContext;

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
    public Result<UserDO> register(UserRegisterReqDTO userRegisterReqDTO) {
        // 责任链验证注册信息是否合法
        userRegisterChainHandlerContext.handle("userCheck", userRegisterReqDTO);
        log.info("用户注册校验通过，开始注册");
        UserDO userDO = userConvertor.convert(userRegisterReqDTO);
        boolean res = save(userDO);
        distributedCache.safeSet(userDO.getUsername(), userDO, 600);
        return GlobalResult.success(userDO);
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

    @Override
    public boolean hasUsername(String username) {
        return getByUsername(username) != null;
    }
}
