package com.easy.architecture.io.http.handler;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URISyntaxException;

/**
 * @author yanghai
 * @ClassName
 * @Description
 * @date 2024/8/23 23:46
 */
public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws URISyntaxException {
        // 处理请求
        if (!(msg instanceof FullHttpRequest)) {
            // 不是完整的 http 请求，直接触发，然后结束。
            System.out.println("---------------- 不是合格的 http 请求，直接忽略丢弃");
            ctx.fireChannelRead(msg);
            return;
        }

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            HttpMethod method = request.getMethod();

            if (!method.equals(HttpMethod.GET)) { // 非 GET 方法不处理
                sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
                return;
            }
            // 直接把响应返回给客户端
            response(ctx, "hell client!!!");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 异常处理
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 直接响应给客户端
     *
     * @param ctx     上下文
     * @param content 响应内容
     */
    private void response(ChannelHandlerContext ctx, String content) {
        FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));

        // 请求头设置
        resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

        // 2.发送
        // 注意必须在使用完之后，close channel
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 发送失败
     *
     * @param ctx
     * @param status
     */
    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.content().writeBytes(status.toString().getBytes());
        ctx.writeAndFlush(response);
    }
}
