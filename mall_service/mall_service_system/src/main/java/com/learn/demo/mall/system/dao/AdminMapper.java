package com.learn.demo.mall.system.dao;

import com.learn.demo.mall.system.pojo.AdminPO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zh_cr
 */
@Repository
public interface AdminMapper extends Mapper<AdminPO> {
}
