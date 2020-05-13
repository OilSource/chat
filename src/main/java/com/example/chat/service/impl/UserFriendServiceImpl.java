package com.example.chat.service.impl;

import com.example.chat.entity.User;
import com.example.chat.entity.UserFriend;
import com.example.chat.dao.UserFriendDao;
import com.example.chat.service.UserFriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (UserFriend)表服务实现类
 *
 * @author makejava
 * @since 2020-05-10 15:40:29
 */
@Service("userFriendService")
public class UserFriendServiceImpl implements UserFriendService {
    @Resource
    private UserFriendDao userFriendDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserFriend queryById(Integer id) {
        return this.userFriendDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserFriend> queryAllByLimit(int offset, int limit) {
        return this.userFriendDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userFriend 实例对象
     * @return 实例对象
     */
    @Override
    public UserFriend insert(UserFriend userFriend) {
        this.userFriendDao.insert(userFriend);
        return userFriend;
    }

    /**
     * 修改数据
     *
     * @param userFriend 实例对象
     * @return 实例对象
     */
    @Override
    public UserFriend update(UserFriend userFriend) {
        this.userFriendDao.update(userFriend);
        return this.queryById(userFriend.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userFriendDao.deleteById(id) > 0;
    }

    @Override
    public List<User> getFriendListByUserId(Integer userId) {
        return userFriendDao.getFriendListByUserId(userId);
    }

    @Override
    public void addDefaultFriend(User user) {
        ArrayList<UserFriend> list = new ArrayList<>();
        UserFriend userFriend = new UserFriend();
        userFriend.setUserId(user.getUserId());
        userFriend.setCategory("我的好友");
        userFriend.setFriendId(1);
        list.add(userFriend);

        UserFriend userFriend2 = new UserFriend();
        userFriend2.setUserId(1);
        userFriend2.setCategory("我的好友");
        userFriend2.setFriendId(user.getUserId());
        list.add(userFriend2);
        userFriendDao.batchInsert(list);
    }
}