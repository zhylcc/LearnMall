package com.learn.demo.mall.goods.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.request.PageExampleReq;
import com.learn.demo.mall.goods.dao.BrandMapper;
import com.learn.demo.mall.goods.enums.GoodsErrorCodeEnum;
import com.learn.demo.mall.goods.pojo.BrandPO;
import com.learn.demo.mall.goods.request.BrandExampleReq;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * 品牌管理服务
 * @author zh_cr
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    private Example createExample(BrandExampleReq req) {
        Example example = new Example(BrandPO.class);
        Example.Criteria criteria = example.createCriteria();
        if (req != null) {
            if (StringUtils.isNotBlank(req.getName())) {
                criteria.andLike("name", "%"+req.getName()+"%");
            }
            if (StringUtils.isNotBlank(req.getLetter())) {
                criteria.andLike("letter", "%"+req.getLetter()+"%");
            }
        }
        return example;
    }

    public BrandPO queryBrandByName(String name) {
        Example example = new Example(BrandPO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andEqualTo("name", name);
        }
        return brandMapper.selectOneByExample(example);
    }

    public BrandPO queryBrandById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public Page<BrandPO> queryPageBrandsByExample(PageExampleReq<BrandExampleReq> req) {
        PageHelper.startPage(req.getCurrentPage(), req.getPageSize());
        Example example = createExample(req.getExample());
        return (Page<BrandPO>) brandMapper.selectByExample(example);
    }

    public List<BrandPO> queryBrandsByIds(List<Integer> brandIds) {
        if (CollectionUtils.isEmpty(brandIds)) {
            return Lists.newArrayList();
        }
        return brandMapper.selectByIds(brandIds);
    }

    @Transactional
    public Integer saveBrand(BrandPO brand) {
        brand.setId(null);
        if (Objects.nonNull(queryBrandByName(brand.getName()))) {
            throw new BaseBizException("品牌名重复", GoodsErrorCodeEnum.BIZ_BRAND_WARNING);
        }
        brandMapper.insertSelective(brand);
        return brand.getId();
    }

    public void deleteBrandById(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void updateBrand(BrandPO brand) {
        BrandPO oldBrand = queryBrandByName(brand.getName());
        if (Objects.nonNull(oldBrand) && !oldBrand.getId().equals(brand.getId())) {
            throw new BaseBizException("品牌名重复", GoodsErrorCodeEnum.BIZ_BRAND_WARNING);
        }
        brandMapper.updateByPrimaryKey(brand);
    }
}
