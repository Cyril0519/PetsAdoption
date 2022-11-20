package com.petsAdoption.address.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.address.pojo.Region;
import com.petsAdoption.address.pojo.ShoppingAddress;
import com.petsAdoption.address.service.AddressService;
import com.petsAdoption.address.mapper.ShoppingAddressMapper;
import com.petsAdoption.address.service.RegionService;
import com.petsAdoption.address.utils.TokenDecode;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author wuxingyu
* @description 针对表【t_address】的数据库操作Service实现
* @createDate 2022-10-15 16:07:55
*/
@Service
@DubboService
public class AddressServiceImpl extends ServiceImpl<ShoppingAddressMapper, ShoppingAddress>
    implements AddressService{
    @Autowired
    private TokenDecode tokenDecode;
    @Autowired
    private RegionService regionService;
    /*
     * @Description: 添加地址
       * @param address
     * @return: void
     * @Author: wuxingyu
     * @Date: 2022/10/15 16:38
     */
    public void addAddress(ShoppingAddress address) {
//        tokendDecode获取
        String uid = tokenDecode.getUserInfo().get("id");
        address.setUid(uid);
        if (address.getIsDefault() == 1) {
            // 删除以前的默认地址
            UpdateWrapper<ShoppingAddress> query = new UpdateWrapper<>();
            query.eq("uid",uid).set("is_default", 0);
            this.update(query);
        }
        this.save(address);
    }

    /*
     * @Description: 改变默认地址为id所对应的
       * @param id ：要改为默认地址的id
     * @return: void
     * @Author: wuxingyu
     * @Date: 2022/10/15 16:37
     */
    @Override
    public void changeDefaultAddress(String id) {
        String uid = tokenDecode.getUserInfo().get("id");
        UpdateWrapper<ShoppingAddress> query = new UpdateWrapper<>();
        Map<String, String> eqMap = new HashMap<>();
        eqMap.put("uid", uid);
        eqMap.put("is_default", "1");
        query.allEq(eqMap).set("is_default","0");
        this.update(query);
        query.clear();
        query.eq("id", id).set("is_default", "1");
        this.update(query);
    }

    @Override
    public List<ShoppingAddress> getAllAddress() {
        String uid = tokenDecode.getUserInfo().get("id");
        QueryWrapper<ShoppingAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        List<ShoppingAddress> addressList = this.list(queryWrapper);

        for (int i = 0; i < addressList.size(); i++) {
            Integer regionId = addressList.get(i).getRegionId();
            Region region = regionService.getById(regionId);
            HashMap<String, String> map = new HashMap<>();
            String formattedAddress = region.getMername() + addressList.get(i).getDetailAddress();
            map.put("formattedAddress",formattedAddress);
            addressList.get(i).setExtendField(map);
        }
        return addressList;
    }


}




