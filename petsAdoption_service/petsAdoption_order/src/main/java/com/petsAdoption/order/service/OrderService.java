package com.petsAdoption.order.service;

import com.petsAdoption.order.pojo.DetailOrder;
import com.petsAdoption.order.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wuxingyu
* @description 针对表【t_order】的数据库操作Service
* @createDate 2022-10-16 14:00:14
*/
public interface OrderService extends IService<Order> {

    Order create(Order order);
    Order pay(String orderId);

    List<DetailOrder> getToPayList();

    List<DetailOrder> getTravelList();

    List<DetailOrder> getFinishedList();

    Order confirmReceipt(String orderId);
}
