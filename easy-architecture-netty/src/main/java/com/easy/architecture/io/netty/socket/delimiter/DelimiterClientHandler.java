package com.easy.architecture.io.netty.socket.delimiter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/21 01:36
 */
public class DelimiterClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client receives message: " + msg.trim());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("hello server!");
    }
}

