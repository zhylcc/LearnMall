package com.learn.demo.mall.order.listener;

import com.alibaba.fastjson.JSON;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.order.dao.TaskMapper;
import com.learn.demo.mall.order.pojo.TaskPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 定时任务：查询增加积分
 * @author zh_cr
 */
@Component
@Slf4j
public class AddPointsQueryTask {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 秒 分 时 日 月 星期几 (年)
     * 以下定义了每分钟执行一次的定时任务
     */
    @Scheduled(cron = "* 0/1 * * * ?")
    public void queryTask() {
        // 获取未处理的任务
        List<TaskPO> tasks = taskMapper.queryTaskBeforeNow(new Date());

        tasks.forEach(task -> {
            // 将任务数据发送到消息队列
            rabbitTemplate.convertAndSend(KeyConfigUtil.USER_ADD_POINTS_EXCHANGE, KeyConfigUtil.TO_ADD_POINTS_KEY, JSON.toJSONString(task));
            log.info("发送任务：" + task.getId());
        });
    }
}
