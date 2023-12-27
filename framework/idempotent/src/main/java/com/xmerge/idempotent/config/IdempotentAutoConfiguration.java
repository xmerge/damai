package com.xmerge.idempotent.config;

import com.xmerge.idempotent.core.aspect.IdempotentAspect;
import com.xmerge.idempotent.core.handler.param.IdempotentParamExecuteHandler;
import com.xmerge.idempotent.core.handler.param.IdempotentParamService;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xmerge
 */
@Configuration
public class IdempotentAutoConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect() {
        return new IdempotentAspect();
    }

    /**
     * 参数方式幂等实现，基于 RestAPI 场景
     */
    @Bean
    @ConditionalOnMissingBean
    public IdempotentParamService idempotentParamExecuteHandler(RedissonClient redissonClient) {
        return new IdempotentParamExecuteHandler(redissonClient);
    }

}
