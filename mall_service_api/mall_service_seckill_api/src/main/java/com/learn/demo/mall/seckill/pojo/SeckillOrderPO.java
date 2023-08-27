package com.learn.demo.mall.seckill.pojo;


import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "tb_seckill_order")
public class SeckillOrderPO implements Serializable {

	/**
	 * 主键
	 */
	@Id
	private Long id;

	/**
	 * 秒杀商品ID
	 */
	private Long seckillId;

	/**
	 * 支付金额
	 */
	private BigDecimal money;

	/**
	 * 用户
	 */
	private String userId;

	/**
	 * 商家
	 */
	private String sellerId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 支付时间
	 */
	private Date payTime;

	/**
	 * 状态，0未支付，1已支付
	 */
	private String status;

	/**
	 * 收货人地址
	 */
	private String receiverAddress;

	/**
	 * 收货人电话
	 */
	private String receiverMobile;

	/**
	 * 收货人
	 */
	private String receiver;

	/**
	 * 交易流水
	 */
	private String transactionId;


}
