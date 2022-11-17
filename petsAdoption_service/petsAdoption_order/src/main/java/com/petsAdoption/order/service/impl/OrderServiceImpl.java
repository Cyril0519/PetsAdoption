package com.petsAdoption.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.order.pojo.DetailOrder;
import com.petsAdoption.order.pojo.Order;
import com.petsAdoption.order.service.OrderService;
import com.petsAdoption.order.mapper.OrderMapper;
import com.petsAdoption.order.utils.TokenDecode;
import com.petsAdoption.pets.feign.PetsFeign;
import com.petsAdoption.pets.pojo.Pets;
import com.petsAdoption.pets.pojo.PetsDetail;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.user.feign.UserFeign;
import com.petsAdoption.user.pojo.User;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author wuxingyu
* @description 针对表【t_order】的数据库操作Service实现
* @createDate 2022-10-16 14:00:14
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private PetsFeign petsFeign;
    @Autowired
    private TokenDecode tokenDecode;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @GlobalTransactional
    @Override
    public Order create(Order order) {
        String id = tokenDecode.getUserInfo().get("id");
        order.setUid(id);

        order.setBuildDate(new Date());
        // 计算金额
        List<PetsDetail> petsDetails = petsFeign.getById(order.getPetId()).getData();
        if (petsDetails == null || petsDetails.size() == 0) {
            //    没有该宠物，不创建
            throw new RuntimeException("没有该宠物");
        }
        // todo 地址校验

        PetsDetail petsDetail = petsDetails.get(0);
        BigDecimal totalMoney =petsDetail.getMoney().multiply(new BigDecimal(order.getCount()));
        int freight = petsDetail.getFreight();
        totalMoney = totalMoney.add(new BigDecimal(freight));
        order.setFreight(freight);
        order.setTotalMoney(totalMoney);

        // 判断是否可以优惠
        if (petsDetail.getAllowPointsDeductions()==1) {
            //    可以优惠
            //设置优惠
            // todo 后期做 太难了
            /*User user = userFeign.queryById(id).getData();
            order.setConcessionsMoney();*/
        }

        BigDecimal payMoney = totalMoney;
        order.setPayMoney(payMoney);

        this.save(order);
        return order;
    }

    @Override
    @GlobalTransactional
    public Order pay(String orderId){
        Order order = orderService.getById(orderId);
        if (order == null){
            // 订单不存在
            throw new RuntimeException("订单不存在");
        }
        if (order.getPayDate() != null) {
            // 订单已支付
            throw new RuntimeException("订单已支付");
        }
        // 减库存
        Result<Void> deduct = petsFeign.deduct(order.getPetId(), order.getCount());
        if (!deduct.isFlag()) {
            throw new RuntimeException(deduct.getMessage());
        }
        // todo 设置优惠金额/积分抵扣

        // 减用户金额
        Result<Void> result = userFeign.decreaseMoney(order.getPayMoney());
        if (!result.isFlag()){
            throw new RuntimeException(result.getMessage());
        }
        order.setPayDate(new Date());
        this.updateById(order);
        return order;
    }

    // 确认收货
    @Override
    public Order confirmReceipt(String orderId) {
        Order order = orderService.getById(orderId);
        order.setReceived(1);
        this.updateById(order);
        return order;
    }



    @Override
    public List<DetailOrder> getToPayList() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        // 未支付的pay_date为null
        queryWrapper.isNull("pay_date");
        return this.getSpecialDetailOrder(queryWrapper);
    }

    @Override
    public List<DetailOrder> getTravelList() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        // 未支付的pay_date为null
        queryWrapper.isNull("received");
        queryWrapper.isNotNull("pay_date");
        return this.getSpecialDetailOrder(queryWrapper);
    }

    // 待评价
    @Override
    public List<DetailOrder> getFinishedList() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("received");
        queryWrapper.isNotNull("pay_date");
        queryWrapper.isNull("finished");
        return this.getSpecialDetailOrder(queryWrapper);
    }



    private List<DetailOrder> getSpecialDetailOrder(QueryWrapper<Order> queryWrapper){
        String id = tokenDecode.getUserInfo().get("id");
        IPage<Order> page = new Page<>(1, 9);
        queryWrapper.eq("uid",id);
        IPage<Order> orderIPage = orderMapper.selectPage(page, queryWrapper);
        List<Order> records = orderIPage.getRecords();
        String jsonString = JSON.toJSONString(records);
        List<DetailOrder> detailOrders = JSON.parseArray(jsonString, DetailOrder.class);
        for (int i = 0; i < detailOrders.size(); i++) {
            String petId = detailOrders.get(i).getPetId();
            PetsDetail petsDetail = petsFeign.getById(petId).getData().get(0);
            detailOrders.get(i).setImage(petsDetail.getImage());
            detailOrders.get(i).setTitle(petsDetail.getTitle());
        }
        return detailOrders;
    }

}




