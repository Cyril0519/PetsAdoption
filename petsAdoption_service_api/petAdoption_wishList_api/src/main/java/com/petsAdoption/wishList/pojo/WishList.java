package com.petsAdoption.wishList.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName t_wish_list
 */
@TableName(value ="t_wish_list")
@Data
public class WishList implements Serializable {
    /*
    * 表id
    * */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 用户id
     */
    private String uid;

    /**
     * 宠物id
     */
    private String petId;

    /**
     * 选择日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date selectDate;

    /**
     * 选择数量
     */
    private Integer selectNumber;

    // 扩展字段
    @TableField(exist = false)
    private Map<String,Object> extendField;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}