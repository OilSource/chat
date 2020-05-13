package com.example.chat.config.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component(value = "childChannelHandler")
public class WebSocketChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Autowired
    private TextWebSocketServerHandler webSocketServerHandler;

    @Resource
    private BinaryWebSocketFrameHandler binaryWebSocketFrameHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                // HTTP编码解码器
                .addLast("http-codec", new HttpServerCodec())
                // 将HttpMessage和HttpContents聚合到一个完成的 FullHttpRequest或FullHttpResponse中,具体是FullHttpRequest对象还是FullHttpResponse对象取决于是请求还是响应
                // 需要放到HttpServerCodec这个处理器后面
                .addLast("aggregator", new HttpObjectAggregator(10240))
                //把HTTP头、HTTP体拼成完整的HTTP请求
                .addLast("http-chunked", new ChunkedWriteHandler())
                //方便大文件传输，不过实质上都是短的文本数据
                // webSocket 数据压缩扩展，当添加这个的时候WebSocketServerProtocolHandler的第三个参数需要设置成true
                .addLast("compress-handler",new WebSocketServerCompressionHandler())
                .addLast("idle-handler",new IdleStateHandler(1,2,5, TimeUnit.MINUTES))
                .addLast("websocket-text-handler", webSocketServerHandler)
                .addLast("websocket-binary-handler", binaryWebSocketFrameHandler)
                // 对websocket url中的参数做解析处理的Handler
//				.addLast(customUrlHandler)
                .addLast("http-handler", new WebSocketServerProtocolHandler("/websocket", null, true, 10485760));

    }

}
