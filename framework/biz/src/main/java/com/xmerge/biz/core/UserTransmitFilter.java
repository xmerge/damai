package com.xmerge.biz.core;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import com.xmerge.base.constant.UserConstant;
import com.xmerge.biz.dto.UserInfoDTO;
import com.xmerge.biz.util.JwtUtil;
import com.xmerge.cache.proxy.DistributedCache;
import com.xmerge.convention.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * 用户信息传输过滤器，用户用户上下文实体类传输过滤
 * @author Xmerge
 */
@Slf4j
@Component
public class UserTransmitFilter implements Filter {

    @Resource
    DistributedCache distributedCache;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 拦截请求，检查用户信息
     * @param servletRequest 请求
     * @param servletResponse 响应
     * @param filterChain 过滤器链
     * @throws IOException IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        String accessToken = httpServletRequest.getHeader(UserConstant.USER_TOKEN_KEY);
        log.info("拦截请求，检查用户信息");
        if (StringUtils.hasText(accessToken)) {
            try {
                // 两种方法:直接从Redis获取或者parseToken
                // 方法1:parseToken,但这样无法正确处理用户取消登录情况,需要额外引入黑名单
                // UserInfoDTO userInfoDTO = JwtUtil.parseAccessToken(accessToken);
                // 方法2:直接从Redis获取,这样会造成更多的性能开销,但是可以正确处理用户取消登录情况
                UserInfoDTO userInfoDTO = distributedCache.get(UserConstant.USER_TOKEN_KEY + accessToken, UserInfoDTO.class);
                if (userInfoDTO != null) {
                    userInfoDTO.setAccessToken(accessToken);
                    UserInfoContext.setUser(userInfoDTO);
                }
            } catch (ClientException e) {
                log.warn("解析用户 Token 失败");
                distributedCache.delete(UserConstant.USER_TOKEN_KEY + accessToken);
            }
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserInfoContext.clear();
        }
    }

    @Override
    public void destroy() {

    }
}
