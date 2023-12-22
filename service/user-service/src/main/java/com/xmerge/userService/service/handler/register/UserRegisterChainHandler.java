package com.xmerge.userService.service.handler.register;

import com.xmerge.chainHandler.chain.IChainHandler;
import com.xmerge.userService.constant.UserChainHandlerConstant;

/**
 * @author Xmerge
 */
public interface UserRegisterChainHandler<T> extends IChainHandler<T> {
    @Override
    default String mark() {
        return UserChainHandlerConstant.REGISTER_PARAM;
    }
}
