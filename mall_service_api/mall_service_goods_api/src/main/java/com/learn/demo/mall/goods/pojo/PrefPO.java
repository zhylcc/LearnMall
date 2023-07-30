package com.learn.demo.mall.goods.pojo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_pref")
public class PrefPO {

	/**
	 * ID
	 */
	@Id
	private Integer id;

	/**
	 * 分类ID
	 */
	private Integer cateId;

	/**
	 * 消费金额
	 */
	private Integer buyMoney;

	/**
	 * 优惠金额
	 */
	private Integer preMoney;

	/**
	 * 活动开始日期
	 */
	private LocalDate startTime;

	/**
	 * 活动截至日期
	 */
	private LocalDate endTime;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 状态
	 */
	private String state;


}
