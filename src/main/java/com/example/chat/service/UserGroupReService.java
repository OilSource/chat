package com.example.chat.service;

import com.example.chat.entity.UserGroupRe;
import java.util.List;

/**
 * (UserGroupRe)表服务接口
 *
 * @author makejava
 * @since 2020-05-09 23:23:29
 */
public interface UserGroupReService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserGroupRe queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserGroupRe> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userGroupRe 实例对象
     * @return 实例对象
     */
    UserGroupRe insert(UserGroupRe userGroupRe);

    /**
     * 修改数据
     *
     * @param userGroupRe 实例对象
     * @return 实例对象
     */
    UserGroupRe update(UserGroupRe userGroupRe);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}