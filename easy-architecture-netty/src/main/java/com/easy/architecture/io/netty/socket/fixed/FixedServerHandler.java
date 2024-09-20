package com.easy.architecture.io.netty.socket.fixed;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/21 01:34
 */
public class FixedServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("server receives message: " + msg.trim());
        ctx.writeAndFlush("hello client!");
    }
}

