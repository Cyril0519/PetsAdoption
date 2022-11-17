package com.petsAdoption.pets.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnauditedPet {
    private String id;

    private String kinds;

    private String category;

    private String ownerId;

    private String ownerName;

    private String image;

    private String images;

    private Date postTime;

    /**
     *
     */
    private String title;

    private BigDecimal weight;

    private BigDecimal height;

    private String catDesc;

    //价格
    private BigDecimal money;

    // 运费
    private int freight;

    // 是否允许积分抵扣
    private int allowPointsDeductions;

    /**
     * 详细信息
     */
    private String detailInfo;
    private int exposure;

}
