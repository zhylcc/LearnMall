package com.learn.demo.mall.goods.listener;

import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.goods.pojo.SkuPO;
import com.learn.demo.mall.goods.service.SkuService;
import com.learn.demo.mall.goods.service.SpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品数据监控消息-消费者
 * @author zh_cr
 */
@Component
@Slf4j
public class SkuIndexUpdateConsumer {

    @Resource
    private SpuService spuService;

    @Resource
    private SkuService skuService;

    @RabbitListener(queues = KeyConfigUtil.INDEX_IMPORT_QUEUE)
    public void receiveImportIndexMessage(String message) {
        log.info("收到商品上架消息, spuId: " + message);
        List<SkuPO> skuList = spuService.selectSkusBySpuId(message);
        log.info("包含sku数: " + skuList.size());
        Integer importCnt = skuService.importSkusIndex(skuList);
        log.info("成功新增索引数: " + importCnt);
    }

    @RabbitListener(queues = KeyConfigUtil.INDEX_DELETE_QUEUE)
    public void receiveDeleteIndexMessage(String message) {
        log.info("收到商品下架消息, spuId: " + message);
        List<String> skuIds = spuService.selectSkusBySpuId(message).stream().map(SkuPO::getId).collect(Collectors.toList());
        log.info("包含sku数: " + skuIds.size());
        Integer deleteCnt = skuService.deleteSkusIndex(skuIds);
        log.info("成功删除索引数: " + deleteCnt);
    }
}
