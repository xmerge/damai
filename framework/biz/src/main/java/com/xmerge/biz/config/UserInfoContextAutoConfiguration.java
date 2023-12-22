package com.xmerge.biz.config;

import com.xmerge.biz.core.UserTransmitFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.xmerge.base.constant.FilterOrderConstant;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xmerge
 */
@ConditionalOnWebApplication
@Configuration
public class UserInfoContextAutoConfiguration {

    @Bean
    public FilterRegistrationBean<UserTransmitFilter> globalUserTransmitFilter() {
        FilterRegistrationBean<UserTransmitFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new UserTransmitFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(FilterOrderConstant.USER_TRANSMIT_FILTER_ORDER);
        return registration;
    }
}
