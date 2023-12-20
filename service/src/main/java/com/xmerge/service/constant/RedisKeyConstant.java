package com.xmerge.service.constant;

/**
 * Redis key 常量，用于加锁前缀
 * @author Xmerge
 */
public class RedisKeyConstant {

    /**
     * 用户注册锁，Key Prefix + 用户名
     */
    public static final String USER_REGISTER = "com.xmerge.service:user-register:";

    /**
     * 用户注销锁，Key Prefix + 用户名
     */
    public static final String USER_DELETION = "com.xmerge.service:user-deletion:";

    /**
     * 用户注册可复用用户名分片，Key Prefix + Idx
     */
    public static final String USER_REGISTER_REUSE_SHARDING = "com.xmerge.service:user-reuse:";

    /**
     * 用户乘车人列表，Key Prefix + 用户名
     */
    public static final String USER_PASSENGER_LIST = "com.xmerge.service:user-passenger-list:";
}
