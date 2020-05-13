package com.example.chat.config.websocket;

import cn.hutool.core.io.IoUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

@Slf4j
@Sharable
@Component
public class BinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws InterruptedException, IOException {
        log.info("服务器接收到二进制消息,消息长度:[{}]", msg.content().capacity());
//        ByteBuf byteBuf = Unpooled.directBuffer(msg.content().capacity());
//        byteBuf.writeBytes(msg.content());

        FileOutputStream fileOutputStream = null;
        FileChannel channel = null;
        try {
            fileOutputStream = new FileOutputStream("D:\\javaProject\\chat\\src\\main\\resources\\templates\\chatroom.sql");
            channel = fileOutputStream.getChannel();
            msg.content().getBytes(0, channel, 0, msg.content().capacity());
        } finally {
            IoUtil.close(channel);
            IoUtil.close(fileOutputStream);
        }

    }
}
