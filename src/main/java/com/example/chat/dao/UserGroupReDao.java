package com.example.chat.dao;

import com.example.chat.entity.UserGroupRe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (UserGroupRe)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-09 23:23:29
 */
public interface UserGroupReDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserGroupRe queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserGroupRe> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userGroupRe 实例对象
     * @return 对象列表
     */
    List<UserGroupRe> queryAll(UserGroupRe userGroupRe);

    /**
     * 新增数据
     *
     * @param userGroupRe 实例对象
     * @return 影响行数
     */
    int insert(UserGroupRe userGroupRe);

    /**
     * 修改数据
     *
     * @param userGroupRe 实例对象
     * @return 影响行数
     */
    int update(UserGroupRe userGroupRe);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}