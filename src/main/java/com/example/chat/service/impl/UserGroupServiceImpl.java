package com.example.chat.service.impl;

import com.example.chat.dto.GroupInfo;
import com.example.chat.entity.UserGroup;
import com.example.chat.dao.UserGroupDao;
import com.example.chat.service.UserGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UserGroup)表服务实现类
 *
 * @author makejava
 * @since 2020-05-09 23:23:29
 */
@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService {
    @Resource
    private UserGroupDao userGroupDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserGroup queryById(Integer id) {
        return this.userGroupDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserGroup> queryAllByLimit(int offset, int limit) {
        return this.userGroupDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userGroup 实例对象
     * @return 实例对象
     */
    @Override
    public UserGroup insert(UserGroup userGroup) {
        this.userGroupDao.insert(userGroup);
        return userGroup;
    }

    /**
     * 修改数据
     *
     * @param userGroup 实例对象
     * @return 实例对象
     */
    @Override
    public UserGroup update(UserGroup userGroup) {
        this.userGroupDao.update(userGroup);
        return this.queryById(userGroup.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userGroupDao.deleteById(id) > 0;
    }

    @Override
    public GroupInfo getByGroupId(Integer groupId) {
        return this.userGroupDao.getByGroupId(groupId);
    }

    @Override
    public List<GroupInfo> getGroupListByUserId(Integer userId) {
        return userGroupDao.getGroupListByUserId(userId);
    }
}