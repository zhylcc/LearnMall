package com.learn.demo.mall.goods.controller;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.pojo.SpecPO;
import com.learn.demo.mall.goods.service.CategoryService;
import com.learn.demo.mall.goods.service.TemplateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 规格管理
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/goods/spec")
public class SpecController {

    @Resource
    private TemplateService templateService;

    @Resource
    private CategoryService categoryService;

    @GetMapping("/category/{categoryName}")
    public Result<List<SpecPO>> querySpecsByCategoryName(@PathVariable String categoryName) {
        List<Integer> templateIds = categoryService.queryTemplateIdsByCategoryName(categoryName);
        return Result.success(templateService.querySpecsByTemplateIds(templateIds));
    }
}
