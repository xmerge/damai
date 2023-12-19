package com.xmerge.cache.util;

import com.alibaba.fastjson2.util.ParameterizedTypeImpl;

import java.lang.reflect.Type;

/**
 * FastJson2 工具类，转换类型
 * @author Xmerge
 */
public class FastJson2Util {
    public static Type buildType(Type... types) {
        ParameterizedTypeImpl beforeType = null;
        if (types != null && types.length > 0) {
            if (types.length == 1) {
                return new ParameterizedTypeImpl(new Type[]{null}, null, types[0]);
            }
            for (int i = types.length - 1; i > 0; i--) {
                beforeType = new ParameterizedTypeImpl(new Type[]{beforeType == null ? types[i] : beforeType}, null, types[i - 1]);
            }
        }
        return beforeType;
    }
}
