package com.learn.demo.mall.user.service;

import com.learn.demo.mall.user.dao.UserMapper;
import com.learn.demo.mall.user.pojo.UserPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
