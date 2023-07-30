package com.learn.demo.mall.goods.pojo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
