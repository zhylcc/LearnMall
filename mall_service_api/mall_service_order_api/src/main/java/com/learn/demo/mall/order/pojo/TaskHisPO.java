package com.learn.demo.mall.order.pojo;


import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tb_task_his")
public class TaskHisPO {

	/**
	 * 任务id
	 */
	@Id
	private Long id;

	private Date createTime;

	private Date updateTime;

	private Date deleteTime;

	/**
	 * 任务类型
	 */
	private String taskType;

	/**
	 * 交换机名称
	 */
	private String mqExchange;

	/**
	 * routingkey
	 */
	private String mqRoutingkey;

	/**
	 * 任务请求的内容
	 */
	private String requestBody;

	/**
	 * 任务状态
	 */
	private String status;

	/**
	 * 任务错误信息
	 */
	private String errormsg;


}
