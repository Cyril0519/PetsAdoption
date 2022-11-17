package com.petsAdoption.user.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户表
 * @TableName t_user
 */
@TableName(value ="t_user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId
    private String uid;

    /**
     * 用户名
     */
    @Size(min = 2,max = 10, message = "用户名长度应该大于2并小于10")
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户性别 0：没写，1：男，2：女
     */

    private String gender;

    /**
     * 用户电话号码
     */
    private String phone;

    /**
     * 用户居住地址
     */
    private String address;

    /**
     * 用户邮箱
     */
    private String email;

    // 爱心指数
    @Min(0)
    @Max(4)
    private BigDecimal caringIndex;

    // 用户头像
    private String profile;

    // 权限
    private String authority;

    //账户余额
    private BigDecimal money;

    // 积分
    private int rewardsPoints;
//    注册时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date registerDate;

    /**
     * 逻辑删除，0没删
     */
    @TableLogic
    private Integer isDelete;



    /**
     * 账号是否过期，0没有，1过期
     */
    private Integer isAccountExpire;

    /**
     * 账号是否锁定，0没有，1锁定
     */
    private Integer isAccountLocked;

    /**
     * 账号是否启动，0没有，1启用
     */
    private Integer isEnabled;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}