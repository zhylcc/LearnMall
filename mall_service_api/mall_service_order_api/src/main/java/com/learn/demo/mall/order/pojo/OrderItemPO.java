package com.learn.demo.mall.order.pojo;


import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tb_order_item")
public class OrderItemPO implements Serializable {

	/**
	 * ID
	 */
	@Id
	private String id;

	/**
	 * 1级分类
	 */
	private Integer categoryId1;

	/**
	 * 2级分类
	 */
	private Integer categoryId2;

	/**
	 * 3级分类
	 */
	private Integer categoryId3;

	/**
	 * SPU_ID
	 */
	private String spuId;

	/**
	 * SKU_ID
	 */
	private String skuId;

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 单价
	 */
	private Integer price;

	/**
	 * 数量
	 */
	private Integer num;

	/**
	 * 总金额
	 */
	private Integer money;

	/**
	 * 实付金额
	 */
	private Integer payMoney;

	/**
	 * 图片地址
	 */
	private String image;

	/**
	 * 重量
	 */
	private Integer weight;

	/**
	 * 运费
	 */
	private Integer postFee;

	/**
	 * 是否退货
	 */
	private String isReturn;


}
