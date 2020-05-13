package com.example.chat.dao;

import com.example.chat.entity.User;
import com.example.chat.entity.UserFriend;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (UserFriend)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-10 15:40:29
 */
public interface UserFriendDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserFriend queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserFriend> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userFriend 实例对象
     * @return 对象列表
     */
    List<UserFriend> queryAll(UserFriend userFriend);

    /**
     * 新增数据
     *
     * @param userFriend 实例对象
     * @return 影响行数
     */
    int insert(UserFriend userFriend);

    /**
     * 修改数据
     *
     * @param userFriend 实例对象
     * @return 影响行数
     */
    int update(UserFriend userFriend);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<User> getFriendListByUserId(Integer userId);

    void batchInsert(@Param("list") List<UserFriend> list);

}