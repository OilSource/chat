package com.example.chat.controller;


import com.example.chat.constant.ChatConstant;
import com.example.chat.dto.UserInfo;
import com.example.chat.entity.User;
import com.example.chat.service.UserService;
import com.example.chat.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chatroom")
public class ChatroomController {

    @Autowired
    UserService userService;

    /**
     * 描述：登录成功后，调用此接口进行页面跳转
     *
     * @return
     */
    @GetMapping
    public ModelAndView toChatroom(HttpSession httpSession) {
        User user = (User)httpSession.getAttribute(ChatConstant.USER_TOKEN);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("chatroom");
        modelAndView.addObject("userId", user.getUserId());
        return modelAndView;
    }

}
