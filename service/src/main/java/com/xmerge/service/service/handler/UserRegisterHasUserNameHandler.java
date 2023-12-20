package com.xmerge.service.service.handler;

import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.errorcode.ClientErrorCode;
import com.xmerge.service.dto.req.UserRegisterReqDTO;
import com.xmerge.service.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户注册责任链处理器，校验用户名是否存在
 * @author Xmerge
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegisterHasUserNameHandler implements UserCheckChainHandler<UserRegisterReqDTO> {

    private final UserLoginService userLoginService;

    /**
     * 责任链处理器，执行处理逻辑
     */
    @Override
    public void handle(UserRegisterReqDTO requestParam) {
        String username = requestParam.getUsername();
        if (userLoginService.hasUsername(username)) {
            throw new ClientException(ClientErrorCode.USERNAME_EXIST);
        }
        log.info("用户名校验通过");
    }

    /**
     * 顺序 10
     */
    @Override
    public int getOrder() {
        return 10;
    }
}
