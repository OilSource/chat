package com.example.chat.service.impl;

import com.example.chat.entity.UserGroupRe;
import com.example.chat.dao.UserGroupReDao;
import com.example.chat.service.UserGroupReService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UserGroupRe)表服务实现类
 *
 * @author makejava
 * @since 2020-05-09 23:23:29
 */
@Service("userGroupReService")
public class UserGroupReServiceImpl implements UserGroupReService {
    @Resource
    private UserGroupReDao userGroupReDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserGroupRe queryById(Integer id) {
        return this.userGroupReDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserGroupRe> queryAllByLimit(int offset, int limit) {
        return this.userGroupReDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userGroupRe 实例对象
     * @return 实例对象
     */
    @Override
    public UserGroupRe insert(UserGroupRe userGroupRe) {
        this.userGroupReDao.insert(userGroupRe);
        return userGroupRe;
    }

    /**
     * 修改数据
     *
     * @param userGroupRe 实例对象
     * @return 实例对象
     */
    @Override
    public UserGroupRe update(UserGroupRe userGroupRe) {
        this.userGroupReDao.update(userGroupRe);
        return this.queryById(userGroupRe.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userGroupReDao.deleteById(id) > 0;
    }
}