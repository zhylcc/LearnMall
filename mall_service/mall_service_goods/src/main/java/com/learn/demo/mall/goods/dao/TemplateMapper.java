package com.learn.demo.mall.goods.dao;

import com.learn.demo.mall.goods.pojo.BrandPO;
import com.learn.demo.mall.goods.pojo.SpecPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 模板管理通用mapper
 * @author zh_cr
 */
@Repository
public interface TemplateMapper extends Mapper<BrandPO> {

    @Select("<script> " +
            "SELECT id,name,options,seq,template_id FROM tb_spec \n" +
            "<when test='templateIds!=null'> " +
            "WHERE template_id in " +
            "<foreach item='item' index='index' collection='templateIds' open='(' separator=',' close=')'>" +
            "    #{item}" +
            "</foreach>" +
            "</when> " +
            "</script>")
    List<SpecPO> selectSpecsByTemplateIds(@Param("templateIds") List<Integer> templateIds);
}
