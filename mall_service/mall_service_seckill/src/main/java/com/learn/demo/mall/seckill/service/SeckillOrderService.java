package com.learn.demo.mall.seckill.service;

import com.alibaba.fastjson.JSON;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.seckill.dao.SeckillGoodsMapper;
import com.learn.demo.mall.seckill.dao.SeckillOrderMapper;
import com.learn.demo.mall.seckill.enums.SeckillErrorCodeEnum;
import com.learn.demo.mall.seckill.enums.SeckillOrderStatusEnum;
import com.learn.demo.mall.seckill.utils.MessageConfirmUtil;
import com.learn.demo.mall.seckill.pojo.SeckillGoodsPO;
import com.learn.demo.mall.seckill.pojo.SeckillOrderPO;
import com.learn.demo.mall.seckill.utils.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zh_cr
 */
@Service
@Slf4j
public class SeckillOrderService {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private MessageConfirmUtil messageConfirmUtil;

    @Resource
    private SnowflakeIdUtil snowflakeIdUtil;

    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    public String commit(String username, String period, Long seckillGoodsId) {
        // 同一用户一定时间内只允许对同一商品秒杀一次
        if (!filter4RepeatCommit(username, seckillGoodsId)) {
            throw new BaseBizException("商品秒杀冷却中", SeckillErrorCodeEnum.COMMIT_GOODS_FROZEN);
        }

        // 同一用户只允许对同一商品完成一单秒杀
        if (!filter4RepeatApply(username, seckillGoodsId)) {
            throw new BaseBizException("已参与过该商品秒杀", SeckillErrorCodeEnum.COMMIT_GOODS_REPEAT);
        }

        String seckillGoodsKey = KeyConfigUtil.getSeckillGoodsKeyPrefix() + period;
        String seckillGoodsStockKey = KeyConfigUtil.getSeckillGoodsStockKeyPrefix() + seckillGoodsId;

        // 1. 获取redis中的商品和库存信息
        SeckillGoodsPO seckillGoods = (SeckillGoodsPO) redisTemplate.boundHashOps(seckillGoodsKey).get(seckillGoodsId);
        String stockStr = (String) redisTemplate.boundValueOps(seckillGoodsStockKey).get();
        if (Objects.isNull(seckillGoods) || Objects.isNull(stockStr) || Integer.parseInt(stockStr) <= 0) {
            // 库存不足
            throw new BaseBizException("商品库存不足", SeckillErrorCodeEnum.COMMIT_GOODS_LACK);
        }

        // 2. redis预扣减库存, -1
        Long remainStock = redisTemplate.boundValueOps(seckillGoodsStockKey).decrement();
        if (Objects.isNull(remainStock) || remainStock <= 0) {
            // 售罄
            redisTemplate.boundHashOps(seckillGoodsKey).delete(seckillGoodsId);
            redisTemplate.delete(seckillGoodsStockKey);
        }

        // 3. 异步下单，发送消息
        SeckillOrderPO seckillOrder = SeckillOrderPO.builder()
                .id(snowflakeIdUtil.nextId())
                .seckillId(seckillGoodsId)
                .money(seckillGoods.getCostPrice())
                .userId(username)
                .sellerId(seckillGoods.getSellerId())
                .createTime(new Date())
                .status(SeckillOrderStatusEnum.NOT_COMPLETED.getValue())
                .build();
        messageConfirmUtil.sendMessageWithConfirm("", KeyConfigUtil.SECKILL_ORDER_QUEUE, JSON.toJSONString(seckillOrder));
        return String.valueOf(seckillOrder.getId());
    }

    @Transactional
    public String apply(SeckillOrderPO seckillOrder) {
        int result = seckillGoodsMapper.decreaseStockById(seckillOrder.getSeckillId());
        if (result <= 0) {
            return null;
        }
        result = seckillOrderMapper.insertSelective(seckillOrder);
        if (result <= 0) {
            return null;
        }
        return String.valueOf(seckillOrder.getId());
    }

    private boolean filter4RepeatCommit(String username, Long seckillGoodsId) {
        String key = KeyConfigUtil.getSeckillUserGoodsKeyPrefix() + username + "_" + seckillGoodsId;
        Long commitTimes = Objects.requireNonNull(redisTemplate.boundValueOps(key).increment());
        if (commitTimes == 1L) {
            // 第一次秒杀，设置冷却
            redisTemplate.expire(key, KeyConfigUtil.getSeckillUserGoodsKeyExpire(), TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    private boolean filter4RepeatApply(String username, Long seckillGoodsId) {
        Example example = new Example(SeckillOrderPO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", username);
        criteria.andEqualTo("seckillId", seckillGoodsId);
        SeckillOrderPO history = seckillOrderMapper.selectOneByExample(example);
        return Objects.isNull(history);
    }
}
