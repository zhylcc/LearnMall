package com.learn.demo.mall.common.response;

import lombok.Data;

import java.util.List;

/**
 * 分页数据
 * @author zh_cr
 */
@Data
public class PageList<T> {

    private Integer total;

    private List<T> items;
}
