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
public class InboundHandler1 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 通知执行下一个InboundHandler
        log.info("InboundHandler1 执行了");
        ctx.fireChannelRead(msg);
    }
}
