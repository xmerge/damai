package com.xmerge.biz.util;


import com.alibaba.fastjson2.JSON;
import com.xmerge.base.constant.UserConstant;
import com.xmerge.biz.dto.UserInfoDTO;
import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.errorcode.ClientErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xmerge
 */
@Slf4j
public final class JwtUtil {

    /** Token 过期时间 */
    private static final long EXPIRATION = 86400L;
    public static final String TOKEN_PREFIX = "Token";
    public static final String ISS = "damai";
    public static final String SECRET = "SecretKey039245678901232039487623456783092349288901402967890140939827";

    /**
     * 生成用户 Token
     *
     * @param userInfoDTO 用户信息
     * @return 用户访问 Token
     */
    public static String generateAccessToken(UserInfoDTO userInfoDTO) {
        Map<String, Object> customerUserMap = new HashMap<>();
        customerUserMap.put(UserConstant.USER_ID_KEY, userInfoDTO.getUserId());
        customerUserMap.put(UserConstant.USER_NAME_KEY, userInfoDTO.getUsername());
        customerUserMap.put(UserConstant.REAL_NAME_KEY, userInfoDTO.getRealName());
        String jwtToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuedAt(new Date())
                .setIssuer(ISS)
                .setSubject(JSON.toJSONString(customerUserMap))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
        return TOKEN_PREFIX + jwtToken;
    }

    /**
     * 解析用户 Token
     *
     * @param jwtToken 用户访问 Token
     * @return 用户信息
     */
    public static UserInfoDTO parseAccessToken(String jwtToken) {
        if (StringUtils.hasText(jwtToken)) {
            // 去掉 Token 前缀
            String actualJwtToken = jwtToken.replace(TOKEN_PREFIX, "");
            try {
                Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(actualJwtToken).getBody();
                Date expiration = claims.getExpiration();
                if (expiration.after(new Date())) {
                    String subject = claims.getSubject();
                    return JSON.parseObject(subject, UserInfoDTO.class);
                }
            } catch (ExpiredJwtException e) {
                log.warn("JWT Token已过期，请重新登录");
                throw new ClientException(ClientErrorCode.LOGIN_EXPIRED);
            } catch (Exception e) {
                log.warn("JWT Token解析失败，请检查");
                throw new ClientException(ClientErrorCode.LOGIN_TOKEN_ERROR);
            }

        }
        return null;
    }
}