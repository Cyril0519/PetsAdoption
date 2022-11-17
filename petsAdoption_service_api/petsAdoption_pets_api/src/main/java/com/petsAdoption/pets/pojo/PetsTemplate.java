package com.petsAdoption.pets.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName t_character_template
 */
@TableName(value ="t_pets_template")
@Data
public class PetsTemplate implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer petsId;

    /**
     * 
     */
    private Integer templateId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}