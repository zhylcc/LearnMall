package com.learn.demo.mall.monitor.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.monitor.enums.SpuMarketableRoutingKeyEnum;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品数据监控消息-生产者
 * @author zh_cr
 */
@CanalEventListener
public class SkuIndexUpdateProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    private static final String COL_MARKETABLE = "is_marketable";
    private static final String COL_ID = "id";

    @ListenPoint(schema = "mall_goods", table = "tb_spu")
    public void onSpuMarketableToggle(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        // 更改之前的数据
        Map<String, String> oldData = new HashMap<>();
        rowData.getBeforeColumnsList().forEach(column -> oldData.put(column.getName(), column.getValue()));
        // 更改之后的数据
        Map<String, String> newData = new HashMap<>();
        rowData.getAfterColumnsList().forEach(column -> newData.put(column.getName(), column.getValue()));
        // 生产消息
        if (SpuMarketableRoutingKeyEnum.UP.getStatus().equals(Integer.valueOf(newData.get(COL_MARKETABLE)))
                && SpuMarketableRoutingKeyEnum.DOWN.getStatus().equals(Integer.valueOf(oldData.get(COL_MARKETABLE)))) {
            // 上架
            rabbitTemplate.convertAndSend(KeyConfigUtil.SPU_MARKETABLE_EXCHANGE, KeyConfigUtil.INDEX_IMPORT_KEY, newData.get(COL_ID));
        }
        if (SpuMarketableRoutingKeyEnum.DOWN.getStatus().equals(Integer.valueOf(newData.get(COL_MARKETABLE)))
                && SpuMarketableRoutingKeyEnum.UP.getStatus().equals(Integer.valueOf(oldData.get(COL_MARKETABLE)))) {
            // 下架
            rabbitTemplate.convertAndSend(KeyConfigUtil.SPU_MARKETABLE_EXCHANGE, KeyConfigUtil.INDEX_DELETE_KEY, newData.get(COL_ID));
        }
    }
}
