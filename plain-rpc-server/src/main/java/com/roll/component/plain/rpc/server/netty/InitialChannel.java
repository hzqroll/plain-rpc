package com.roll.component.plain.rpc.server.netty;

import com.roll.component.plain.rpc.common.register.RpcRequest;
import com.roll.component.plain.rpc.common.register.RpcResponse;
import com.roll.component.plain.rpc.common.register.codec.RpcDecoder;
import com.roll.component.plain.rpc.common.register.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author roll
 * created on 2019-09-06 10:22
 */
public class InitialChannel extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new RpcDecoder(RpcRequest.class)); // 解码 RPC 请求
        ch.pipeline().addLast(new RpcEncoder(RpcResponse.class)); // 编码 RPC 响应
        ch.pipeline().addLast(new RpcServerHandler());
    }
}
