package com.learn.demo.mall.goods.pojo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Table;

/**
 *
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_category_brand")
public class CategoryBrandPO {

	/**
	 * 分类ID
	 */
	private Integer categoryId;

	/**
	 * 品牌ID
	 */
	private Integer brandId;


}
