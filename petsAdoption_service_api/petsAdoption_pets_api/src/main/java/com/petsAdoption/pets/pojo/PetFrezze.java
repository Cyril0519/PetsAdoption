package com.petsAdoption.pets.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName t_pet_frezze
 */
@TableName(value ="t_pet_frezze")
@Data
public class PetFrezze implements Serializable {
    public static int TRY = 0;
    public static int COMMIT = 1;
    public static int CANCEL = 2;

    @TableId(type = IdType.INPUT)
    private String xid;

    private String petId;

    private Integer petCount;

    private Integer state;

    private static final long serialVersionUID = 1L;
}