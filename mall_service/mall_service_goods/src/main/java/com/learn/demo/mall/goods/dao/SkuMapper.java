package com.learn.demo.mall.goods.dao;

import com.learn.demo.mall.goods.pojo.SkuPO;
import com.learn.demo.mall.order.pojo.OrderItemPO;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * SKU管理通用mapper
 * @author zh_cr
 */
@Repository
public interface SkuMapper extends Mapper<SkuPO> {

    @Update("update tb_sku set num=num-#{num},sale_num=sale_num+#{num} where id=#{skuId} and num>=#{num}")
    int reduce(OrderItemPO item);

    @Update("update tb_sku set num=num+#{num},sale_num=sale_num-#{num} where id=#{id}")
    int increase(String id, Integer num);
}
