package com.learn.demo.mall.goods.dao;

import com.learn.demo.mall.goods.pojo.CategoryBrandPO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 分类-品牌中间表通用mapper
 * @author zh_cr
 */
@Repository
public interface CategoryBrandMapper extends Mapper<CategoryBrandPO> {
}
