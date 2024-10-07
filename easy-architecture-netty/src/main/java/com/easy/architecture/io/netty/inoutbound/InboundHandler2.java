package com.easy.architecture.io.netty.inoutbound;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/21 01:45
 */
@Slf4j
public class InboundHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 读取Client发送的信息，并打印出来
        log.info("InboundHandler2 执行了");
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        String resultStr = new String(result1);
        System.out.println("Client said:" + resultStr);
        result.release();
//        ctx.writeAndFlush(msg);
        ctx.channel().writeAndFlush(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("InboundHandler2 读完数据了");
    }
}