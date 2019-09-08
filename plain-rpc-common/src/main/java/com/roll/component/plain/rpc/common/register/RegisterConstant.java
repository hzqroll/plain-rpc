package com.roll.component.plain.rpc.common.register;

/**
 * @author roll
 * created on 2019-09-05 15:48
 */
public class RegisterConstant {
    /**
     * 注册服务根路径
     */
    public static final String SERVICE_ROOT = "/register";

    /**
     * zk session超时时间
     */
    public static final long ZK_SESSION_TIMEOUAT = 10000L;

    /**
     * zk 链接超时时间
     */
    public static final long ZK_CONNECT_TIMEOUT = 10000L;

    public static final String LOCAL_ADDRESS = "127.0.0.1:8001";
}
