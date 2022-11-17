package com.petsAdoption.wishList.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.pets.feign.PetsFeign;
import com.petsAdoption.pets.pojo.PetsDetail;
import com.petsAdoption.wishList.pojo.WishList;
import com.petsAdoption.wishList.service.WishListService;
import com.petsAdoption.wishList.mapper.WishListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author wuxingyu
* @description 针对表【t_wish_list】的数据库操作Service实现
* @createDate 2022-09-05 16:20:58
*/
@Service
public class WishListServiceImpl extends ServiceImpl<WishListMapper, WishList> implements WishListService{
    @Autowired
    private WishListMapper wishListMapper;
    @Autowired
    private PetsFeign petsFeign;

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
            PetsDetail petsDetail = petsFeign.getById(petId).getData().get(0);
            HashMap<String, Object> petsInfo = new HashMap<>();
            petsInfo.put("petsDetail", petsDetail);
            wishListPage.getRecords().get(i).setExtendField(petsInfo);
        }
        return wishListPage;
    }
}




