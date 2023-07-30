package com.learn.demo.mall.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页数据
 * @author zh_cr
 */
@Getter
@Setter
@AllArgsConstructor
public class PageList<T> {

    private Long total;

    private List<T> items;
}
