package com.learn.demo.mall.goods.controller;

import com.github.pagehelper.Page;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.request.PageExampleReq;
import com.learn.demo.mall.common.response.PageResult;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.enums.GoodsErrorCodeEnum;
import com.learn.demo.mall.goods.pojo.BrandPO;
import com.learn.demo.mall.goods.request.BrandExampleReq;
import com.learn.demo.mall.goods.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 品牌管理
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/goods/brand")
public class BrandController {

    @Resource
    private BrandService brandService;

    @GetMapping("/{id}")
    public Result<BrandPO> queryBrandById(@PathVariable Integer id) {
        return Result.success(brandService.queryBrandById(id));
    }

    @PostMapping("/{page}/{size}")
    public PageResult<BrandPO> queryPageBrandsByExample(@RequestBody BrandExampleReq req, @PathVariable Integer page, @PathVariable Integer size) {
        if (page <= 0) {
            throw new BaseBizException("页码参数必须大于0", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (size <= 0) {
            throw new BaseBizException("每页条数参数必须大于0", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        Page<BrandPO> brands = brandService.queryPageBrandsByExample(new PageExampleReq<>(req, page, size));
        return PageResult.success(brands.getTotal(), brands.getResult());
    }

    @PostMapping
    public Result<Integer> saveBrand(@RequestBody BrandPO brand) {
        if (StringUtils.isBlank(brand.getName())) {
            throw new BaseBizException("品牌名称不能为空", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (!(StringUtils.isAlpha(brand.getLetter()) && brand.getLetter().length() == 1)) {
            throw new BaseBizException("品牌首字母必须为A-Z", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        return Result.success(brandService.saveBrand(brand));
    }

    @PutMapping("/{id}")
    public Result<Void> updateBrand(@PathVariable Integer id, @RequestBody BrandPO brand) {
        brand.setId(id);
        if (StringUtils.isBlank(brand.getName())) {
            throw new BaseBizException("品牌名称不能为空", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (!(StringUtils.isAlpha(brand.getLetter()) && brand.getLetter().length() == 1)) {
            throw new BaseBizException("品牌首字母必须为A-Z", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        brandService.updateBrand(brand);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteBrandById(@PathVariable Integer id) {
        brandService.deleteBrandById(id);
        return Result.success();
    }
}
