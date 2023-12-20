package com.xmerge.service.service.handler;

import com.xmerge.chainHandler.chain.IChainHandler;

/**
 * @author Xmerge
 */
public interface UserCheckChainHandler<T> extends IChainHandler<T> {
    @Override
    default String mark() {
        return "userCheck";
    }
}
