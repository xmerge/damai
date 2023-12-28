package com.xmerge.idempotent.core.handler.param;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.xmerge.biz.core.UserInfoContext;
import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.errorcode.ClientErrorCode;
import com.xmerge.idempotent.core.context.IdempotentContext;
import com.xmerge.idempotent.core.handler.AbstractIdempotentExecuteHandler;
import com.xmerge.idempotent.core.wrapper.IdempotentParamWrapper;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 幂等参数处理器
 * @author Xmerge
 */
@Slf4j
@RequiredArgsConstructor
public class IdempotentParamExecuteHandler extends AbstractIdempotentExecuteHandler implements IdempotentParamService {

    private final RedissonClient redissonClient;
    private static final String LOCK_KEY_PREFIX = "idempotent:param:";


    @Override
    protected IdempotentParamWrapper buildWrapper(ProceedingJoinPoint joinPoint) {
        String lockKey = String.format("idempotent:path:%s:currentUserId:%s:md5:%s", getServletPath(), getCurrentUserId(), calcArgsMd5(joinPoint));
        return IdempotentParamWrapper.builder().lockKey(lockKey).joinPoint(joinPoint).build();
    }

    @Override
    public void handle(IdempotentParamWrapper idempotentParamWrapper) {
        String lockKey = LOCK_KEY_PREFIX + idempotentParamWrapper.getLockKey();
        RLock lock = redissonClient.getLock(lockKey);
        log.info("尝试上锁 {}", lock.getName());
        if (!lock.tryLock()) {
            throw new ClientException("请勿重复提交");
        }
        IdempotentContext.putContext(lock);
    }


    @Override
    public void postProcessing() {
        RLock lock = null;
        try {
            lock = IdempotentContext.getLock();
        } catch (Exception e) {
            log.error("获取锁失败", e);
        } finally {
            if (lock != null && lock.isLocked()) {
                log.info("解锁 {}", lock.getName());
                lock.unlock();
            }
        }
        IdempotentContext.clean();
    }


    /**
     * 获取当前线程上下文 ServletPath
     */
    private String getServletPath() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            return sra.getRequest().getServletPath();
        }
        return null;
    }

    /**
     * 获取当前操作用户 ID
     */
    private String getCurrentUserId() {
        String userId = UserInfoContext.getUserId();
        if (StringUtil.isBlank(userId)) {
            throw new ClientException(ClientErrorCode.LOGIN_NOT_EXIST);
        }
        return userId;
    }

    /**
     * @return joinPoint md5
     */
    private String calcArgsMd5(ProceedingJoinPoint joinPoint) {
        return DigestUtil.md5Hex(JSON.toJSONBytes(joinPoint.getArgs()));
    }
}
