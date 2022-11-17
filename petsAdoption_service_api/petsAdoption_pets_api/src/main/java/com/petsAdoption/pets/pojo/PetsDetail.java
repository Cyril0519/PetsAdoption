package com.petsAdoption.pets.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName t_pets_detail
 */
@TableName(value ="t_pets_detail")
@Data
public class PetsDetail implements Serializable {
    /**
     * 
     */
    @TableId
    private String id;

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
     * 审核是否通过，0->不通过；1通过
     */
    private Integer audited;

    /**
     * 发布时间
     */
    private Date postTime;

    /**
     * 
     */
    private String title;

    private BigDecimal weight;

    private BigDecimal height;


    /**
     * 
     */
    private String catDesc;

    //价格
    private BigDecimal money;

    // 运费
    private int freight;

    // 是否允许积分抵扣
    private int allowPointsDeductions;

    /**
     * 详细信息
     */
    private String detailInfo;

    @TableLogic(value = "0", delval = "1")
    private int isDeleted;

//    曝光度
    private int exposure;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}