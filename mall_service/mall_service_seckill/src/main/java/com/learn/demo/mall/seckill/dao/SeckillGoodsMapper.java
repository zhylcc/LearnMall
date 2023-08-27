package com.learn.demo.mall.seckill.dao;

import com.learn.demo.mall.seckill.pojo.SeckillGoodsPO;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zh_cr
 */
@Component
public interface SeckillGoodsMapper extends Mapper<SeckillGoodsPO> {

    @Update("update tb_seckill_goods set stock_count=stock_count-1 where id=#{id} and stock_count>=1")
    int decreaseStockById(Long id);
}
