package com.xmerge.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xmerge.cache.proxy.DistributedCache;
import com.xmerge.chainHandler.chain.ChainHandlerContext;
import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.ServerException;
import com.xmerge.convention.exception.errorcode.ClientErrorCode;
import com.xmerge.convention.exception.errorcode.ServerErrorCode;
import com.xmerge.convention.result.Result;
import com.xmerge.service.dao.entity.UserDO;
import com.xmerge.service.dao.mapper.UserMapper;
import com.xmerge.service.dto.req.UserDeleteReqDTO;
import com.xmerge.service.dto.req.UserLoginReqDTO;
import com.xmerge.service.dto.req.UserRegisterReqDTO;
import com.xmerge.service.service.UserLoginService;
import com.xmerge.service.constant.RedisKeyConstant;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmerge.service.service.convertor.UserConvertor;
import com.xmerge.web.globalResult.GlobalResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.api.RLock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


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
public class UserLoginServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserLoginService {

    private final UserMapper userMapper;
    private final UserConvertor userConvertor;
    private final RedissonClient redissonClient;
    private final RBloomFilter<String> userLoginBloomFilter;
    private final DistributedCache distributedCache;
    private final ChainHandlerContext<UserRegisterReqDTO> userRegisterChainHandlerContext;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<UserDO> register(UserRegisterReqDTO userRegisterReqDTO) {
        // 责任链验证注册信息是否合法
        userRegisterChainHandlerContext.handle("userCheck", userRegisterReqDTO);
        RLock lock = redissonClient.getLock(RedisKeyConstant.USER_REGISTER + userRegisterReqDTO.getUsername());
        try {
            boolean isLock = lock.tryLock(1, TimeUnit.SECONDS);
            if (isLock) {
                // 拿到锁后再次判断用户名是否存在，因为可能在等待锁的过程中，该用户名已经被注册
                if (hasUsername(userRegisterReqDTO.getUsername())) {
                    throw new ClientException(ClientErrorCode.USERNAME_EXIST);
                }
                // 对象转换
                UserDO userDO = userConvertor.convert(userRegisterReqDTO);
                try {
                    boolean res = save(userDO); // 将用户信息持久化存储到MySQL
                    distributedCache.safeSet(userDO.getUsername(), userDO, 300, TimeUnit.SECONDS); // 在分布式缓存中存入信息,过期时间五分钟
                    return GlobalResult.success(userDO);
                } catch (Exception e) { // 数据库操作失败
                    log.error("注册失败", e);
                    throw new ServerException(ServerErrorCode.BASE_ERROR);
                }
            }
        } catch (InterruptedException e) {
            // 没有拿到锁,该用户名已经注册
            throw new ClientException(ClientErrorCode.USERNAME_EXIST);
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
        throw new ServerException(ServerErrorCode.BASE_ERROR);
    }

    @Override
    public Result<UserDO> login(UserLoginReqDTO userLoginReqDTO) {
        return null;
    }

    @Override
    public Result<String> logout(String accessToken) {
        return null;
    }

    @Override
    public Result<String> deleteUser(UserDeleteReqDTO userDeleteReqDTO) {
        return null;
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
