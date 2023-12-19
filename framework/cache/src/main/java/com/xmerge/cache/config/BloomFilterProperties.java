package com.xmerge.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Xmerge
 */
@Data
@ConfigurationProperties(prefix = BloomFilterProperties.BLOOM_FILTER_PREFIX)
public class BloomFilterProperties {
    public static final String BLOOM_FILTER_PREFIX = "framework.cache.bloom-filter";

    /**
     * 布隆过滤器默认实例名称
     */
    private String name = "bloomFilter";

    /**
     * 每个元素的预期插入量
     */
    private Long expectedInsertions = 64000L;

    /**
     * 预期错误概率
     */
    private Double falseProbability = 0.03D;
}
