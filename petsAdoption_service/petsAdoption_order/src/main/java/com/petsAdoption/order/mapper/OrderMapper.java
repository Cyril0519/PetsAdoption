package com.petsAdoption.order.mapper;

import com.petsAdoption.order.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wuxingyu
* @description 针对表【t_order】的数据库操作Mapper
* @createDate 2022-10-16 14:00:14
* @Entity com.petsAdoption.order.pojo.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}




