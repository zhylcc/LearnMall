package com.learn.demo.mall.order.service;

import com.learn.demo.mall.order.dao.TaskHisMapper;
import com.learn.demo.mall.order.dao.TaskMapper;
import com.learn.demo.mall.order.pojo.TaskHisPO;
import com.learn.demo.mall.order.pojo.TaskPO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zh_cr
 */
@Service
public class TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskHisMapper taskHisMapper;

    @Transactional
    public void deleteTaskById(Long id) {
        TaskPO task = taskMapper.selectByPrimaryKey(id);
        TaskHisPO taskHis = new TaskHisPO();
        BeanUtils.copyProperties(task, taskHis);
        taskHisMapper.insertSelective(taskHis);
        taskMapper.deleteByPrimaryKey(id);
    }
}
