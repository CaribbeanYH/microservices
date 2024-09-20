package com.easy.architecture.io.netty.socket.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/21 01:35
 */
public class DelimiterClient {

    public void connect(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            String delimiter = "_$";
                            // 对服务端返回的消息通过_$进行分隔，并且每次查找的最大大小为1024字节
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.wrappedBuffer(delimiter.getBytes())));
                            // 将分隔之后的字节数据转换为字符串
                            ch.pipeline().addLast(new StringDecoder());
                            // 对客户端发送的数据进行编码，这里主要是在客户端发送的数据最后添加分隔符
                            ch.pipeline().addLast(new DelimiterBasedFrameEncoder(delimiter));
                            // 客户端发送数据给服务端，并且处理从服务端响应的数据
                            ch.pipeline().addLast(new DelimiterClientHandler());
                        }

                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new DelimiterClient().connect("127.0.0.1", 8080);
    }
}

