package com.example.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

public interface ChatService {

    void register(Map<String, Object> param, ChannelHandlerContext ctx) throws JsonProcessingException;

    void singleSend(Map<String, Object> param, ChannelHandlerContext ctx) throws JsonProcessingException;

    void groupSend(Map<String, Object> param, ChannelHandlerContext ctx) throws JsonProcessingException;

    void FileMsgSingleSend(Map<String, Object> param, ChannelHandlerContext ctx) throws JsonProcessingException;

    void FileMsgGroupSend(Map<String, Object> param, ChannelHandlerContext ctx) throws JsonProcessingException;

    void remove(ChannelHandlerContext ctx);

    void typeError(ChannelHandlerContext ctx) throws JsonProcessingException;

}
