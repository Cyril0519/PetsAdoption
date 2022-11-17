package com.petsAdoption.address.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * 
 * @TableName t_address
 */
@TableName(value ="t_shipping_address")
@Data
public class ShoppingAddress implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 
     */
    private Integer regionId;

    /**
     * 地址名称
     */
    private String detailAddress;

    private String username;

    private String phone;

    /**
     * 是否默认
     */
    private Integer isDefault;

    @TableField(exist = false)
    private Map<String,String> extendField;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}