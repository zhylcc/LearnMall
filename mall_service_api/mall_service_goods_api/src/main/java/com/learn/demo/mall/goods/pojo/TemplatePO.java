package com.learn.demo.mall.goods.pojo;


import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author zh_cr
 */
@Data
@Table(name = "tb_template")
public class TemplatePO {

	/**
	 * ID
	 */
	@Id
	private Integer id;

	/**
	 * 模板名称
	 */
	private String name;

	/**
	 * 规格数量
	 */
	private Integer specNum;

	/**
	 * 参数数量
	 */
	private Integer paraNum;


}
