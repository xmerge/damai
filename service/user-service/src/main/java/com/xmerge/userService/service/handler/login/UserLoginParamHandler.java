package com.xmerge.userService.service.handler.login;

import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.errorcode.ClientErrorCode;
import com.xmerge.userService.constant.UserChainHandlerConstant;
import com.xmerge.userService.dto.req.UserLoginReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 用户登录责任链处理器，校验用户登录参数
 * @author Xmerge
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserLoginParamHandler implements UserLoginChainHandler<UserLoginReqDTO> {


    @Override
    public void handle(UserLoginReqDTO requestParam) {
        if (StringUtils.isBlank(requestParam.getUsername()) || StringUtils.isBlank(requestParam.getPassword())) {
            throw new ClientException(ClientErrorCode.USERNAME_OR_PASSWORD_EMPTY);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
