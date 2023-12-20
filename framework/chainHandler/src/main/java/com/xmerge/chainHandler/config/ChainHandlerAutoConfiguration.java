package com.xmerge.chainHandler.config;

import com.xmerge.chainHandler.chain.ChainHandlerContext;
import com.xmerge.chainHandler.chain.IChainHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xmerge
 */
@Configuration
public class ChainHandlerAutoConfiguration {

    @Bean
    public <T> ChainHandlerContext<T> chainHandlerContext() {
        return new ChainHandlerContext<>();
    }
}
