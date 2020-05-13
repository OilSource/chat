package com.example.chat.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-05-09 23:23:21
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -15011309877065710L;
    
    private Integer userId;
    /**
    * 用户名
    */
    private String userName;
    /**
    * 密码
    */
    private String passport;
    /**
    * 图片
    */
    private String pic;

}