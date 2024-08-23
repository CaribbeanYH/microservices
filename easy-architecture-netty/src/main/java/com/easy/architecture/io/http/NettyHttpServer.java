package com.easy.architecture.io.http;

import com.easy.architecture.io.http.handler.NettyHttpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author yanghai
 * @ClassName
 * @Description
 * @date 2024/8/23 23:45
 */
public class NettyHttpServer {
    private final int port;

    public NettyHttpServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加 HttpRequestDecoder，用于将字节流解码成 HttpRequest 对象
                            pipeline.addLast(new HttpRequestDecoder());
                            // 添加 HttpObjectAggregator，用于将多个 HttpContent 对象合并成一个 FullHttpRequest 对象
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            // 添加 HttpResponseEncoder，用于将 HttpResponse 对象编码成字节流
                            pipeline.addLast(new HttpResponseEncoder());
                            // 添加处理器
                            pipeline.addLast(new NettyHttpServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 启动服务器
            ChannelFuture f = b.bind(port).sync();
            // 等待服务器套接字关闭
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        System.out.println("服务端准备在 port 启动完成" + port);
        NettyHttpServer server = new NettyHttpServer(port);
        server.start();
    }
}
