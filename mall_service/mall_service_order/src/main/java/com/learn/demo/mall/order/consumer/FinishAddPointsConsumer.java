package com.learn.demo.mall.order.consumer;

import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.order.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zh_cr
 */
@Component
@Slf4j
public class FinishAddPointsConsumer {

    @Resource
    private TaskService taskService;

    @RabbitListener(queues = KeyConfigUtil.FINISH_ADD_POINTS_QUEUE)
    public void receiveFinishAddPointsMessage(String message) {
        log.info("接收到完成积分增加消息");
        // 项任务历史记录中插入数据，并删除任务记录
        taskService.deleteTaskById(Long.valueOf(message));
    }
}
