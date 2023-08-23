package com.learn.demo.mall.goods.service;

import com.alibaba.fastjson.JSON;
import com.learn.demo.mall.common.enums.SortOrderEnum;
import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.goods.dao.ESSkuMapper;
import com.learn.demo.mall.goods.dao.SkuMapper;
import com.learn.demo.mall.goods.entity.SkuDocument;
import com.learn.demo.mall.goods.enums.GoodsErrorCodeEnum;
import com.learn.demo.mall.goods.pojo.SkuPO;
import com.learn.demo.mall.goods.request.SkuExampleESReq;
import com.learn.demo.mall.order.pojo.OrderItemPO;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * SKU管理
 * @author zh_cr
 */
@Service
public class SkuService {

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private ESSkuMapper esSkuMapper;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CART_KEY_PREFIX = KeyConfigUtil.getCartKeyPrefix();

    /**
     * 创建索引
     */
    public void createIndex() {
        elasticsearchTemplate.createIndex(SkuDocument.class);
        elasticsearchTemplate.putMapping(SkuDocument.class);
    }

    /**
     * 删除索引
     */
    public void deleteIndex() {
        elasticsearchTemplate.deleteIndex(SkuDocument.class);
    }

    /**
     * 导入全部索引
     */
    public Integer importAllSkuIndex() {
        List<SkuPO> skuList = skuMapper.selectAll();
        return importSkusIndex(skuList);
    }

    /**
     * 按skuId批量导入索引
     */
    public Integer importSkusIndex(List<SkuPO> skuList) {
        if (CollectionUtils.isEmpty(skuList)) {
            return 0;
        }
        List<SkuDocument> skuDocuments = new ArrayList<>();
        skuList.forEach(sku -> {
            SkuDocument skuDocument = new SkuDocument();
            skuDocument.setId(sku.getId());
            skuDocument.setName(sku.getName());
            skuDocument.setBrandName(sku.getBrandName());
            skuDocument.setPrice(sku.getPrice());
            skuDocument.setSpec(sku.getSpec());
            skuDocument.setSpecMap(JSON.parseObject(sku.getSpec()));
            skuDocuments.add(skuDocument);
        });
        try {
            esSkuMapper.saveAll(skuDocuments);
        } catch (Exception e) {
            throw new BaseBizException("导入Sku索引失败", GoodsErrorCodeEnum.BIZ_ES_IMPORT_SKU_ERROR);
        }
        return skuDocuments.size();
    }

    /**
     * 按skuId批量删除索引
     */
    public Integer deleteSkusIndex(List<String> skuIds) {
        if (CollectionUtils.isEmpty(skuIds)) {
            return 0;
        }
        try {
            skuIds.forEach(id -> esSkuMapper.deleteById(id));
        } catch (Exception e) {
            throw new BaseBizException("删除Sku索引失败", GoodsErrorCodeEnum.BIZ_ES_DELETE_SKU_ERROR);
        }
        return skuIds.size();
    }

    /**
     * 商品搜索
     */
    public Map<String, Object> search(SkuExampleESReq req, Integer currentPage, Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        // 1 设置查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        queryBuilder.withQuery(boolQuery);
        // 按sku名称查询
        if (StringUtils.isNotBlank(req.getName())) {
            boolQuery.must(QueryBuilders.matchQuery("name", req.getName()).operator(Operator.AND));
        }
        // 按品牌名称过滤
        if (StringUtils.isNotBlank(req.getBrandName())) {
            boolQuery.filter(QueryBuilders.termQuery("brandName", req.getBrandName()));
        }
        // 按价格区间过滤
        if (Objects.nonNull(req.getMinPrice())) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(req.getMinPrice()));
        }
        if (Objects.nonNull(req.getMaxPrice())) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").lte(req.getMaxPrice()));
        }
        // 按规格过滤
        if (Objects.nonNull(req.getSpecExample()) && !CollectionUtils.isEmpty(req.getSpecExample().entrySet())) {
            req.getSpecExample().forEach((key, value) -> {
                boolQuery.filter(QueryBuilders.termQuery("specMap."+key+".keyword", value));
            });
        }
        // 聚合品牌
        if (Boolean.TRUE.equals(req.getAggOnBrandName())) {
            queryBuilder.addAggregation(AggregationBuilders.terms("brandName").field("brandName"));
        }
        // 聚合规格
        if (Boolean.TRUE.equals(req.getAggOnSpec())) {
            queryBuilder.addAggregation(AggregationBuilders.terms("spec").field("spec.keyword"));
        }
        // 分页
        queryBuilder.withPageable(PageRequest.of(currentPage-1, pageSize));
        // 排序（默认升序）
        if (StringUtils.isNotBlank(req.getSortKey())) {
            SortOrder sortOrder = SortOrderEnum.DESC.getOrder().equals(req.getSortOrder()) ? SortOrder.DESC : SortOrder.ASC;
            queryBuilder.withSort(SortBuilders.fieldSort(req.getSortKey()).order(sortOrder));
        }
        // 设置高亮域
        if (Boolean.TRUE.equals(req.getHighlight())) {
            queryBuilder.withHighlightFields(new HighlightBuilder.Field("name").preTags("<span style='color:red'>").postTags("</span>"));
        }
        // 2 执行查询
        AggregatedPage<SkuDocument> searchResult = elasticsearchTemplate.queryForPage(queryBuilder.build(), SkuDocument.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<T> list = new ArrayList<>();
                SearchHits hits = searchResponse.getHits();
                if (Objects.nonNull(hits)) {
                    // 处理查询结果
                    hits.forEach(hit -> {
                        SkuDocument document = JSON.parseObject(hit.getSourceAsString(), SkuDocument.class);
                        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                        if (highlightFields != null && highlightFields.size() > 0) {
                            document.setName(highlightFields.get("name").getFragments()[0].toString());
                        }
                        list.add((T) document);
                    });
                }
                return new AggregatedPageImpl<>(list, pageable, hits.getTotalHits(), searchResponse.getAggregations());
            }
        });
        // 3 返回结果
        result.put("total", searchResult.getTotalElements());
        result.put("item", searchResult.getContent());
        if (Boolean.TRUE.equals(req.getAggOnBrandName())) {
            List<String> brandNames = ((StringTerms) searchResult.getAggregation("brandName")).getBuckets()
                    .stream().map(StringTerms.Bucket::getKeyAsString).collect(Collectors.toList());
            result.put("brandNames", brandNames);
        }
        if (Boolean.TRUE.equals(req.getAggOnSpec())) {
            List<String> specs = ((StringTerms) searchResult.getAggregation("spec")).getBuckets()
                    .stream().map(StringTerms.Bucket::getKeyAsString).collect(Collectors.toList());
            result.put("specs", specs);
        }
        return result;
    }

    public SkuPO queryById(String id) {
        return skuMapper.selectByPrimaryKey(id);
    }

    public Integer reduce(String username) {
        AtomicInteger delta = new AtomicInteger();
        Objects.requireNonNull(redisTemplate.boundHashOps(CART_KEY_PREFIX + username).values())
                .forEach(item->{
                    int count = skuMapper.reduce((OrderItemPO) item);
                    if (count <= 0) {
                        throw new BaseBizException("库存不足", GoodsErrorCodeEnum.BIZ_SKU_LACK);
                    }
                    delta.addAndGet(count);
                });
        return delta.get();
    }
}
