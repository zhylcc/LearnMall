package com.learn.demo.mall.seckill.controller;

import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.common.utils.RandomUtil;
import com.learn.demo.mall.seckill.aspect.AccessLimit;
import com.learn.demo.mall.seckill.enums.SeckillErrorCodeEnum;
import com.learn.demo.mall.seckill.service.SeckillOrderService;
import com.learn.demo.mall.seckill.utils.TokenDecodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀下单
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/seckill/order")
@Slf4j
public class SeckillOrderController {

    @Resource
    private SeckillOrderService seckillOrderService;

    @Resource
    private TokenDecodeUtil tokenDecodeUtil;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;


    @PostMapping("/apply")
    public Result<String> apply(@RequestParam String period, @RequestParam Long seckillGoodsId) {
        String username = tokenDecodeUtil.parseClaims().getUsername();
        return Result.success(seckillOrderService.commit(username, period, seckillGoodsId));
    }

    @PostMapping("/applyRandom")
    public Result<String> applyInSecret(@RequestParam String period, @RequestParam Long seckillGoodsId, @RequestParam String randomCode) {
        String jti = tokenDecodeUtil.parseClaims().getJti();
        String target = (String) redisTemplate.boundValueOps(KeyConfigUtil.getSeckillRandomKeyPrefix() + jti).get();
        if (Objects.isNull(target) || !target.equals(randomCode)) {
            throw new BaseBizException("访问随机码错误", SeckillErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        String username = tokenDecodeUtil.parseClaims().getUsername();
        return Result.success(seckillOrderService.commit(username, period, seckillGoodsId));
    }

    @GetMapping("/random")
    @AccessLimit
    public Result<String> getRandomCode() {
        String randomCode = RandomUtil.getRandomString();
        String jti = tokenDecodeUtil.parseClaims().getJti();
        redisTemplate.boundValueOps(KeyConfigUtil.getSeckillRandomKeyPrefix() + jti).set(randomCode, KeyConfigUtil.getSeckillRandomKeyExpire(), TimeUnit.SECONDS);
        return Result.success(randomCode);
    }
}
