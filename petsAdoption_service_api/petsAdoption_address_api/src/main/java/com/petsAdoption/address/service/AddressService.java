package com.petsAdoption.address.service;

import com.petsAdoption.address.pojo.ShoppingAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wuxingyu
* @description 针对表【t_address】的数据库操作Service
* @createDate 2022-10-15 16:07:55
*/
public interface AddressService extends IService<ShoppingAddress> {
    void addAddress(ShoppingAddress address);

    void changeDefaultAddress(String id);

    List<ShoppingAddress> getAllAddress();
}
