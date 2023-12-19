package com.xmerge.cache.config;

import com.xmerge.cache.proxy.StringRedisTemplateProxy;
import lombok.AllArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Xmerge
 */
@AllArgsConstructor
@EnableConfigurationProperties({BloomFilterProperties.class})
@Configuration
public class CacheAutoConfiguration {

    /**
     * 布隆过滤器，防止缓存穿透
     * @return 布隆过滤器Bean
     */
    @Bean
//    @ConditionalOnProperty(prefix = BloomFilterProperties.BLOOM_FILTER_PREFIX, name = "enable", havingValue = "true")
    public RBloomFilter<String> bloomFilter(RedissonClient redissonClient, BloomFilterProperties bloomFilterPenetrateProperties) {
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter(bloomFilterPenetrateProperties.getName());
        cachePenetrationBloomFilter.tryInit(bloomFilterPenetrateProperties.getExpectedInsertions(), bloomFilterPenetrateProperties.getFalseProbability());
        return cachePenetrationBloomFilter;
    }

    /**
     * StringRedisTemplate代理，增加布隆过滤器等功能
     * @return 代理类Bean
     */
    @Bean
    StringRedisTemplateProxy stringRedisTemplateProxy(StringRedisTemplate stringRedisTemplate, RedissonClient redissonClient) {
        return new StringRedisTemplateProxy(
                stringRedisTemplate, redissonClient
        );
    }

}
