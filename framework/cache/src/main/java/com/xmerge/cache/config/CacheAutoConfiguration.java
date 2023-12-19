package com.xmerge.cache.config;

import com.xmerge.cache.proxy.StringRedisTemplateProxy;
import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Xmerge
 */
@AllArgsConstructor
@Configuration
public class CacheAutoConfiguration {

    /**
     * StringRedisTemplate代理，增加布隆过滤器等功能
     * @param stringRedisTemplate redis模板
     * @param redissonClient      redisson客户端
     * @return 代理类
     */
    @Bean
    StringRedisTemplateProxy stringRedisTemplateProxy(StringRedisTemplate stringRedisTemplate, RedissonClient redissonClient) {
        return new StringRedisTemplateProxy(
                stringRedisTemplate, redissonClient
        );
    }

}
