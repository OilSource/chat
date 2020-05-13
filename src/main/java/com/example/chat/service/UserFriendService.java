package com.example.chat.service;

import com.example.chat.entity.User;
import com.example.chat.entity.UserFriend;
import java.util.List;

/**
 * (UserFriend)表服务接口
 *
 * @author makejava
 * @since 2020-05-10 15:40:29
 */
public interface UserFriendService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserFriend queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserFriend> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userFriend 实例对象
     * @return 实例对象
     */
    UserFriend insert(UserFriend userFriend);

    /**
     * 修改数据
     *
     * @param userFriend 实例对象
     * @return 实例对象
     */
    UserFriend update(UserFriend userFriend);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<User> getFriendListByUserId(Integer userId);

    void addDefaultFriend(User user);

}