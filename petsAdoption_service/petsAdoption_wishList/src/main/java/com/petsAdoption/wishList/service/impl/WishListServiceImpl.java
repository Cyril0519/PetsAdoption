package com.petsAdoption.wishList.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.pets.pojo.PetsDetail;
import com.petsAdoption.pets.serive.PetsDetailService;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import com.petsAdoption.wishList.config.TokenDecode;
import com.petsAdoption.wishList.pojo.WishList;
import com.petsAdoption.wishList.mapper.WishListMapper;
import com.petsAdoption.wishList.service.WishListService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
* @author wuxingyu
* @description 针对表【t_wish_list】的数据库操作Service实现
* @createDate 2022-09-05 16:20:58
*/
@Service
@DubboService
public class WishListServiceImpl extends ServiceImpl<WishListMapper, WishList> implements WishListService {
    @Autowired
    private WishListMapper wishListMapper;
    @Autowired
    private TokenDecode tokenDecode;

    @DubboReference
    private PetsDetailService petsDetailService;


    @Override
    public List<WishList> getAllByUid(String uid) {
        return wishListMapper.getAllByUid(uid);
    }

    public Page<WishList> findUserWishListPage(int current, int size, String uid) {
        QueryWrapper<WishList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        Page<WishList> wishListPage = wishListMapper.selectPage(new Page<>(current, size), queryWrapper);

        for (int i = 0; i < wishListPage.getRecords().size(); i++) {
            String petId = wishListPage.getRecords().get(i).getPetId();
            PetsDetail petsDetail = petsDetailService.getById(petId);
            HashMap<String, Object> petsInfo = new HashMap<>();
            petsInfo.put("petsDetail", petsDetail);
            wishListPage.getRecords().get(i).setExtendField(petsInfo);
        }
        return wishListPage;
    }

    @Override
    public void add(String petId, Integer selectNumber) {

        // 判断是否符合条件
        PetsDetail pet = petsDetailService.getById(petId);
        if(pet == null){
            throw new RuntimeException("宠物不存在");
        }
        Integer number = pet.getNumber();  // 剩余数量

        if(selectNumber > number){
            throw new RuntimeException("剩余数量不足");
        }

        WishList wishList = new WishList();

        // 获取用户id
        String uid = tokenDecode.getUserInfo().get("id");
//        设置用户id
        wishList.setUid(uid);
//        宠物id
        wishList.setPetId(petId);
        Date date = new Date();
        wishList.setSelectDate(date);
        wishList.setSelectNumber(selectNumber);

        this.save(wishList);
//        减库存
        PetsDetail petsDetail = new PetsDetail();
        petsDetail.setNumber(number - selectNumber);
        petsDetail.setId(petId);
        petsDetailService.updatePets(petsDetail);
    }
}




