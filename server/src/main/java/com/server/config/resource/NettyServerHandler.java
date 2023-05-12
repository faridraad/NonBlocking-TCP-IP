package com.server.config.resource;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg)  {
        System.out.println("Server received message with header: " + msg.getHeader() + ", body: " + msg.getBody());
        Message responseMsg = new Message("Response Header", "Hello from server!");
        // Encode the message and header into a ByteBuf
        ByteBuf headerBuf = Unpooled.copiedBuffer(responseMsg.getHeader().getBytes(StandardCharsets.UTF_8));
        ByteBuf messageBuf = Unpooled.copiedBuffer(responseMsg.getBody().getBytes(StandardCharsets.UTF_8));
        int headerLength = headerBuf.readableBytes();
        int messageLength = messageBuf.readableBytes();
        ByteBuf buf = Unpooled.buffer(8 + headerLength + messageLength);
        buf.writeInt(headerLength);
        buf.writeInt(messageLength);
        buf.writeBytes(headerBuf);
        buf.writeBytes(messageBuf);
        ctx.channel().writeAndFlush(buf);
    }
}