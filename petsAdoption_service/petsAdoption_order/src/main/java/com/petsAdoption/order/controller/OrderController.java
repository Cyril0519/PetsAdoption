package com.petsAdoption.order.controller;

import com.petsAdoption.order.pojo.DetailOrder;
import com.petsAdoption.order.pojo.Order;
import com.petsAdoption.order.service.OrderService;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /*
     * @Description: 创建订单，就简单插入数据库，等支付时再修改库存
       * @param order 订单对象，前端需要传：宠物id、数量、地址id即可，
     * @return: com.petsAdoption.pojo.Result<java.lang.Void>
     * @Author: wuxingyu
     * @Date: 2022/10/16 15:11
     */
    @PostMapping("/create")
    public Result<Order> create(@RequestBody Order order) {
//        设置uid
        Order order1 = orderService.create(order);
        return new Result<>(true, ResultCode.OK, "创建成功", order1);
    }

    /*
     * @Description: 用户支付接口，同时修改库存，退货时再弥补回去
       * @param
     * @return: com.petsAdoption.pojo.Result<java.lang.Void>
     * @Author: wuxingyu
     * @Date: 2022/10/16 15:16
     */
    @PostMapping("/pay")
    public Result<Order> pay(@RequestParam("orderId") String orderId) {
//        tokendecode获取
        Order order = orderService.pay(orderId);
        return new Result<>(true, ResultCode.OK, "支付成功",order);
    }

    @PostMapping("/confirmReceipt")
    public Result<Order> confirmReceipt(@RequestParam("orderId") String orderId) {
//        tokendecode获取
        Order order = orderService.confirmReceipt(orderId);
        return new Result<>(true, ResultCode.OK, "确认收货成功",order);
    }


    @GetMapping("/getToPayList")
    public Result<List<DetailOrder>> getToPayList(){
        List<DetailOrder> res = orderService.getToPayList();
        return new Result<>(true, ResultCode.OK, "查询成功", res);
    }

    // 获取运送中的
    @GetMapping("/getToReceiveList")
    public Result<List<DetailOrder>> getToReceiveList(){
        List<DetailOrder> res = orderService.getToReceiveList();
        return new Result<>(true, ResultCode.OK, "查询成功", res);
    }

    @GetMapping("/getFinishedList")
    public Result<List<DetailOrder>> getFinishedList(){
        List<DetailOrder> res = orderService.getFinishedList();
        return new Result<>(true, ResultCode.OK, "查询成功", res);
    }
}
