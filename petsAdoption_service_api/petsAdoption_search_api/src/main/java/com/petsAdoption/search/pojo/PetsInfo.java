package com.petsAdoption.search.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 
 * @TableName t_pets_detail
 */

@Data
@Document(indexName = "petsinfo", type = "docs")
public class PetsInfo {
    /**
     * 
     */
    @Id
    @Field(index = true, store = true, type = FieldType.Keyword)
    private String id;

    @Field(index = true, store = true, type = FieldType.Keyword)
    private String kinds;

    @Field(index = true, store = true, type = FieldType.Keyword)
    private String category;

    /**
     * 
     */
    private String petsId;

    /**
     * 
     */
    private String ownerId;

    /**
     * 
     */
    private Integer number;

    /**
     * 封面图片
     */
    private String image;

    /**
     * 图片列表
     */
    private String images;


    /**
     * 发布时间
     */
    private Date postTime;

    /**
     * 
     */
    @Field(index = true, store = true, type = FieldType.Text, analyzer = "ik_smart")
    private String title;

    /**
     * 
     */
    private String catDesc;

    /**
     * 详细信息
     */
    @Field(index = true, store = true, type = FieldType.Keyword)
    private String detailInfo;

//    必须写泛型
    private Map<String,String> detailMap;

    @Field(index = true, store = true, type = FieldType.Double)
    private BigDecimal weight;

    @Field(index = true, store = true, type = FieldType.Double)
    private BigDecimal height;

}