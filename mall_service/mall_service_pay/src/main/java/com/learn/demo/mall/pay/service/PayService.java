package com.learn.demo.mall.pay.service;

import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.pay.enums.TradeStatusEnum;
import com.learn.demo.mall.pay.response.CloseOrderResp;
import com.learn.demo.mall.pay.response.QueryOrderResp;
import com.learn.demo.mall.pay.utils.SnowflakeIdUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zh_cr
 */
@Service
public class PayService {

    @Resource
    private SnowflakeIdUtil snowflakeIdUtil;


    public QueryOrderResp queryOrderById(String orderId) {
        // todo 仅模拟
        QueryOrderResp resp = new QueryOrderResp();
        resp.setOrderId(orderId);
        resp.setTransactionId(String.valueOf(snowflakeIdUtil.nextId()));
        resp.setTradeStatus(Objects.requireNonNull(TradeStatusEnum.of(KeyConfigUtil.getDefaultTradeStatus())).getStatus());
        return resp;
    }

    public CloseOrderResp closeOrderById(String orderId) {
        // todo 仅模拟
        CloseOrderResp resp = new CloseOrderResp();
        resp.setOrderId(orderId);
        return resp;
    }
}
