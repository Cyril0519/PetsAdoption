package com.petsAdoption.order.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_order
 */
@TableName(value ="t_order")
@Data
public class Order implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 宠物id
     */
    private String petId;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 下单时间
     */
    private Date buildDate;

    /**
     * 收货人地址id
     */
    private String addressId;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 总金额
     */
    private BigDecimal totalMoney;

    /**
     * 优惠金额
     */
    private BigDecimal concessionsMoney;

    /**
     * 积分抵扣
     */
    private BigDecimal rewordsPointsDeductions;

    /**
     * 运费
     */
    private Integer freight;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;

    /**
     * 付款时间
     */
    private Date payDate;

    /**
     * 是否收到货
     */
    private Integer received;

    /**
     * 该订单是否完成
     */
    private Integer finished;

    /**
     * 积分(价格的1%)
     */
    private Integer rewardsPoints;

    /**
     * 给主人的分0-5
     */
    private Integer score;

    /**
     * 评价
     */
    private String comment;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}