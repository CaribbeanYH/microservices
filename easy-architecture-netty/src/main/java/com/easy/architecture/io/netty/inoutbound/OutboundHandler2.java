package com.easy.architecture.io.netty.inoutbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/21 01:45
 */
@Slf4j
public class OutboundHandler2 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("OutboundHandler2 执行了");
        // 执行下一个OutboundHandler
        super.write(ctx, msg, promise);
    }
}