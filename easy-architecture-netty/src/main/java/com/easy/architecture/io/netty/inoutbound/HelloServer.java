package com.easy.architecture.io.netty.inoutbound;

/**
 * Created by andilyliao on 16-6-1.
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {
    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2 OutboundHandler1
                            ch.pipeline().addLast(new OutboundHandler1());
                            ch.pipeline().addLast(new OutboundHandler2());
                            // 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1 InboundHandler2
                            ch.pipeline().addLast(new InboundHandler1());
                            ch.pipeline().addLast(new InboundHandler2());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HelloServer server = new HelloServer();
        server.start(8000);
    }

    /**
     pipeline的顺序问题
     1、InboundHandler是通过fire事件决定是否要执行下一个InboundHandler，如果哪个InboundHandler没有调用fire事件，那么往后的Pipeline就断掉了。
     2、InboundHandler是按照Pipleline的加载顺序，顺序执行。
     3、OutboundHandler是按照Pipeline的加载顺序，逆序执行。
     4、有效的InboundHandler是指通过fire事件能触达到的最后一个InboundHander。
     5、如果想让所有的OutboundHandler都能被执行到，那么必须把OutboundHandler放在最后一个有效的InboundHandler之前。
     6、推荐的做法是通过addFirst加载所有OutboundHandler，再通过addLast加载所有InboundHandler。
     7、OutboundHandler是通过write方法实现Pipeline的串联的。
     8、如果OutboundHandler在Pipeline的处理链上，其中一个OutboundHandler没有调用write方法，最终消息将不会发送出去。
     9、ctx.writeAndFlush是从当前ChannelHandler开始，逆序向前执行OutboundHandler。
     10、ctx.writeAndFlush所在ChannelHandler后面的OutboundHandler将不会被执行。
     11、ctx.channel().writeAndFlush 是从最后一个OutboundHandler开始，依次逆序向前执行其他OutboundHandler，即使最后一个ChannelHandler是OutboundHandler，在InboundHandler之前，也会执行该OutbondHandler。
     12、千万不要在OutboundHandler的write方法里执行ctx.channel().writeAndFlush，否则就死循环了。
     */
}
