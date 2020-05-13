package com.example.chat.service;

import com.example.chat.vo.Result;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface SecurityService {

    Result login(String username, String password, HttpSession session);

    Result register(String username, String password) throws IOException;
}
