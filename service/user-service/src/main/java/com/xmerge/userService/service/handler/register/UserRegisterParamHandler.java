package com.xmerge.userService.service.handler.register;

import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.errorcode.ClientErrorCode;
import com.xmerge.userService.constant.UserChainHandlerConstant;
import com.xmerge.userService.dto.req.UserRegisterReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 用户注册责任链处理器，校验用户注册参数
 * @author Xmerge
 */
@Component
@Slf4j
public class UserRegisterParamHandler implements UserRegisterChainHandler<UserRegisterReqDTO> {
    @Override
    public void handle(UserRegisterReqDTO requestParam) {
        if (Objects.isNull(requestParam.getUsername()) || Objects.isNull(requestParam.getPassword())) {
            throw new ClientException(ClientErrorCode.USERNAME_OR_PASSWORD_EMPTY);
        } else if (requestParam.getUsername().length() < 6 || requestParam.getUsername().length() > 16) {
            throw new ClientException(ClientErrorCode.USERNAME_OR_PASSWORD_LENGTH);
        } else if (Objects.isNull(requestParam.getPhoneNumber())) {
            throw new ClientException(ClientErrorCode.PHONE_OR_PASSWORD_EMPTY);
        }
        log.info("用户注册参数校验通过");
    }

    /**
     * 顺序 0
     */
    @Override
    public int getOrder() {
        return 0;
    }

}
