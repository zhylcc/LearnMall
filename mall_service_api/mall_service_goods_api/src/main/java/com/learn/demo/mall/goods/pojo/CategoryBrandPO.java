package com.learn.demo.mall.goods.pojo;


import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author zh_cr
 */
@Data
@Table(name = "tb_category_brand")
public class CategoryBrandPO {

	/**
	 * 分类ID
	 */
	@Id
	private Integer categoryId;

	/**
	 * 品牌ID
	 */
	private Integer brandId;


}
