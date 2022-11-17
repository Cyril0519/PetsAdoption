package com.petsAdoption.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @TableName t_money_freeze
 */
@TableName(value ="t_money_freeze")
@Data
public class MoneyFreeze implements Serializable {
    public static final int TRY = 0;
    public static final int COMMIT = 1;
    public static final int CANCEL = 2;

    @TableId(type = IdType.INPUT)
    private String xid;

    private String userId;

    private BigDecimal freezeMoney;

    private Integer state;

    private static final long serialVersionUID = 1L;

    abstract static class state{

    }
}