package com.learn.demo.mall.goods.controller;

import com.github.pagehelper.Page;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.request.PageExampleReq;
import com.learn.demo.mall.common.response.PageResult;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.enums.GoodsErrorCodeEnum;
import com.learn.demo.mall.goods.pojo.BrandPO;
import com.learn.demo.mall.goods.pojo.CategoryPO;
import com.learn.demo.mall.goods.pojo.SpecPO;
import com.learn.demo.mall.goods.request.CategoryExampleReq;
import com.learn.demo.mall.goods.service.BrandService;
import com.learn.demo.mall.goods.service.CategoryService;
import com.learn.demo.mall.goods.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/goods/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private TemplateService templateService;

    @GetMapping("/{id}")
    public Result<CategoryPO> queryCategoryById(@PathVariable Integer id) {
        return Result.success(categoryService.queryCategoryById(id));
    }

    @PostMapping("/{page}/{size}")
    public PageResult<CategoryPO> queryCategoriesByExample(@RequestBody CategoryExampleReq req, @PathVariable Integer page, @PathVariable Integer size) {
        if (page <= 0) {
            throw new BaseBizException("页码参数不正确", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (size <= 0) {
            throw new BaseBizException("页容量参数不正确", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        Page<CategoryPO> categories = categoryService.queryPageBrandsByExample(new PageExampleReq<>(req, page, size));
        return PageResult.success(categories.getTotal(), categories.getResult());
    }

    @GetMapping("/brand/{categoryName}")
    public Result<List<BrandPO>> queryBrandsByCategoryName(@PathVariable String categoryName) {
        List<Integer> brandIds = categoryService.queryBrandIdsByCategoryName(categoryName);
        return Result.success(brandService.queryBrandsByIds(brandIds));
    }

    @GetMapping("/spec/{categoryName}")
    public Result<List<SpecPO>> querySpecsByCategoryName(@PathVariable String categoryName) {
        List<Integer> templateIds = categoryService.queryTemplateIdsByCategoryName(categoryName);
        return Result.success(templateService.querySpecsByTemplateIds(templateIds));
    }
}
