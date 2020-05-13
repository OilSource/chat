package com.example.chat.service.impl;

import com.example.chat.dto.UserInfo;
import com.example.chat.entity.User;
import com.example.chat.dao.UserDao;
import com.example.chat.service.UserFriendService;
import com.example.chat.service.UserGroupService;
import com.example.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020-05-09 23:23:27
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    UserFriendService userFriendService;

    @Resource
    UserGroupService userGroupService;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer userId) {
        return this.userDao.queryById(userId);
    }


    @Override
    public UserInfo getUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserId());
        userInfo.setUsername(user.getUserName());
        userInfo.setAvatarUrl(user.getPic());
        userInfo.setFriendList(userFriendService.getFriendListByUserId(user.getUserId()));
        userInfo.setGroupList(userGroupService.getGroupListByUserId(user.getUserId()));
        return userInfo;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer userId) {
        return this.userDao.deleteById(userId) > 0;
    }

    @Override
    public User getByUsername(String userName) {
        return this.userDao.getByUsername(userName);
    }
}