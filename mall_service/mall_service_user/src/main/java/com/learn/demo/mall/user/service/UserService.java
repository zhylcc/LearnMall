package com.learn.demo.mall.user.service;

import com.learn.demo.mall.user.pojo.UserPO;
import com.learn.demo.mall.user.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zh_cr
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public UserPO queryByUsername(String username) {
        return userMapper.selectByPrimaryKey(username);
    }

    public void deleteByUsername(String username) {
        userMapper.deleteByPrimaryKey(username);
    }

    public void updatePoints(String username, Integer points) {
        userMapper.updatePoints(username, points);
    }
}
