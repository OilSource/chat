package com.example.chat.controller;

import com.example.chat.service.SecurityService;
import com.example.chat.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class SecurityController {

    @Autowired
    SecurityService securityService;

    @GetMapping(value = {"login","/"})
    public String toLogin() {
        return "index";
    }

    @GetMapping(value = {"register"})
    public String register() {
        return "register";
    }


    @PostMapping(value = "login")
    @ResponseBody
    public Result login(HttpSession session,
                        @RequestParam String username,
                        @RequestParam String password) {
        return securityService.login(username, password, session);
    }

    @PostMapping(value = "register")
    @ResponseBody
    public Result register(HttpSession session,
                        @RequestParam String username,
                        @RequestParam String password) throws IOException {
        return securityService.register(username, password);
    }

}
