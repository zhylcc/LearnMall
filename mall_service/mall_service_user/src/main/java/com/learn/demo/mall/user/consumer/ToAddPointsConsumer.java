package com.learn.demo.mall.user.consumer;

import com.alibaba.fastjson.JSON;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.order.pojo.TaskPO;
import com.learn.demo.mall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 增加积分消息消费者
 *
 * @author zh_cr
 */
@Component
@Slf4j
public class ToAddPointsConsumer {

    @Resource
    private UserService userService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = KeyConfigUtil.TO_ADD_POINTS_QUEUE)
    public void receiveToAddPointsMessage(String message) {
        log.info("收到增加积分消息");
        TaskPO task = JSON.parseObject(message, TaskPO.class);
        if (Objects.isNull(task) || StringUtils.isEmpty(task.getRequestBody())) {
            log.info("任务消息为空");
            return;
        }
        // 更新用户积分（本地事务）
        int result = userService.updatePointsByTask(task);
        if (result == 0) {
            log.info("任务正在处理或任务数据为空");
            return;
        }
        // 发送积分增加完成消息
        rabbitTemplate.convertAndSend(KeyConfigUtil.USER_ADD_POINTS_EXCHANGE, KeyConfigUtil.FINISH_ADD_POINTS_KEY, String.valueOf(task.getId()));
        log.info("发送积分增加完成消息");
    }

}
