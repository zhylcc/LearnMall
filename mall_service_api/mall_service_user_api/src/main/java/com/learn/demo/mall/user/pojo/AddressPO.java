package com.learn.demo.mall.user.pojo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_address")
public class AddressPO implements Serializable {

	@Id
	private Integer id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 省
	 */
	private String provinceid;

	/**
	 * 市
	 */
	private String cityid;

	/**
	 * 县/区
	 */
	private String areaid;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 详细地址
	 */
	private String address;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 是否是默认 1默认 0否
	 */
	private String isDefault;

	/**
	 * 别名
	 */
	private String alias;


}
