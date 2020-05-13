package com.example.chat.service;

import com.example.chat.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface FileUploadService {

    Result upload(MultipartFile file, HttpServletRequest request);
}
