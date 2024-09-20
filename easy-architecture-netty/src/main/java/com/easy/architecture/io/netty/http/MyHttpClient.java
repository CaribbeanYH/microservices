package com.easy.architecture.io.netty.http;

import com.easy.architecture.io.netty.http.handler.MyHttpClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author yanghai
 * @ClassName
 * @Description
 * @date 2024/8/23 23:51
 */
public class MyHttpClient {

    public static void sendRequest(String host, int port, FullHttpRequest fullHttpRequest) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel)
                                throws Exception {
                            channel.pipeline().addLast(new HttpRequestEncoder());
                            channel.pipeline().addLast(new HttpResponseDecoder());
//                            channel.pipeline().addLast(new HttpClientCodec());
//                            channel.pipeline().addLast(new HttpObjectAggregator(65536));
                            channel.pipeline().addLast(new HttpContentDecompressor());
                            channel.pipeline().addLast(new MyHttpClientHandler(fullHttpRequest));
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    private static FullHttpRequest buildGetRequest() {
        try {
            URI uri = new URI("/test");
            FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri.toASCIIString());
            request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            request.headers().add(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
            return request;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // 调用指定的服务端信息
        FullHttpRequest request = buildGetRequest();
        sendRequest("127.0.0.1", 8080, request);
    }

}