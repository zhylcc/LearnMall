package com.learn.demo.mall.goods.pojo;


import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author zh_cr
 */
@Data
@Table(name = "tb_album")
public class AlbumPO {

	/**
	 * 编号
	 */
	@Id
	private Long id;

	/**
	 * 相册名称
	 */
	private String title;

	/**
	 * 相册封面
	 */
	private String image;

	/**
	 * 图片列表
	 */
	private String imageItems;


}
