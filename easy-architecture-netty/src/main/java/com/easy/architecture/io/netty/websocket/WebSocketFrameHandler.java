package com.easy.architecture.io.netty.websocket;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author yanghai
 * @ClassName
 * @Description
 * @date 2024/10/7 15:42
 */
@Slf4j
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    //客户端组
    public static ChannelGroup channelGroup;

    static {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    //存储ip和channel的容器
    private static ConcurrentMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
         if (frame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame) frame).text();
            log.info("接收消息:" + request);
            String msg = "接收成功";
            //获取当前channel绑定的IP地址
            InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
            String address = ipSocket.getAddress().getHostAddress();
            log.info("address为:" + address);
            //将IP和channel的关系保存
            if (!channelMap.containsKey(address)) {
                channelMap.put(address, ctx.channel());
            }
            //返回信息
            ctx.channel().writeAndFlush(new TextWebSocketFrame(msg));
        } else if (frame instanceof BinaryWebSocketFrame) {
            //二进制
            ByteBuf content = frame.content();
            byte[] reg = new byte[content.readableBytes()];
            content.readBytes(reg);
            String request = new String(reg, "UTF-8");
            log.info("接收消息:" + request);
            String msg = "接收成功";
            //返回信息
            ByteBuf respByteBuf = Unpooled.copiedBuffer(msg.getBytes());
            ctx.channel().writeAndFlush(new BinaryWebSocketFrame(respByteBuf));
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("与客户端连接成功");
        channelGroup.add(ctx.channel());
    }
}
