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
@Table(name = "tb_order_log")
public class OrderLogPO {

	/**
	 * ID
	 */
	@Id
	private String id;

	/**
	 * 操作员
	 */
	private String operater;

	/**
	 * 操作时间
	 */
	private Date operateTime;

	/**
	 * 订单ID
	 */
	private Long orderId;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 付款状态
	 */
	private String payStatus;

	/**
	 * 发货状态
	 */
	private String consignStatus;

	/**
	 * 备注
	 */
	private String remarks;


}
