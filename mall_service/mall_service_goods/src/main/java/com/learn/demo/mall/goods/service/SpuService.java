package com.learn.demo.mall.goods.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.goods.dao.*;
import com.learn.demo.mall.goods.enums.GoodsErrorCodeEnum;
import com.learn.demo.mall.goods.enums.SpuStatusEnum;
import com.learn.demo.mall.goods.pojo.*;
import com.learn.demo.mall.goods.utils.SnowflakeIdUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商品管理服务
 * @author zh_cr
 */
@Service
public class SpuService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private SnowflakeIdUtil snowflakeIdUtil;

    private void saveSpuFromGoods(GoodsPO goods) {
        SpuPO spu = goods.getSpu();
        spu.setId(String.valueOf(snowflakeIdUtil.nextId()));
        spu.setIsDelete(SpuStatusEnum.NOT_DELETE.getValue());
        spu.setIsMarketable(SpuStatusEnum.NOT_MARKETABLE.getValue());
        spu.setStatus(SpuStatusEnum.NOT_CHECKED.getValue());
        spuMapper.insertSelective(spu);
    }

    private void saveCategoryBrandFromGoods(GoodsPO goods) {
        SpuPO spu = goods.getSpu();
        CategoryBrandPO categoryBrand = new CategoryBrandPO();
        categoryBrand.setCategoryId(spu.getCategory3Id());
        categoryBrand.setBrandId(spu.getBrandId());
        if (categoryBrandMapper.selectCount(categoryBrand) == 0) {
            categoryBrandMapper.insert(categoryBrand);
        }
    }

    private void saveSkusFromGoods(GoodsPO goods) {
        SpuPO spu = goods.getSpu();
        String spuName = spu.getName();
        String spuId = spu.getId();
        CategoryPO category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        BrandPO brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        List<SkuPO> skus = goods.getSkuList();
        if (CollectionUtils.isEmpty(skus)) {
            return;
        }
        skus.forEach(sku -> {
            sku.setId(String.valueOf(snowflakeIdUtil.nextId()));
            if (StringUtils.isBlank(sku.getSpec())) {
                sku.setSpec("{}");
            }
            Map<String, Object> specMap = JSON.parseObject(sku.getSpec(), new TypeReference<Map<String, Object>>(){});
            String joinedSpec = "";
            if (!CollectionUtils.isEmpty(specMap)) {
                joinedSpec = StringUtils.join(specMap.values(), " ");
            }
            sku.setName(spuName + joinedSpec);
            sku.setSpuId(spuId);
            sku.setCreateTime(LocalDateTime.now());
            sku.setUpdateTime(LocalDateTime.now());
            if (Objects.nonNull(category)) {
                sku.setCategoryId(category.getId());
                sku.setCategoryName(category.getName());
            }
            if (Objects.nonNull(brand)) {
                sku.setBrandName(brand.getName());
            }
            skuMapper.insertSelective(sku);
        });
    }

    @Transactional
    public String saveGoods(GoodsPO goods) {
        // 保存spu
        saveSpuFromGoods(goods);
        // 关联分类和品牌
        saveCategoryBrandFromGoods(goods);
        // 保存sku列表
        saveSkusFromGoods(goods);
        return goods.getSpu().getId();
    }


    @Transactional
    public void deleteGoodsById(String id) {
        SpuPO spu = spuMapper.selectByPrimaryKey(id);
        // （逻辑）删除前判断商品是否存在且处于下架状态
        if (Objects.isNull(spu) || Objects.equals(spu.getIsMarketable(), SpuStatusEnum.MARKETABLE.getValue())) {
            throw new BaseBizException("当前商品状态不可删除", GoodsErrorCodeEnum.BIZ_SPU_CANNOT_DELETE);
        }
        spu.setIsDelete(SpuStatusEnum.DELETE.getValue());
        spu.setStatus(SpuStatusEnum.NOT_CHECKED.getValue());
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    public List<SkuPO> selectSkusBySpuId(String spuId) {
        Example example = new Example(SkuPO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", spuId);
        return skuMapper.selectByExample(example);
    }

    public SpuPO queryById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    public void toggleMarketable(String id, String marketable) {
        SpuPO example = spuMapper.selectByPrimaryKey(id);
        if (Objects.isNull(example)) {
            return;
        }
        example.setIsMarketable(marketable);
        spuMapper.updateByPrimaryKeySelective(example);
    }
}
