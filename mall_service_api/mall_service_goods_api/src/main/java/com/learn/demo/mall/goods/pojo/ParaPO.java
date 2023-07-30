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
@Table(name = "tb_para")
public class ParaPO {

	/**
	 * id
	 */
	@Id
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 选项
	 */
	private String options;

	/**
	 * 排序
	 */
	private Integer seq;

	/**
	 * 模板ID
	 */
	private Integer templateId;


}
