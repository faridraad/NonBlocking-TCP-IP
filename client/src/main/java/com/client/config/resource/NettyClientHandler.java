package com.client.config.resource;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

@Component
public class NettyClientHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Client connected to server!");
        NettyClient.ctx = ctx;
//        Message msg = new Message("Header", "Hello from client!");
//        // Encode the message and header into a ByteBuf
//        ByteBuf headerBuf = Unpooled.copiedBuffer(msg.getHeader().getBytes(StandardCharsets.UTF_8));
//        ByteBuf messageBuf = Unpooled.copiedBuffer(msg.getBody().getBytes(StandardCharsets.UTF_8));
//        int headerLength = headerBuf.readableBytes();
//        int messageLength = messageBuf.readableBytes();
//        ByteBuf buf = Unpooled.buffer(8 + headerLength + messageLength);
//        buf.writeInt(headerLength);
//        buf.writeInt(messageLength);
//        buf.writeBytes(headerBuf);
//        buf.writeBytes(messageBuf);
//        ctx.channel().writeAndFlush(buf);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
        System.out.println("Client received message with header: " + msg.getHeader() + ", body: " + msg.getBody());
    }
}
