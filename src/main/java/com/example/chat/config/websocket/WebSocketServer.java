package com.example.chat.config.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * 描述: Netty WebSocket服务器
 * 使用独立的线程启动
 *
 * @author Kanarien
 * @version 1.0
 * @date 2018年5月18日 上午11:22:51
 */
@Slf4j
@Component
public class WebSocketServer {

    @Value("${websocket.port}")
    @Getter@Setter
    private int port;
    @Resource(name = "bossGroup")
    private EventLoopGroup bossGroup;
    @Resource(name = "bossGroup")
    private EventLoopGroup workerGroup;
    @Resource
    private ServerBootstrap serverBootstrap;
    @Resource(name = "childChannelHandler")
    private ChannelHandler childChannelHandler;

    private ChannelFuture serverChannelFuture;


    /**
     * 描述：启动Netty Websocket服务器
     */
    public void build() {
        try {
            long begin = System.currentTimeMillis();
            serverBootstrap.group(bossGroup, workerGroup) //boss辅助客户端的tcp连接请求  worker负责与客户端之前的读写操作
                    .channel(NioServerSocketChannel.class) //配置客户端的channel类型
                    .option(ChannelOption.SO_BACKLOG, 1024) //配置TCP参数，握手字符串长度设置
                    .option(ChannelOption.TCP_NODELAY, true) //TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))//配置固定长度接收缓存区分配器
                    .childHandler(childChannelHandler); //绑定I/O事件的处理类,WebSocketChildChannelHandler中定义
            long end = System.currentTimeMillis();
            log.info("Netty Websocket服务器启动完成，耗时 " + (end - begin) + " ms，已绑定端口 " + port + " 阻塞式等候客户端连接");

            serverChannelFuture = serverBootstrap.bind(port).sync();
        } catch (Exception e) {
            log.info(e.getMessage());
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
        }

    }

    /**
     * 描述：关闭Netty Websocket服务器，主要是释放连接
     * 连接包括：服务器连接serverChannel，
     * 客户端TCP处理连接bossGroup，
     * 客户端I/O操作连接workerGroup
     * <p>
     * 若只使用
     * bossGroupFuture = bossGroup.shutdownGracefully();
     * workerGroupFuture = workerGroup.shutdownGracefully();
     * 会造成内存泄漏。
     */
    @PreDestroy
    public void close() {
        log.info("正在释放Netty Websocket相关连接...");
        serverChannelFuture.channel().close();
        Future<?> bossGroupFuture = bossGroup.shutdownGracefully();
        Future<?> workerGroupFuture = workerGroup.shutdownGracefully();

        try {
            bossGroupFuture.await();
            workerGroupFuture.await();
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
        }
    }



}
