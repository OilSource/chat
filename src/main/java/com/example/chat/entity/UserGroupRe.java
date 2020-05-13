package com.example.chat.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (UserGroupRe)实体类
 *
 * @author makejava
 * @since 2020-05-09 23:23:29
 */
@Data
public class UserGroupRe implements Serializable {
    private static final long serialVersionUID = 254461984750557678L;
    
    private Integer id;
    
    private Integer groupId;
    
    private Integer userId;

}