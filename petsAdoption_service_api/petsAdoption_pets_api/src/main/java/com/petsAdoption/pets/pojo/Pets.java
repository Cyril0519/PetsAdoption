package com.petsAdoption.pets.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName t_pets
 */
@TableName(value ="t_pets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pets implements Serializable {
    /**
     * id
     */
    @TableId
    private String id;

    /**
     * 种类
     */
    private String kinds;

    /**
     * 品种
     */
    private String category;

    /**
     * 1:审核，0:未审核
     */
    private Integer audited;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}