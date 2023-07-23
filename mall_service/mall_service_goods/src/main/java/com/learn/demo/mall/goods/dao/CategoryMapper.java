package com.learn.demo.mall.goods.dao;

import com.learn.demo.mall.goods.pojo.CategoryPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 分类管理通用mapper
 * @author zh_cr
 */
@Repository
public interface CategoryMapper extends Mapper<CategoryPO> {

    @Select("SELECT DISTINCT brand_id FROM tb_category_brand " +
            "WHERE category_id in (SELECT id FROM tb_category WHERE name=#{categoryName})")
    List<Integer> selectBrandIdsByCategoryName(@Param("categoryName") String categoryName);

    @Select("SELECT template_id FROM tb_category WHERE name=#{categoryName}")
    List<Integer> selectTemplateIdsByCategoryName(@Param("categoryName") String categoryName);
}
