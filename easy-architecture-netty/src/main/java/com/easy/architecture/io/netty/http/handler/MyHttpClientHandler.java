package com.easy.architecture.io.netty.http.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

/**
 * @author yanghai
 * @ClassName
 * @Description
 * @date 2024/8/23 23:54
 */
public class MyHttpClientHandler extends ChannelInboundHandlerAdapter {

    private final FullHttpRequest request;

    public MyHttpClientHandler(FullHttpRequest request) {
        this.request = request;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 触发请求
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收到完整的响应：msg -> " + msg);
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            ByteBuf buf = response.content();
            String result = buf.toString(CharsetUtil.UTF_8);
            // 接收到 http 服务端的响应
            System.out.println("response -> " + result);
        }
    }
}
