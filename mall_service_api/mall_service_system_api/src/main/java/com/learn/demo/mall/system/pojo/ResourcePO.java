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
@Table(name = "tb_resource")
public class ResourcePO {

	@Id
	private Integer id;

	private String resKey;

	private String resName;

	private Integer parentId;


}
