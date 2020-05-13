package com.example.chat.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (UserGroup)实体类
 *
 * @author makejava
 * @since 2020-05-09 23:23:29
 */
@Data
public class UserGroup implements Serializable {
    private static final long serialVersionUID = 714105991878495071L;
    /**
    * 分组id
    */
    private Integer id;
    /**
    * 分组名称
    */
    private String name;
    /**
    * 分组创建人
    */
    private Integer userId;
    /**
    * 分组图像
    */
    private String pic;

}