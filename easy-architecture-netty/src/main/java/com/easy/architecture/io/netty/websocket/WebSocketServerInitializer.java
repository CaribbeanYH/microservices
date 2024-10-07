package com.easy.architecture.io.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;

/**
 * @author yanghai
 * @ClassName
 * @Description
 * @date 2024/10/7 15:42
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final String WEBSOCKET_PATH = "/";

    private final SslContext sslCtx;

    public WebSocketServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc())); // 设置 https 相关
        }
        pipeline.addLast(new HttpServerCodec()); // http 编码
        pipeline.addLast(new HttpObjectAggregator(65536)); // http 消息聚合器
        pipeline.addLast(new WebSocketServerCompressionHandler()); // 压缩 可以不设置
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true)); // 协议
        pipeline.addLast(new WebSocketFrameHandler()); // 处理WebSocketFrame
    }
}
