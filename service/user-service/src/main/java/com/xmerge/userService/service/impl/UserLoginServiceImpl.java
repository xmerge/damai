package com.xmerge.userService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xmerge.base.constant.UserConstant;
import com.xmerge.biz.core.UserInfoContext;
import com.xmerge.cache.proxy.DistributedCache;
import com.xmerge.chainHandler.chain.ChainHandlerContext;
import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.ServerException;
import com.xmerge.convention.exception.errorcode.ClientErrorCode;
import com.xmerge.convention.exception.errorcode.ServerErrorCode;
import com.xmerge.convention.result.Result;
import com.xmerge.userService.constant.UserChainHandlerConstant;
import com.xmerge.userService.dao.entity.UserDO;
import com.xmerge.userService.dao.mapper.UserMapper;
import com.xmerge.userService.dto.req.UserDeleteReqDTO;
import com.xmerge.userService.dto.req.UserLoginReqDTO;
import com.xmerge.userService.dto.req.UserRegisterReqDTO;
import com.xmerge.userService.dto.resp.UserLoginRespDTO;
import com.xmerge.userService.dto.resp.UserRegisterRespDTO;
import com.xmerge.userService.service.UserLoginService;
import com.xmerge.userService.constant.UserRedisKeyConstant;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmerge.userService.service.convertor.UserConvertor;
import com.xmerge.biz.util.JwtUtil;
import com.xmerge.biz.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
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
    private final ChainHandlerContext<UserLoginReqDTO> userLoginChainHandlerContext;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserRegisterRespDTO register(UserRegisterReqDTO userRegisterReqDTO) {
        // 责任链验证注册信息是否合法
        userRegisterChainHandlerContext.handle(UserChainHandlerConstant.REGISTER_PARAM, userRegisterReqDTO);
        RLock lock = redissonClient.getLock(UserRedisKeyConstant.USER_REGISTER + userRegisterReqDTO.getUsername());
        try {
            // 尝试拿锁，超时时间一秒
            boolean isLock = lock.tryLock(1000, TimeUnit.MILLISECONDS);
            if (isLock) {
                // 拿到锁后再次判断用户名是否存在，因为可能在等待锁的过程中，该用户名已经被注册
                if (hasUsername(userRegisterReqDTO.getUsername())) {
                    throw new ClientException(ClientErrorCode.USERNAME_EXIST);
                }
                // 对象转换
                UserDO userDO = userConvertor.toUserDO(userRegisterReqDTO);
                try {
                    boolean res = save(userDO); // 将用户信息持久化存储到MySQL
                    if (res) {
                        distributedCache.safeSet(userDO.getUsername(), userDO, 300, TimeUnit.SECONDS); // 在分布式缓存中存入信息,过期时间五分钟
                        userLoginBloomFilter.add(userDO.getUsername()); // 将用户名存入布隆过滤器
                        return userConvertor.toUserRegisterRespDTO(userRegisterReqDTO);
                    }
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
    public UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO) {
        userLoginChainHandlerContext.handle(UserChainHandlerConstant.LOGIN_PARAM, userLoginReqDTO);
        String username = userLoginReqDTO.getUsername();
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getPassword, userLoginReqDTO.getPassword());
        UserDO userDO = userMapper.selectOne(queryWrapper);
        if (userDO!= null) {
            UserLoginRespDTO userLoginRespDTO = userConvertor.toUserLoginRespDTO(userDO);
            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                    .userId(String.valueOf(userDO.getUserId()))
                    .username(userDO.getUsername())
                    .realName(userDO.getRealName())
                    .build();
            String accessToken = JwtUtil.generateAccessToken(userInfoDTO);
            userLoginRespDTO.setAccessToken(accessToken);
            distributedCache.set(UserConstant.USER_TOKEN_KEY + accessToken, userInfoDTO, 7, TimeUnit.DAYS);
            UserInfoDTO testDTO = distributedCache.get(UserConstant.USER_TOKEN_KEY + accessToken, UserInfoDTO.class);
            log.info("testDTO:{}", testDTO);
            return userLoginRespDTO;
        }
        throw new ClientException(ClientErrorCode.USERNAME_OR_PASSWORD_ERROR);
    }

    @Override
    public UserInfoDTO checkLogin() {
        UserInfoDTO userInfoDTO = UserInfoContext.getUser();
        if (ObjectUtils.isNull(userInfoDTO)) {
            throw new ClientException(ClientErrorCode.LOGIN_NOT_EXIST);
        }
        log.info("用户上下文信息：{}", userInfoDTO);
        return userInfoDTO;
    }

    @Override
    public boolean logout() {
        String accessToken = UserInfoContext.getAccessToken();
        boolean res = distributedCache.delete(UserConstant.USER_TOKEN_KEY + accessToken);
        if (res) {
            return true;
        }
        throw new ClientException(ClientErrorCode.LOGIN_NOT_EXIST);
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
        // 如果布隆过滤器检查不存在，则直接返回false
        // 布隆过滤器判断不存在则一定不存在
        if (!userLoginBloomFilter.contains(username)) {
            log.info("userLoginBloomFilter检查不存在，避免缓存穿透");
            return false;
        }
        // 如果布隆过滤器检查存在，则再次检查分布式缓存和数据库中是否存在
        return getByUsername(username) != null;
    }
}
