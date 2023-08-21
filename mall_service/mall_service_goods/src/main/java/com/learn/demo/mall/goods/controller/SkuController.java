package com.learn.demo.mall.goods.controller;

import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.enums.GoodsErrorCodeEnum;
import com.learn.demo.mall.goods.pojo.SkuPO;
import com.learn.demo.mall.goods.request.SkuExampleESReq;
import com.learn.demo.mall.goods.service.SkuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * SKU管理
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/goods/sku")
public class SkuController {

    @Resource
    private SkuService skuService;

    @PostMapping("/index")
    public Result<Void> createIndex() {
        skuService.createIndex();
        return Result.success();
    }

    @DeleteMapping("/index")
    public Result<Void> deleteIndex() {
        skuService.deleteIndex();
        return Result.success();
    }

    @PostMapping("/index/all")
    public Result<Integer> importAll() {
        return Result.success(skuService.importAllSkuIndex());
    }

    @PostMapping("/index/{page}/{size}")
    public Result<Map<String, Object>> search(@RequestBody SkuExampleESReq req, @PathVariable Integer page, @PathVariable Integer size) {
        if (page <= 0) {
            throw new BaseBizException("页码参数必须大于0", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (size <= 0) {
            throw new BaseBizException("每页条数参数必须大于0", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (StringUtils.isBlank(req.getName())) {
            throw new BaseBizException("索引时商品名称不能为空", GoodsErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        return Result.success(skuService.search(req, page, size));
    }

    @GetMapping("/{id}")
    public Result<SkuPO> queryById(@PathVariable String id) {
        return Result.success(skuService.queryById(id));
    }

    @PostMapping("/reduce")
    public Result<Integer> reduce(@RequestParam String username) {
        return Result.success(skuService.reduce(username));
    }
}
