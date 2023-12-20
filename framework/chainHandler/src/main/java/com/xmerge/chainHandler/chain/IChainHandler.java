package com.xmerge.chainHandler.chain;

import org.springframework.core.Ordered;

/**
 * @author Xmerge
 */
public interface IChainHandler<T> extends Ordered {

    /**
     * 责任链处理器，执行处理逻辑
     */
    void handle(T requestParam);

    /**
     * @return 责任链组件标识
     */
    String mark();
}
