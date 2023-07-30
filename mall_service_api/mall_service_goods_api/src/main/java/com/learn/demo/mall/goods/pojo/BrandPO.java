package com.learn.demo.mall.goods.pojo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 品牌表
 *
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_brand")
public class BrandPO {

	/**
	 * 品牌id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 品牌名称
	 */
	private String name;

	/**
	 * 品牌图片地址
	 */
	private String image;

	/**
	 * 品牌的首字母
	 */
	private String letter;

	/**
	 * 排序
	 */
	private Integer seq;


}
