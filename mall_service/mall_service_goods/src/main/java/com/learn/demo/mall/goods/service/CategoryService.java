package com.learn.demo.mall.goods.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.learn.demo.mall.common.request.PageExampleReq;
import com.learn.demo.mall.goods.dao.CategoryMapper;
import com.learn.demo.mall.goods.pojo.CategoryPO;
import com.learn.demo.mall.goods.request.CategoryExampleReq;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * 分类管理服务
 * @author zh_cr
 */
@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    private Example createExample(CategoryExampleReq req) {
        Example example = new Example(CategoryPO.class);
        Example.Criteria criteria = example.createCriteria();
        if (req != null) {
            if (StringUtils.isNotBlank(req.getName())) {
                criteria.andLike("name", "%"+req.getName()+"%");
            }
            if (Objects.nonNull(req.getParentId())) {
                criteria.andEqualTo("parentId", req.getParentId());
            }
            if (Objects.nonNull(req.getTemplateId())) {
                criteria.andEqualTo("templateId", req.getTemplateId());
            }
        }
        return example;
    }

    public CategoryPO queryCategoryById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public Page<CategoryPO> queryPageBrandsByExample(PageExampleReq<CategoryExampleReq> req) {
        PageHelper.startPage(req.getCurrentPage(), req.getPageSize());
        Example example = createExample(req.getExample());
        return (Page<CategoryPO>) categoryMapper.selectByExample(example);
    }

    public List<Integer> queryBrandIdsByCategoryName(String categoryName) {
        if (StringUtils.isBlank(categoryName)) {
            return Lists.newArrayList();
        }
        return categoryMapper.selectBrandIdsByCategoryName(categoryName);
    }

    public List<Integer> queryTemplateIdsByCategoryName(String categoryName) {
        if (StringUtils.isBlank(categoryName)) {
            return Lists.newArrayList();
        }
        return categoryMapper.selectTemplateIdsByCategoryName(categoryName);
    }
}
