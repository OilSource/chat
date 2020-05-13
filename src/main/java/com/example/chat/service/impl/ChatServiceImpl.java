package com.example.chat.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.chat.constant.ChatConstant;
import com.example.chat.dto.GroupInfo;
import com.example.chat.dto.UserInfo;
import com.example.chat.entity.User;
import com.example.chat.enums.ChatType;
import com.example.chat.service.ChatService;
import com.example.chat.service.UserGroupService;
import com.example.chat.service.UserService;
import com.example.chat.vo.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Override
    public void register(Map<String, Object> param, ChannelHandlerContext ctx) throws JsonProcessingException {
        Integer userId = (Integer) param.get("userId");
        HttpSession httpSession = ChatConstant.sessionMap.get(userId);
        User user = (User)httpSession.getAttribute(ChatConstant.USER_TOKEN);
        UserInfo userInfo = userService.getUserInfo(user);
        sendMessage(ctx, new Result().success()
                .setData("type", ChatType.REGISTER).setData("userInfo",userInfo));

    }

    @Override
    public void singleSend(Map<String, Object> param, ChannelHandlerContext ctx) throws JsonProcessingException {
        Integer fromUserId = (Integer) param.get("fromUserId");
        String toUserId = (String) param.get("toUserId");
        String content = (String) param.get("content");
        ChannelHandlerContext toUserCtx = ChatConstant.onlineUserMap.get(Integer.parseInt(toUserId));
        if (toUserCtx == null) {
            sendMessage(ctx, new Result()
                    .error(MessageFormat.format("userId为 {0} 的用户没有登录！", toUserId)));
        } else {
            Result result = new Result().success()
                    .setData("fromUserId", fromUserId)
                    .setData("content", content)
                    .setData("type", ChatType.SINGLE_SENDING);
            sendMessage(toUserCtx, result);
        }
    }

    @Override
    public void groupSend(Map<String, Object> param, ChannelHandlerContext ctx) throws JsonProcessingException {

        Integer fromUserId = (Integer) param.get("fromUserId");
        String toGroupId = (String) param.get("toGroupId");
        String content = (String) param.get("content");
        GroupInfo groupInfo = userGroupService.getByGroupId(Integer.parseInt(toGroupId));
        if (groupInfo == null) {
            sendMessage(ctx, new Result().error("该群id不存在"));
        } else {
            Result result = new Result().success()
                    .setData("fromUserId", fromUserId)
                    .setData("content", content)
                    .setData("toGroupId", toGroupId)
                    .setData("type", ChatType.GROUP_SENDING);
            groupInfo.getMembers().stream()
                    .forEach(member -> {
                        ChannelHandlerContext toCtx = ChatConstant.onlineUserMap.get(member.getUserId());
                        if (toCtx != null && !member.getUserId().equals(fromUserId)) {
                            try {
                                sendMessage(toCtx, result);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    @Override
    public void remove(ChannelHandlerContext ctx) {
        String id = ctx.channel().id().toString();
        Integer userId = ChatConstant.channelMap.get(id);
        if(null!=userId){
            ChatConstant.onlineUserMap.remove(userId);
        }
        log.info(MessageFormat.format("userId为 {0} 的用户已退出聊天，当前在线人数为：{1}"
                , userId, ChatConstant.onlineUserMap.size()));
        ChatConstant.channelMap.remove(id);
        ctx.close();
    }

    @Override
    public void FileMsgSingleSend(Map<String, Object> param, ChannelHandlerContext ctx) throws JsonProcessingException {
        Integer fromUserId = (Integer) param.get("fromUserId");
        String toUserId = (String) param.get("toUserId");
        String originalFilename = (String) param.get("originalFilename");
        String fileSize = (String) param.get("fileSize");
        String fileUrl = (String) param.get("fileUrl");
        ChannelHandlerContext toUserCtx = ChatConstant.onlineUserMap.get(Integer.parseInt(toUserId));
        if (toUserCtx == null) {
            sendMessage(ctx, new Result()
                    .error(MessageFormat.format("userId为 {0} 的用户没有登录！", toUserId)));
        } else {
            Result result = new Result().success()
                    .setData("fromUserId", fromUserId)
                    .setData("originalFilename", originalFilename)
                    .setData("fileSize", fileSize)
                    .setData("fileUrl", fileUrl)
                    .setData("type", ChatType.FILE_MSG_SINGLE_SENDING);
            sendMessage(toUserCtx, result);
        }
    }

    @Override
    public void FileMsgGroupSend(Map<String, Object> param, ChannelHandlerContext ctx) throws JsonProcessingException {
        Integer fromUserId = (Integer) param.get("fromUserId");
        String toGroupId = (String) param.get("toGroupId");
        String originalFilename = (String) param.get("originalFilename");
        String fileSize = (String) param.get("fileSize");
        String fileUrl = (String) param.get("fileUrl");
        GroupInfo groupInfo = userGroupService.getByGroupId(Integer.parseInt(toGroupId));
        if (groupInfo == null) {
            sendMessage(ctx, new Result().error("该群id不存在"));
        } else {
            Result result = new Result().success()
                    .setData("fromUserId", fromUserId)
                    .setData("toGroupId", toGroupId)
                    .setData("originalFilename", originalFilename)
                    .setData("fileSize", fileSize)
                    .setData("fileUrl", fileUrl)
                    .setData("type", ChatType.FILE_MSG_GROUP_SENDING);
            groupInfo.getMembers().stream()
                    .forEach(member -> {
                        ChannelHandlerContext toCtx = ChatConstant.onlineUserMap.get(member.getUserId());
                        if (toCtx != null && !member.getUserId().equals(fromUserId)) {
                            try {
                                sendMessage(toCtx, result);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    @Override
    public void typeError(ChannelHandlerContext ctx) throws JsonProcessingException {
        sendMessage(ctx, new Result().error("该类型不存在！"));
    }


    private void sendMessage(ChannelHandlerContext ctx, Result result) throws JsonProcessingException {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(result)));
    }


}
