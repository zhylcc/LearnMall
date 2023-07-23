package com.learn.demo.mall.goods.controller;

import com.github.pagehelper.Page;
import com.learn.demo.mall.common.request.PageExampleReq;
import com.learn.demo.mall.common.response.PageResult;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.enums.GoodsErrorCodeEnum;
import com.learn.demo.mall.goods.exception.GoodsException;
import com.learn.demo.mall.goods.pojo.BrandPO;
import com.learn.demo.mall.goods.request.BrandExampleReq;
import com.learn.demo.mall.goods.service.BrandService;
import com.learn.demo.mall.goods.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌管理
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/goods/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public Result<BrandPO> queryBrandById(@PathVariable Integer id) {
        return Result.success(brandService.queryBrandById(id));
    }

    @PostMapping("/search")
    public Result<List<BrandPO>> queryBrandsByExample(@RequestBody BrandExampleReq req) {
        return Result.success(brandService.queryBrandsByExample(req));
    }

    @PostMapping("/search/{page}/{size}")
    public PageResult<BrandPO> queryPageBrandsByExample(@RequestBody BrandExampleReq req, @PathVariable Integer page, @PathVariable Integer size) {
        if (page <= 0) {
            throw new GoodsException("页码参数不正确", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (size <= 0) {
            throw new GoodsException("页容量参数不正确", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        Page<BrandPO> brands = brandService.queryPageBrandsByExample(new PageExampleReq<>(req, page, size));
        return PageResult.success(brands.getTotal(), brands.getResult());
    }

    @PostMapping("/save")
    public Result<Integer> saveBrand(@RequestBody BrandPO brand) {
        if (StringUtils.isBlank(brand.getName())) {
            throw new GoodsException("品牌名称不能为空", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (!(StringUtils.isAlpha(brand.getLetter()) && brand.getLetter().length() == 1)) {
            throw new GoodsException("品牌首字母必须为A-Z", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        return Result.success(brandService.saveBrand(brand));
    }

    @DeleteMapping("/{id}")
    public Result<Object> deleteBrandById(@PathVariable Integer id) {
        brandService.deleteBrandById(id);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Object> updateBrand(@PathVariable Integer id, @RequestBody BrandPO brand) {
        brand.setId(id);
        if (StringUtils.isBlank(brand.getName())) {
            throw new GoodsException("品牌名称不能为空", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (!(StringUtils.isAlpha(brand.getLetter()) && brand.getLetter().length() == 1)) {
            throw new GoodsException("品牌首字母必须为A-Z", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        brandService.updateBrand(brand);
        return Result.success();
    }

    @GetMapping("/category/{categoryName}")
    public Result<List<BrandPO>> queryBrandsByCategoryName(@PathVariable String categoryName) {
        List<Integer> brandIds = categoryService.queryBrandIdsByCategoryName(categoryName);
        return Result.success(brandService.queryBrandsByIds(brandIds));
    }
}
