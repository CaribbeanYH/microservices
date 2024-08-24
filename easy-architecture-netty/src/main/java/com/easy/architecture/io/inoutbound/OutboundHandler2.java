package com.easy.architecture.io.inoutbound;

/**
 * Created by andilyliao on 16-6-1.
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class OutboundHandler2 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // 执行下一个OutboundHandler
        super.write(ctx, msg, promise);
    }
}