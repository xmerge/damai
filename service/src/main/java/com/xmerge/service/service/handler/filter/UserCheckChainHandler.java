package com.xmerge.service.service.handler.filter;

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
