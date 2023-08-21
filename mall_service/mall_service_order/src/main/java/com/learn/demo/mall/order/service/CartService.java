package com.learn.demo.mall.order.service;

import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.goods.feign.SkuFeign;
import com.learn.demo.mall.goods.feign.SpuFeign;
import com.learn.demo.mall.goods.pojo.SkuPO;
import com.learn.demo.mall.goods.pojo.SpuPO;
import com.learn.demo.mall.order.enums.OrderErrorCodeEnum;
import com.learn.demo.mall.order.pojo.OrderItemPO;
import com.learn.demo.mall.order.request.AddCartReq;
import com.learn.demo.mall.order.response.ListCartResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zh_cr
 */
@Service
public class CartService {

    @Value("${cart-key-prefix}")
    private String cartKeyPrefix;

    @Resource
    private RedisTemplate<String, Map<String, OrderItemPO>> redisTemplate;

    @Resource
    private SkuFeign skuFeign;

    @Resource
    private SpuFeign spuFeign;

    public String add(AddCartReq req) {
        String key = cartKeyPrefix + req.getUsername();
        OrderItemPO orderItem = (OrderItemPO) redisTemplate.boundHashOps(key).get(req.getSkuId());
        // 没有缓存数据，新增购物车
        if (Objects.isNull(orderItem)) {
            Result<SkuPO> skuResult = skuFeign.queryById(req.getSkuId());
            if (Objects.isNull(skuResult.getData())) {
                throw new BaseBizException("商品sku不存在", OrderErrorCodeEnum.GOODS_NOT_EXIST);
            }
            SkuPO sku = skuResult.getData();
            Result<SpuPO> spuResult = spuFeign.queryById(sku.getSpuId());
            if (Objects.isNull(spuResult.getData())) {
                throw new BaseBizException("商品spu不存在", OrderErrorCodeEnum.GOODS_NOT_EXIST);
            }
            SpuPO spu = spuResult.getData();
            int num = req.getNum();
            orderItem = OrderItemPO.builder()
                    .skuId(sku.getId()).name(sku.getName()).price(sku.getPrice()).image(sku.getImage())
                    .spuId(spu.getId()).categoryId1(spu.getCategory1Id()).categoryId2(spu.getCategory2Id()).categoryId3(spu.getCategory3Id())
                    .num(num).money(num*sku.getPrice()).payMoney(num*sku.getPrice()).weight(num*sku.getWeight())
                    .build();
        }
        // 有缓存数据，修改
        else {
            int num = req.getNum() + orderItem.getNum();
            orderItem.setNum(num);
            if (orderItem.getNum() <= 0) {
                redisTemplate.boundHashOps(key).delete(orderItem.getSkuId());
                return "";
            }
            orderItem.setMoney(num * orderItem.getPrice());
            orderItem.setPayMoney(num * orderItem.getPrice());
        }
        // 重新写入缓存
        redisTemplate.boundHashOps(key).put(req.getSkuId(), orderItem);
        return key;
    }

    public ListCartResp list(String username) {
        ListCartResp resp = new ListCartResp();
        List<OrderItemPO> items = Objects.requireNonNull(redisTemplate.boundHashOps(cartKeyPrefix + username).values())
                .stream()
                .map(item->(OrderItemPO)item)
                .collect(Collectors.toList());
        resp.setItems(items);
        Integer total = 0;
        Integer money = 0;
        for (OrderItemPO item : items) {
            total += item.getNum();
            money += item.getMoney();
        }
        resp.setTotal(total);
        resp.setMoney(money);
        return resp;
    }
}
