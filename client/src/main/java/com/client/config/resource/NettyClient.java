package com.client.config.resource;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class NettyClient {
    private static final String host = "localhost";
    private static final int port = 3030;

    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    public static ChannelHandlerContext ctx;

    @PostConstruct
    public void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageDecoder());
                        ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });

        channel = bootstrap.connect("localhost", port).sync().channel();
    }

//    public void stop() throws InterruptedException {
//        channel.close().sync();
//        group.shutdownGracefully();
//    }

}
