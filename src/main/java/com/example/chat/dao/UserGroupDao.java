package com.example.chat.dao;

import com.example.chat.dto.GroupInfo;
import com.example.chat.entity.UserGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (UserGroup)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-09 23:23:29
 */
public interface UserGroupDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserGroup queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserGroup> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userGroup 实例对象
     * @return 对象列表
     */
    List<UserGroup> queryAll(UserGroup userGroup);

    /**
     * 新增数据
     *
     * @param userGroup 实例对象
     * @return 影响行数
     */
    int insert(UserGroup userGroup);

    /**
     * 修改数据
     *
     * @param userGroup 实例对象
     * @return 影响行数
     */
    int update(UserGroup userGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    GroupInfo getByGroupId(Integer groupId);

    List<GroupInfo> getGroupListByUserId(Integer userId);

}