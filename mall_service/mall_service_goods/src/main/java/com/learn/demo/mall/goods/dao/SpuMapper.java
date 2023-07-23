package com.learn.demo.mall.goods.dao;

import com.learn.demo.mall.goods.pojo.SpuPO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * SPU管理通用mapper
 * @author zh_cr
 */
@Repository
public interface SpuMapper extends Mapper<SpuPO> {
}
