package com.learn.demo.mall.user.dao;

import com.learn.demo.mall.user.pojo.UserPO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zh_cr
 */
@Repository
public interface UserMapper extends Mapper<UserPO> {
}
