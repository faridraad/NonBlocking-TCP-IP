package com.client.config.resource;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 8) {
            return; // Not enough data for header
        }

        int headerLength = in.readInt();
        int messageLength = in.readInt();

        if (in.readableBytes() < headerLength + messageLength) {
            in.resetReaderIndex();
            return; // Not enough data for message
        }

        ByteBuf headerBuf = in.readBytes(headerLength);
        ByteBuf messageBuf = in.readBytes(messageLength);

        String header = headerBuf.toString(StandardCharsets.UTF_8);
        String message = messageBuf.toString(StandardCharsets.UTF_8);

        out.add(new Message(header, message));
    }
}

