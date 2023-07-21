package com.learn.demo.mall.goods.pojo;


import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author zh_cr
 */
@Data
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
