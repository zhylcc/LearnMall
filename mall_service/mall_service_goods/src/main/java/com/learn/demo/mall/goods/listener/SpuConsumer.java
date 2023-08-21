package com.learn.demo.mall.goods.listener;

import com.learn.demo.mall.goods.pojo.SkuPO;
import com.learn.demo.mall.goods.service.SkuService;
import com.learn.demo.mall.goods.service.SpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品数据监控消息-消费者
 * @author zh_cr
 */
@Component
@Slf4j
public class SpuConsumer {

    @Resource
    private SpuService spuService;

    @Resource
    private SkuService skuService;

    @RabbitListener(queues = "${consumer.spu.queue.importIndex}")
    public void importIndex(String spuId) {
        log.info("收到商品上架消息, spuId: " + spuId);
        List<SkuPO> skuList = spuService.selectSkusBySpuId(spuId);
        log.info("包含sku数: " + skuList.size());
        Integer importCnt = skuService.importSkusIndex(skuList);
        log.info("成功新增索引数: " + importCnt);
    }

    @RabbitListener(queues = "${consumer.spu.queue.deleteIndex}")
    public void deleteIndex(String spuId) {
        log.info("收到商品下架消息, spuId: " + spuId);
        List<String> skuIds = spuService.selectSkusBySpuId(spuId).stream().map(SkuPO::getId).collect(Collectors.toList());
        log.info("包含sku数: " + skuIds.size());
        Integer deleteCnt = skuService.deleteSkusIndex(skuIds);
        log.info("成功删除索引数: " + deleteCnt);
    }
}
