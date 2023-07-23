package com.learn.demo.mall.goods.dao;

import com.learn.demo.mall.goods.pojo.SkuPO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * SKU管理通用mapper
 * @author zh_cr
 */
@Repository
public interface SkuMapper extends Mapper<SkuPO> {
}
