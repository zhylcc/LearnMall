package com.learn.demo.mall.goods.controller;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.enums.GoodsErrorCodeEnum;
import com.learn.demo.mall.goods.exception.GoodsException;
import com.learn.demo.mall.goods.pojo.GoodsPO;
import com.learn.demo.mall.goods.service.SpuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 商品管理
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/goods/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @PostMapping()
    public Result<String> saveGoods(@RequestBody GoodsPO goods) {
        if (!(Objects.nonNull(goods.getSpu()) && StringUtils.isNotBlank(goods.getSpu().getName()))) {
            throw new GoodsException("商品SPU名不能为空", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (!CollectionUtils.isEmpty(goods.getSkuList())) {
            goods.getSkuList().forEach(sku -> {
                if (StringUtils.isBlank(sku.getSn())) {
                    throw new GoodsException("商品条码不能为空", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
                }
                if (Objects.isNull(sku.getNum())) {
                    throw new GoodsException("商品库存不能为空", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
                }
                if (Objects.isNull(sku.getPrice())) {
                    throw new GoodsException("商品价格不能为空", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
                }
            });
        }
        return Result.success(spuService.saveGoods(goods));
    }

    @DeleteMapping("/{id}")
    public Result<Object> deleteGoodsById(@PathVariable String id) {
        spuService.deleteGoodsById(id);
        return Result.success();
    }
}
