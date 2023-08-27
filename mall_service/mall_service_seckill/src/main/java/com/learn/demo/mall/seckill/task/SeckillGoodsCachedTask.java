package com.learn.demo.mall.seckill.task;

import com.learn.demo.mall.common.utils.DateUtil;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.seckill.dao.SeckillGoodsMapper;
import com.learn.demo.mall.seckill.enums.SeckillGoodsStatusEnum;
import com.learn.demo.mall.seckill.pojo.SeckillGoodsPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 定时任务：秒杀商品缓存预热
 * @author zh_cr
 */
@Component
@Slf4j
public class SeckillGoodsCachedTask {

    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    private static final String NAME_STATUS = "status";
    private static final String NAME_STOCK_COUNT = "stockCount";
    private static final String NAME_START_TIME = "startTime";
    private static final String NAME_END_TIME = "endTime";
    private static final String NAME_ID = "id";

    @Scheduled(cron = "0/30 * * * * ?")
    public void cachedSeckillGoods() {
        log.info("cachedSeckillGoods 执行时间：" + DateUtil.DATE_FORMAT.format(new Date()));

        // 获取当前时间后5个时间段
        List<Date> dateMenus = DateUtil.getDateMenus();
        dateMenus.forEach(date -> {
            // 从数据库查询秒杀商品
            Example example = new Example(SeckillGoodsPO.class);
            Example.Criteria criteria = example.createCriteria();
            // 1. 上架、库存>0、在当前时间段
            criteria.andEqualTo(NAME_STATUS, SeckillGoodsStatusEnum.APPROVED.getValue());
            criteria.andGreaterThan(NAME_STOCK_COUNT, 0);
            criteria.andGreaterThanOrEqualTo(NAME_START_TIME, DateUtil.DATE_FORMAT.format(date));
            criteria.andLessThan(NAME_END_TIME, DateUtil.DATE_FORMAT.format(DateUtil.addDateHour(date, 2)));
            // 2. 过滤缓存中存在的秒杀商品
            String seckillGoodsKey = KeyConfigUtil.getSeckillGoodsKeyPrefix() + DateUtil.date2Str(date);
            Set<Object> existedIds = redisTemplate.boundHashOps(seckillGoodsKey).keys();
            if (!CollectionUtils.isEmpty(existedIds)) {
                criteria.andNotIn(NAME_ID, existedIds);
            }
            List<SeckillGoodsPO> seckillGoodsList = seckillGoodsMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(seckillGoodsList)) {
                return;
            }

            // 添加到缓存
            seckillGoodsList.forEach(seckillGoods -> {
                redisTemplate.boundHashOps(seckillGoodsKey).put(seckillGoods.getId(), seckillGoods);
                redisTemplate.boundValueOps(KeyConfigUtil.getSeckillGoodsStockKeyPrefix() + seckillGoods.getId()).set(seckillGoods.getStockCount());
            });
        });
    }
}
