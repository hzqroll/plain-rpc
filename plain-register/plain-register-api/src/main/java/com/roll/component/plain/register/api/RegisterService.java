package com.roll.component.plain.register.api;

/**
 * 服务注册与发现接口
 *
 * @author roll
 * created on 2019-09-05 14:40
 */
public interface RegisterService {
    /**
     * 注册服务到zk上
     *
     * @param register 服务名:应用名/类名-版本, {@link Register}
     * @return 是否注册成功
     */
    boolean register(Register register);

    /**
     * 取消注册到zk上
     *
     * @param register 服务名:应用名/类名-版本, {@link Register}
     * @return 是否取消注册成功
     */
    boolean unRegister(Register register);

    /**
     * 在zk上寻找注册的ip+port
     *
     * @param register 服务名:应用名/类名-版本, {@link Register}
     * @return 返回寻找到的ip+port
     */
    String lookup(Register register);
}
