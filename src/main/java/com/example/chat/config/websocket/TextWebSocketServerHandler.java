package com.example.chat.config.websocket;


import cn.hutool.http.HttpUtil;
import com.example.chat.constant.ChatConstant;
import com.example.chat.service.ChatService;
import com.example.chat.vo.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Slf4j
@Component
@Sharable
public class TextWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 只针对FullHttpRequest类型的做处理，其它类型的自动放过
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            int idx = uri.indexOf("?");
            if (idx > 0) {
                HashMap<String, String> paramMap = HttpUtil.decodeParamMap(uri, "UTF-8");
                int userId = Integer.parseInt(paramMap.get("userId"));
                ChannelHandlerContext channelHandlerContext = ChatConstant.onlineUserMap.get(userId);
                if(null != channelHandlerContext){
                    channelHandlerContext.close();
                    ChatConstant.onlineUserMap.remove(userId);
                    ChatConstant.channelMap.remove(channelHandlerContext.channel().id());
                    log.info(MessageFormat.format("userId为 {0} 的用户已退出聊天，当前在线人数为：{1}"
                            , userId, ChatConstant.onlineUserMap.size()));
                }
                ChatConstant.onlineUserMap.put(userId, ctx);
                ChatConstant.channelMap.put(ctx.channel().id().toString(),userId);
                request.setUri(uri.substring(0, idx));
                log.info(MessageFormat.format("userId为 {0} 的用户已加入聊天，当前在线人数为：{1}"
                        , userId, ChatConstant.onlineUserMap.size()));
            } else {
                ctx.close();
            }
        }
        super.channelRead(ctx, msg);
    }

    /**
     * 描述：读取完连接的消息后，对消息进行处理。
     * 这里主要是处理WebSocket请求
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        handlerWebSocketFrame(ctx, msg);
    }

    /**
     * 描述：处理WebSocketFrame
     *
     * @param ctx
     * @param frame
     * @throws Exception
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        // 客服端发送过来的消息
        String request = frame.text();
        LOGGER.info("服务端收到新信息：" + request);
        Map param = null;
        try {
            param = objectMapper.readValue(request, Map.class);
        } catch (Exception e) {
            sendErrorMessage(ctx, "JSON字符串转换出错！");
            e.printStackTrace();
        }
        if (param == null) {
            sendErrorMessage(ctx, "参数为空！");
            return;
        }

        String type = (String) param.get("type");
        switch (type) {
            case "REGISTER":
                chatService.register(param, ctx);
                break;
            case "SINGLE_SENDING":
                chatService.singleSend(param, ctx);
                break;
            case "GROUP_SENDING":
                chatService.groupSend(param, ctx);
                break;
            case "FILE_MSG_SINGLE_SENDING":
                chatService.FileMsgSingleSend(param, ctx);
                break;
            case "FILE_MSG_GROUP_SENDING":
                chatService.FileMsgGroupSend(param, ctx);
                break;
            default:
                chatService.typeError(ctx);
                break;
        }
    }

    /**
     * 描述：客户端断开连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chatService.remove(ctx);
    }

    /**
     * 异常处理：关闭channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        chatService.remove(ctx);
    }


    private void sendErrorMessage(ChannelHandlerContext ctx, String errorMsg) throws JsonProcessingException {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Result()
                .error(errorMsg))));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    log.info("读空闲");
                    break;
                case WRITER_IDLE:
                    log.info("写空闲");
                    break;
                case ALL_IDLE:
                    chatService.remove(ctx);
                    break;
                default:
                    break;
            }
        }
    }
}
