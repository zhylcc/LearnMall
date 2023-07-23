package com.learn.demo.mall.goods.dao;

import com.learn.demo.mall.goods.pojo.BrandPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 品牌管理通用mapper
 * @author zh_cr
 */
@Repository
public interface BrandMapper extends Mapper<BrandPO> {

    @Select("<script> " +
            "SELECT id,name,image,letter,seq FROM tb_brand \n" +
            "<when test='brandIds!=null'> " +
            "WHERE id in " +
            "<foreach item='item' index='index' collection='brandIds' open='(' separator=',' close=')'>" +
            "    #{item}" +
            "</foreach>" +
            "</when> " +
            "</script>")
    List<BrandPO> selectByIds(@Param("brandIds") List<Integer> brandIds);
}
