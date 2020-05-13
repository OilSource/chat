package com.example.chat.config.init;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.example.chat.config.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class ChatInit implements CommandLineRunner {

    @Autowired
    WebSocketServer webSocketServer;

    @Override
    public void run(String... args) throws Exception {
        webSocketServer.build();
    }
}
