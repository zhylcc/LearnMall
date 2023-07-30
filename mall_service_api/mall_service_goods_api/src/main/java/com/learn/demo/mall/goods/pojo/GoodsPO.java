package com.learn.demo.mall.goods.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author zh_cr
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoodsPO {

    private SpuPO spu;

    private List<SkuPO> skuList;
}
