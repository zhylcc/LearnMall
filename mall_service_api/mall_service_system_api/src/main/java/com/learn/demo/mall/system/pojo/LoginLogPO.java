package com.learn.demo.mall.system.pojo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 *
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_login_log")
public class LoginLogPO {

	@Id
	private Integer id;

	private String loginName;

	private String ip;

	private String browserName;

	/**
	 * 地区
	 */
	private String location;

	/**
	 * 登录时间
	 */
	private LocalDateTime loginTime;


}
