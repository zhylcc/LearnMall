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
