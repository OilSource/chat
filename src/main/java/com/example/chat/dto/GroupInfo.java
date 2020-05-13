package com.example.chat.dto;

import com.example.chat.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupInfo {

    private String groupId;
    private String groupName;
    private String groupAvatarUrl;
    private List<User> members;
    
}
