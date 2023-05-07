package com.client.controller;

import com.client.config.resource.Message;
import com.client.config.resource.NettyClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.StandardCharsets;


@RestController
public class ClientController {

    @PostMapping(path = "/SEND_MESSAGE")
    public ResponseEntity<String> sendMessage() {
        Message msg = new Message("Header", "Hello from client!");
        // Encode the message and header into a ByteBuf
        ByteBuf headerBuf = Unpooled.copiedBuffer(msg.getHeader().getBytes(StandardCharsets.UTF_8));
        ByteBuf messageBuf = Unpooled.copiedBuffer(msg.getBody().getBytes(StandardCharsets.UTF_8));
        int headerLength = headerBuf.readableBytes();
        int messageLength = messageBuf.readableBytes();
        ByteBuf buf = Unpooled.buffer(8 + headerLength + messageLength);
        buf.writeInt(headerLength);
        buf.writeInt(messageLength);
        buf.writeBytes(headerBuf);
        buf.writeBytes(messageBuf);
        if(NettyClient.ctx!=null){
            if(NettyClient.ctx.channel().isActive()){
                NettyClient.ctx.channel().writeAndFlush(buf);
            }
        }
        return new ResponseEntity<>("Send Message", HttpStatus.OK);
    }
}
