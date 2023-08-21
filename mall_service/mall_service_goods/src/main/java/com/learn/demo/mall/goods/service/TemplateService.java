package com.learn.demo.mall.goods.service;

import com.google.common.collect.Lists;
import com.learn.demo.mall.goods.dao.TemplateMapper;
import com.learn.demo.mall.goods.pojo.SpecPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 模板管理服务
 * @author zh_cr
 */
@Service
public class TemplateService {

    @Resource
    private TemplateMapper templateMapper;

    public List<SpecPO> querySpecsByTemplateIds(List<Integer> templateIds) {
        if (CollectionUtils.isEmpty(templateIds)) {
            return Lists.newArrayList();
        }
        return templateMapper.selectSpecsByTemplateIds(templateIds);
    }
}
