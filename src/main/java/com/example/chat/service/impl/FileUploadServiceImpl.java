package com.example.chat.service.impl;

import com.example.chat.service.FileUploadService;
import com.example.chat.utils.FileUtils;
import com.example.chat.vo.Result;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${server.port}")
    @Getter@Setter
    private String serverPort;


    private final static String FILE_STORE_PATH = "uploadFile";

    @Override
    public Result upload(MultipartFile file, HttpServletRequest request) {
        // 重命名文件，防止重名
        String filename = getRandomUUID();
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        String fileSize = FileUtils.getFormatSize(file.getSize());
        // 截取文件的后缀名
        if (originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        filename = filename + suffix;
        String prefix = "D:\\javaProject\\chat\\src\\main\\resources\\static\\" + FILE_STORE_PATH;
        System.out.println("存储路径为:" + prefix + "\\" + filename);
        Path filePath = Paths.get(prefix, filename);
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result().error("文件上传发生错误！");
        }
        return new Result().success()
                .setData("originalFilename", originalFilename)
                .setData("fileSize", fileSize)
                .setData("fileUrl", "http://localhost:"+serverPort+"/" + FILE_STORE_PATH + "/" + filename);
    }

    private String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
