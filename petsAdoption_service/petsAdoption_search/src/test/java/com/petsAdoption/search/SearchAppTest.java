package com.petsAdoption.search;

import com.alibaba.fastjson.JSON;
import com.petsAdoption.search.pojo.PetsInfo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;


import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SpringBootTest
public class SearchAppTest {
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;


    // 基本查询
    @Test
    public void test1(){
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(QueryBuilders.rangeQuery("age").gte(1).lte(4));
        boolQueryBuilder.filter(QueryBuilders.termsQuery("title","猫"));
        query.withQuery(boolQueryBuilder);

        SearchHits<PetsInfo> searchHits = elasticsearchRestTemplate.search(query.build(), PetsInfo.class);
        System.out.println(searchHits.getTotalHits());

        for (SearchHit<PetsInfo> searchHit : searchHits) {
            System.out.println(searchHit.getContent());
        }
    }

    // 高亮查询
    @Test
    public void test2() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("title", "现货"));

        HighlightBuilder highlightBuilder = new HighlightBuilder().field("title").preTags("<strong>").postTags("</strong>");

        queryBuilder.withHighlightBuilder(highlightBuilder);
        queryBuilder.withQuery(boolQueryBuilder);
        SearchHits<PetsInfo> search = elasticsearchRestTemplate.search(queryBuilder.build(), PetsInfo.class);

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

        collect.forEach(System.out::println);

    }

    // 分组
    @Test
    public void test3() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termsQuery("title", "现货"));

        TermsAggregationBuilder ageField = AggregationBuilders.terms("detail").field("detailInfo");

        nativeSearchQueryBuilder.withQuery(boolQueryBuilder)
                .addAggregation(ageField);

        SearchHits<PetsInfo> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), PetsInfo.class);


        Terms detailGroup = search.getAggregations().get("detail");

        // 转set删除重复值
        Set<String> detailCollect = detailGroup.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toSet());
        System.out.println(detailCollect);
        HashMap<String, Set<String>> detailMap = new HashMap<>();

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

        // 输出detailMap
        for (String s : detailMap.keySet()) {
            System.out.println(s +":"+ detailMap.get(s));
        }
    }

    @Test
    public void test4() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = "2020-06-25 23:21:11";

        Date parse = format.parse(dateString);
        System.out.println(parse);


    }


}
