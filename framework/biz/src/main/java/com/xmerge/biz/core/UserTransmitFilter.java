package com.xmerge.biz.core;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

import com.xmerge.base.constant.UserConstant;
import com.xmerge.biz.dto.UserInfoDTO;
import com.xmerge.biz.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 用户信息传输过滤器，用户用户上下文实体类传输过滤
 * @author Xmerge
 */
@Slf4j
public class UserTransmitFilter implements Filter {


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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        String accessToken = httpServletRequest.getHeader(UserConstant.USER_TOKEN_KEY);
        log.info("拦截请求，检查用户信息");
        if (StringUtils.hasText(accessToken)) {
            try {
                UserInfoDTO userInfoDTO = JwtUtil.parseAccessToken(accessToken);
                assert userInfoDTO != null;
                userInfoDTO.setAccessToken(accessToken);
                log.info("用户上下文信息：{}", userInfoDTO);
                UserInfoContext.setUser(userInfoDTO);
            } catch (Exception e) {
                log.info("解析用户 Token 失败");
            }
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            UserInfoContext.clear();
        }
    }

    @Override
    public void destroy() {

    }
}
