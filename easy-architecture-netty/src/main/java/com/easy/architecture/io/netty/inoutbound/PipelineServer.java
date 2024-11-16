package com.easy.architecture.io.netty.inoutbound;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/21 01:45
 * <p>
 *     结论：ctx.channel().writeAndFlush 将从 Pipeline 的尾部开始往前找 OutboundHandler 。 ctx.writeAndFlush 会从当前 handler 往前找 OutboundHandler。
 * </p>
 */
public class PipelineServer {
    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
//                            // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2 OutboundHandler1
//                            ch.pipeline().addLast(new OutboundHandler1());
//                            ch.pipeline().addLast(new OutboundHandler2());
//                            // 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1 InboundHandler2
//                            ch.pipeline().addLast(new InboundHandler1());
//                            ch.pipeline().addLast(new InboundHandler2());
//                            //OutboundHandler1 --> OutboundHandler2 --> InboundHandler1 --> InboundHandler2
//                            //ctx.writeAndFlush(msg) 与 ctx.channel().writeAndFlush(msg); 都是从InboundHandler2向前找out


//                            // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler1 OutboundHandler2
//                            ch.pipeline().addLast(new OutboundHandler2());
//                            ch.pipeline().addLast(new OutboundHandler1());
//                            // 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1 InboundHandler2
//                            ch.pipeline().addLast(new InboundHandler1());
//                            ch.pipeline().addLast(new InboundHandler2());
//                            //OutboundHandler2 --> OutboundHandler1 --> InboundHandler1 --> InboundHandler2
//                            //ctx.writeAndFlush(msg) 与 ctx.channel().writeAndFlush(msg); 都是从InboundHandler2向前找out


//                            // 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1 InboundHandler2
                            ch.pipeline().addLast(new InboundHandler1());
                            ch.pipeline().addLast(new InboundHandler2());
                            // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2 OutboundHandler1
                            ch.pipeline().addLast(new OutboundHandler1());
                            ch.pipeline().addLast(new OutboundHandler2());
//                            //InboundHandler1 --> InboundHandler2 --> OutboundHandler1 --> OutboundHandler2
//                            //InboundHandler2 是ctx.writeAndFlush(msg); 会从InboundHandler2开始找out，没有了out了
//                            //InboundHandler2 是ctx.channel().writeAndFlush(msg); 会从OutboundHandler2开始找out，没有了out了
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
        PipelineServer server = new PipelineServer();
        server.start(8000);
    }
}
