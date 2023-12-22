package com.xmerge.userService.service.handler.login;

import com.xmerge.chainHandler.chain.IChainHandler;
import com.xmerge.userService.constant.UserChainHandlerConstant;

/**
 * @author Xmerge
 */
public interface UserLoginChainHandler<T> extends IChainHandler<T> {
    @Override
    default String mark() {
        return UserChainHandlerConstant.LOGIN_PARAM;
    }
}
