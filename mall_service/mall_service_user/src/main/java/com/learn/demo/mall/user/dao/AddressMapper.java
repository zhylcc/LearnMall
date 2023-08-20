package com.learn.demo.mall.user.dao;

import com.learn.demo.mall.user.pojo.AddressPO;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zh_cr
 */
@Component
public interface AddressMapper extends Mapper<AddressPO> {
}
