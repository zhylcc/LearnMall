package com.learn.demo.mall.seckill.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_seckill_goods")
public class SeckillGoodsPO implements Serializable {

	@Id
	private Long id;

	/**
	 * spu ID
	 */
	private Long goodsId;

	/**
	 * sku ID
	 */
	private Long itemId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 商品图片
	 */
	private String smallPic;

	/**
	 * 原价格
	 */
	private BigDecimal price;

	/**
	 * 秒杀价格
	 */
	private BigDecimal costPrice;

	/**
	 * 商家ID
	 */
	private String sellerId;

	/**
	 * 添加日期
	 */
	private LocalDateTime createTime;

	/**
	 * 审核日期
	 */
	private LocalDateTime checkTime;

	/**
	 * 审核状态，0未审核，1审核通过，2审核不通过
	 */
	private String status;

	/**
	 * 开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	private LocalDateTime endTime;

	/**
	 * 秒杀商品数
	 */
	private Integer num;

	/**
	 * 剩余库存数
	 */
	private Integer stockCount;

	/**
	 * 描述
	 */
	private String introduction;


}
