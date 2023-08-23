package com.learn.demo.mall.goods.dao;

import com.learn.demo.mall.goods.entity.SkuDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zh_cr
 */
@Repository
public interface ESSkuMapper extends ElasticsearchRepository<SkuDocument, String> {

}
