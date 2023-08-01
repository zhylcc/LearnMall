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
@Table(name = "tb_admin_role")
public class AdminRolePO {

	/**
	 * 管理员ID
	 */
	private Integer adminId;

	/**
	 * 角色ID
	 */
	private Integer roleId;


}
