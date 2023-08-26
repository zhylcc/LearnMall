package com.learn.demo.mall.user.pojo;


import lombok.*;

import javax.persistence.Table;

/**
 *
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tb_point_log")
public class PointLogPO {

	private String orderId;

	private String userId;

	private Integer point;


}
