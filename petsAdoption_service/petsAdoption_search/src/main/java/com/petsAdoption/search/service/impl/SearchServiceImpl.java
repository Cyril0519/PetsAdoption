package com.petsAdoption.search.service.impl;


import com.alibaba.fastjson.JSON;
import com.petsAdoption.pets.pojo.Pets;
import com.petsAdoption.search.pojo.PetsInfo;
import com.petsAdoption.search.service.SearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public Map<String,Object> search(Map<String, String> searchMap) {
        // searchmap:{current, size, kinds, keyword, weight}
        if (searchMap != null) {
            // 结果
            Map<String, Object> resultMap = new HashMap<>();
            // 选中的条件
            Map<String, Object> selectedConditionMap = new HashMap<>();
            // 查询类
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            // 分页查询
            String currentPage = searchMap.get("current");
            if(StringUtils.isEmpty(currentPage)){
                currentPage = "1";
            }
            int current = Integer.parseInt(currentPage);  // 当前页
            String size = searchMap.get("size");        // 一页显示数目
            size = StringUtils.isEmpty(size) ? "20" : size;
            // 组装条件
            queryBuilder.withPageable(PageRequest.of(current - 1, Integer.parseInt(size)));

            // 种类
            String kinds = searchMap.get("kinds");
            kinds = StringUtils.isEmpty(kinds) ? "猫猫" : kinds;  // 默认查猫
            selectedConditionMap.put("kinds",kinds);
            boolQueryBuilder.must(QueryBuilders.termsQuery("kinds", kinds));

            // 品种
            String category = searchMap.get("category");
            if (!StringUtils.isEmpty(category)) {
                selectedConditionMap.put("category",category);
                boolQueryBuilder.must(QueryBuilders.termsQuery("category", category));
            }

            // 关键字查询
            String keyword = searchMap.get("keyword");
            if(!StringUtils.isEmpty(keyword)){
                boolQueryBuilder.must(QueryBuilders.matchQuery("title", keyword));
            }

            // 重量区间处理 todo
            String weight = searchMap.get("weight");
            if(!StringUtils.isEmpty(weight)){
                selectedConditionMap.put("weight", weight);
                if(weight.contains("kg以上")){
                    int low_weight = Integer.parseInt(weight.replace("kg以上", ""));
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("weight").gte(low_weight));

                }else{
                    weight = weight.replace("kg", "");
                    String[] split = weight.split("-");
                    int low_weight = Integer.parseInt(split[0]);
                    int height_weight = Integer.parseInt(split[1]);
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("weight").lte(height_weight).gte(low_weight));
                }
            }

            // 身高
            String height = searchMap.get("height");
            if(!StringUtils.isEmpty(height)){
                selectedConditionMap.put("height", height);
                if(height.contains("cm以上")){
                    int low_height = Integer.parseInt(height.replace("cm以上", ""));
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("height").gte(low_height));
                }else{
                    height = height.replace("cm", "");
                    String[] split = height.split("-");
                    int low_height = Integer.parseInt(split[0]);
                    int height_height = Integer.parseInt(split[1]);
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("weight").lte(height_height).gte(low_height));
                }
            }

            // 宠物细节查询
            for (String key : searchMap.keySet()) {
                if (key.contains("condition_")) {
                    String conditionValue = searchMap.get(key);
                    String conditionField = key.substring(10);
                    selectedConditionMap.put(conditionField, conditionValue);
                    // 名称
                    boolQueryBuilder.must(QueryBuilders.termsQuery("detailMap."+conditionField+".keyword", conditionValue));
                }
            }
            queryBuilder.withQuery(boolQueryBuilder);

            // 高亮查询
            HighlightBuilder highlightBuilder = new HighlightBuilder()
                    .field("title")
                    .preTags("<span style='color:red'>")
                    .postTags("</span>");
            queryBuilder.withHighlightBuilder(highlightBuilder);

            // 条件分类
                // 品种分类
            TermsAggregationBuilder categoryField = AggregationBuilders.terms("categoryGroup").field("category");
            queryBuilder.addAggregation(categoryField);
                // 细节分类
            TermsAggregationBuilder detailField = AggregationBuilders.terms("detailGroup").field("detailInfo");
            queryBuilder.addAggregation(detailField);

            //查询
            SearchHits<PetsInfo> search = elasticsearchRestTemplate.search(queryBuilder.build(), PetsInfo.class);

            // 高亮处理
            List<PetsInfo> collect = search.getSearchHits().stream().map(searchHit -> {
                //获得结果实体
                PetsInfo content = searchHit.getContent();
                //所有高亮结果
                Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
                //遍历高亮结果
                for (Map.Entry<String, List<String>> stringListEntry : highlightFields.entrySet()) {

                    String key = stringListEntry.getKey();
                    //获取实体反射类
                    Class<?> aClass = content.getClass();
                    try {
                        //获取该实体属性
                        Field declaredField = aClass.getDeclaredField(key);
                        //权限为私的 解除！
                        declaredField.setAccessible(true);
                        //替换，把高亮字段替换到这个实体对应的属性值上
                        declaredField.set(content, stringListEntry.getValue().get(0));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return content;
            }).collect(Collectors.toList());

            // 添加结果集
            resultMap.put("dataList", collect);

            // 分组处理
            Terms categoryGroup = search.getAggregations().get("categoryGroup");
            Terms detailGroup = search.getAggregations().get("detailGroup");
                // categoryGroup
            List<String> categoryCollect = categoryGroup.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
                // detailGroup
            Set<String> detailCollect = detailGroup.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toSet());
            HashMap<String, Set<String>> detailMap = new HashMap<>();  // 存放详细信息map
            // 算法改进 todo
            for (String s : detailCollect) {
                // 查询结果遍历添加到detailMap，（[{"颜色": "红色"}, {"颜色": "红色", "毛长": "短毛"}, {"颜色": "红色", "毛长": "长毛"}]）
                Map<String,String> itemMap = JSON.parseObject(s, Map.class);
                for (String itemKey : itemMap.keySet()) {
                    // 列表每一个itemMap对应的值
                    String itemValue = itemMap.get(itemKey);

                    Set<String> valueSet = detailMap.get(itemKey);
                    // 键值对不存在就创建并添加
                    if (valueSet == null) {
                        valueSet = new HashSet<>();
                    }
                    valueSet.add(itemValue);
                    detailMap.put(itemKey, valueSet);
                }
            }

            resultMap.put("categoryCollect", categoryCollect);
            resultMap.put("detailMap", detailMap);
            resultMap.put("selectedConditionMap", selectedConditionMap);

            // 页面处理
            long totalHits = search.getTotalHits();
            resultMap.put("current", current);
            resultMap.put("size", size);
            resultMap.put("total", totalHits);

            return resultMap;
        }

        return null;
    }




}
