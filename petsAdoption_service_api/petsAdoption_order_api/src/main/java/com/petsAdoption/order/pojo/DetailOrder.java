package com.petsAdoption.order.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrder {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 宠物id
     */
    private String petId;
    // 宠物图片
    private String image;
    // 宠物标题
    private String title;

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



}
