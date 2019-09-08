package com.roll.component.plain.rpc.server.netty;

import com.roll.component.plain.rpc.common.register.RpcRequest;
import com.roll.component.plain.rpc.common.register.RpcResponse;
import com.roll.component.plain.rpc.server.rpcserver.RpcServiceCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author roll
 * created on 2019-09-06 10:50
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private final Logger log = LoggerFactory.getLogger(RpcServerHandler.class);

    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        RpcResponse response = new RpcResponse();
        response.setRequestId(msg.getRequestId());
        try {
            Object result = invokeService(msg);
            response.setResult(result);
        } catch (InvocationTargetException e) {
            response.setException(e);
        }
        ctx.channel().writeAndFlush(response);
    }


    /**
     * 调用具体实现
     *
     * @param request 请求参数
     * @return 调用结果
     * @throws InvocationTargetException 调用失败异常
     */
    private Object invokeService(RpcRequest request) throws InvocationTargetException {
        String serviceName = request.getInterfaceName();

        Object bean = RpcServiceCache.getServiceBean(serviceName);
        if (bean == null) {
            log.error("no match bean, serviceName: {}.", serviceName);
            throw new RuntimeException("no match service name.");
        }

        Class<?> serviceClass = bean.getClass();
        String metthod = request.getMethodName();
        Class<?>[] paramTypes = request.getParameterTypes();
        Object[] params = request.getParameters();

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod fastMethod = serviceFastClass.getMethod(metthod, paramTypes);
        return fastMethod.invoke(bean, params);
    }
}
