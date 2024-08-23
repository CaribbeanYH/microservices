package com.easy.architecture.io.tcp.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/23 17:19
 */
public class NettyTcpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }
}
