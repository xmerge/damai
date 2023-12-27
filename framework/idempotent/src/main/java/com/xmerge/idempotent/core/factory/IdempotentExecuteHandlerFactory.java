package com.xmerge.idempotent.core.factory;

import com.xmerge.base.context.ApplicationContextHolder;
import com.xmerge.idempotent.constant.IdempotentSceneConstant;
import com.xmerge.idempotent.constant.IdempotentTypeConstant;
import com.xmerge.idempotent.core.handler.IdempotentExecuteHandler;
import com.xmerge.idempotent.core.handler.param.IdempotentParamService;

/**
 * 幂等执行处理器工厂类
 * @author Xmerge
 */
public class IdempotentExecuteHandlerFactory {
    /**
     * 获取幂等执行处理器
     *
     * @param scene 指定幂等验证场景类型
     * @param type  指定幂等处理类型
     * @return 幂等执行处理器
     */
    public static IdempotentExecuteHandler getInstance(IdempotentSceneConstant scene, IdempotentTypeConstant type) {
        IdempotentExecuteHandler result = null;
        switch (scene) {
            case RESTAPI -> {
                switch (type) {
                    case PARAM -> result = ApplicationContextHolder.getBean(IdempotentParamService.class);
//                    case TOKEN -> result = ApplicationContextHolder.getBean(IdempotentTokenService.class);
//                    case SPEL -> result = ApplicationContextHolder.getBean(IdempotentSpELByRestAPIExecuteHandler.class);
                    default -> {
                    }
                }
            }
//            case MQ -> result = ApplicationContextHolder.getBean(IdempotentSpELByMQExecuteHandler.class);
            default -> {
            }
        }
        return result;
    }
}
