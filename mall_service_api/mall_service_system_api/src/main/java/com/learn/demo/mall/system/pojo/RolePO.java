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
@Table(name = "tb_role")
public class RolePO {

	/**
	 * ID
	 */
	@Id
	private Integer id;

	/**
	 * 角色名称
	 */
	private String name;


}
