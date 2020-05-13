package com.example.chat.entity;

import java.io.Serializable;

/**
 * (UserFriend)实体类
 *
 * @author makejava
 * @since 2020-05-10 15:40:29
 */
public class UserFriend implements Serializable {
    private static final long serialVersionUID = -36752668956039868L;
    
    private Integer id;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 类别
    */
    private String category;
    /**
    * 好友用户id
    */
    private Integer friendId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

}