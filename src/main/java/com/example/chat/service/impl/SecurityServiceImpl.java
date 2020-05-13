package com.example.chat.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.chat.constant.ChatConstant;
import com.example.chat.entity.User;
import com.example.chat.entity.UserFriend;
import com.example.chat.service.SecurityService;
import com.example.chat.service.UserFriendService;
import com.example.chat.service.UserService;
import com.example.chat.utils.ImageUtil;
import com.example.chat.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;

@Slf4j
@Service
public class SecurityServiceImpl implements SecurityService {

    private static final String root_path = "D:/javaProject/chat/src/main/resources/static" ;
    private static final String file_path = "/img/avatar/";


    @Autowired
    private UserService userService;

    @Autowired
    private UserFriendService userFriendService;


    @Override
    public Result login(String username, String password, HttpSession session) {
        User user = userService.getByUsername(username);
        if (user == null) {
            return new Result().error("不存在该用户名");
        }
        if (!user.getPassport().equals(password)) {
            return new Result().error("密码不正确");
        }
        user.setPassport(null);
        session.setAttribute(ChatConstant.USER_TOKEN, user);
        ChatConstant.sessionMap.put(user.getUserId(),session);
        return new Result().success();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result register(String username, String password) throws IOException {
        User user = userService.getByUsername(username);
        if(null!=user){
            return new Result().error("已存在该用户名");
        }
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassport(password);
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();
        ImageUtil.createImage(username,root_path+file_path+id+".jpg");
        newUser.setPic(file_path+id+".jpg");
        userService.insert(newUser);
        userFriendService.addDefaultFriend(newUser);
        return new Result().success();
    }
}
