package com.learn.demo.mall.user.dao;

import com.learn.demo.mall.user.pojo.PointLogPO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zh_cr
 */
@Component
public interface PointLogMapper extends Mapper<PointLogPO> {

    @Select("select * from tb_point_log where order_id=#{orderId}")
    PointLogPO selectByOrderId(String orderId);
}
