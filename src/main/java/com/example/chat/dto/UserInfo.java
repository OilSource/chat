package com.example.chat.dto;

import com.example.chat.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Integer userId;
    private String username;
    private String avatarUrl;
    private List<User> friendList;
    private List<GroupInfo> groupList;

}
