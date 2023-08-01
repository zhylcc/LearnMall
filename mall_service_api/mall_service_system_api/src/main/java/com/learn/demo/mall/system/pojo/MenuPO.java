package com.learn.demo.mall.system.pojo;


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
@Table(name = "tb_menu")
public class MenuPO {

	/**
	 * 菜单ID
	 */
	@Id
	private String id;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * URL
	 */
	private String url;

	/**
	 * 上级菜单ID
	 */
	private String parentId;


}
