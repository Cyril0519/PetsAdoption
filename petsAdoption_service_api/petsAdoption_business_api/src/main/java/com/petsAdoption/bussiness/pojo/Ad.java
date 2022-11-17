package com.petsAdoption.bussiness.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_ad
 */
@TableName(value ="t_ad")
@Data
public class Ad implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 投放广告的用户
     */
    private String name;

    /**
     * 广告url地址
     */
    private String imgUrl;

    /**
     * 广告url地址
     */
    private String link;

    /**
     * 广告开始时间
     */
    private Date beginDate;

    /**
     * 广告结束时间
     */
    private Date endDate;

    /**
     * 曝光度（0-10000）
     */
    private Integer exposure;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}