package com.xmerge.idempotent.constant;

/**
 * 幂等场景常量
 * @author Xmerge
 */
public enum IdempotentSceneConstant {

    /**
    * 接口幂等
    */
    RESTAPI,
    /**
    * 消息队列幂等
    */
    MQ;
}
