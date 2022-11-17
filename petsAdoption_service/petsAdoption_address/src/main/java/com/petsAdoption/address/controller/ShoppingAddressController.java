package com.petsAdoption.address.controller;

import com.petsAdoption.address.pojo.ShoppingAddress;
import com.petsAdoption.address.service.AddressService;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/shopping_address")
public class ShoppingAddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/addAddress")
    public Result<Void> addAddress(@RequestBody ShoppingAddress address) {
        addressService.addAddress(address);
        return new Result<>(true,ResultCode.OK,"添加成功");
    }

    @PutMapping("/changeDefaultAddress")
    public Result<Void> changeDefaultAddress(@RequestParam("id") String id) {
        addressService.changeDefaultAddress(id);
        return new Result<>(true, ResultCode.OK, "修改成功");
    }

    @DeleteMapping("/deleteAddress")
    public Result<Void> deleteAddress(@RequestParam("id") String id) {
        addressService.removeById(id);
        return new Result<>(true, ResultCode.OK, "删除成功");
    }

    @GetMapping("/getAllAddress")
    public Result<List<ShoppingAddress>> getAllAddress() {
        List<ShoppingAddress> list = addressService.getAllAddress();
        return new Result<>(true, ResultCode.OK, "查询成功", list);
    }
}
