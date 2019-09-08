package com.roll.component.plain.rpc.client;

import com.roll.component.plain.register.api.Register;
import com.roll.component.plain.register.api.RegisterService;
import com.roll.component.plain.rpc.common.register.RegisterConstant;
import com.roll.component.plain.rpc.common.register.RpcRequest;
import com.roll.component.plain.rpc.common.register.RpcResponse;

import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author roll
 * created on 2019-09-03 20:07
 */
public class RpcProxy {
    private String serviceAddress;

    private RegisterService registerService;

    public RpcProxy(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public RpcProxy(RegisterService registerService) {
        this.registerService = registerService;
    }

    public <T> T create(final Class<?> interfaceClass) {
        return create(interfaceClass, "");
    }

    public <T> T create(final Class<?> interfaceClass, final String serviceVersion) {
        // create proxy object
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                (proxy, method, args) -> {
                    // create rpc request object and set attribute
                    RpcRequest request = new RpcRequest();
                    request.setRequestId(UUID.randomUUID().toString());
                    request.setInterfaceName(method.getDeclaringClass().getName());
                    request.setServiceVersion(serviceVersion);
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setParameters(args);
                    // get rpc address
                    if (registerService != null) {
                        String serviceName = interfaceClass.getName();
                        if (serviceVersion != null && serviceVersion.length() > 0) {
                            serviceName += "-" + serviceVersion;
                        }
                        Register register = new Register();
                        register.setServiceName(serviceName);
                        register.setServiceVersion(serviceVersion);
                        register.setAddress(serviceAddress);
                        register.setAppName("appName");
                        serviceAddress = registerService.lookup(register);
                        System.out.println("discovery service:" + serviceName + "->" + serviceAddress);
                    }
                    if (serviceAddress == null || serviceAddress.length() == 0) {
                        throw new RuntimeException("server address is empty");
                    }
                    // get address and port from rpc serviceAddress
                    String[] array = serviceAddress.split(":");
                    String host = array[0];
                    int port = Integer.valueOf(array[1]);
                    // create rpc client object and send
                    RpcClient client = new RpcClient(host, port);
                    RpcResponse response = client.send(request);
                    if (request == null) {
                        throw new RuntimeException("response is null");
                    }
                    if (response.getException() != null) {
                        throw response.getException();
                    } else {
                        return response.getResult();
                    }
                }
        );
    }

}
