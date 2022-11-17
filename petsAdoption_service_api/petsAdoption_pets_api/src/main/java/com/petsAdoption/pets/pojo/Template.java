package com.petsAdoption.pets.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName t_character
 */
@TableName(value ="t_template")
@Data
public class Template implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 特点
     */
    private String character;

    /**
     * 选项
     */
    private String options;

    /**
     * 1:true,0:false
     */
    private Integer required;

    /**
     * 单位：kg，月
     */
    private String unit;

    /**
     * 例子
     */
    private String example;

    /**
     * 需要的类型（text,number）
     */
    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}