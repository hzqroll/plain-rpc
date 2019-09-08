package com.roll.component.plain.rpc.server.rpcserver;

import com.roll.component.plain.register.api.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待注册服务缓存
 * 被注册的服务bean缓存
 *
 * @author roll
 * created on 2019-09-06 14:19
 */
public final class RpcServiceCache {
    private static final List<Register> registers;

    private static final Map<String, Object> serviceBeanMap;

    static {
        registers = new ArrayList<>();

        serviceBeanMap = new HashMap<>();
    }

    public static void refreshserviceBeanMap(String beanName, Object bean) {
        serviceBeanMap.put(beanName, bean);
    }

    public static Object getServiceBean(String beanName) {
        return serviceBeanMap.get(beanName);
    }

    public static void refreshRegister(Register register) {
        registers.add(register);
    }

    public static List<Register> getRegisters() {
        return registers;
    }
}
