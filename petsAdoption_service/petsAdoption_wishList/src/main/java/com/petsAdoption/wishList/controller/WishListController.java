package com.petsAdoption.wishList.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import com.petsAdoption.wishList.config.TokenDecode;
import com.petsAdoption.wishList.pojo.WishList;
import com.petsAdoption.wishList.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;


@RequestMapping("/wishList")
@Controller
public class WishListController {
    @Autowired
    private TokenDecode tokenDecode;
    @Autowired
    private WishListService wishListService;


    @GetMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestParam(value = "petId") String petId,
                              @RequestParam(value = "selectNumber") Integer selectNumber
                            ) throws ParseException {

        return new Result<>(true, ResultCode.OK, "添加成功");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<Void> delete(@PathVariable("id") String id) {
        wishListService.removeById(id);
        return new Result<>(true, ResultCode.OK, "删除成功");
    }

    @PostMapping("/update")
    @ResponseBody
    public Result<Void> update(@RequestBody WishList wishList) {
        String uid = this.getUserId();
        wishList.setUid(uid);
        UpdateWrapper<WishList> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("uid", wishList.getUid())
                .eq("id",wishList.getId());
        wishList.setUid(null);
        wishListService.update(wishList, updateWrapper);
        return new Result<>(true, ResultCode.OK, "更新成功");
    }

    @GetMapping("/query/{current}/{size}")
    @ResponseBody
    public Result<Page<WishList>> query(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {
//        获取id
        String uid = this.getUserId();
//        分页查询
        Page<WishList> userWishListPage = wishListService.findUserWishListPage(current, size, uid);

        return new Result<>(true, ResultCode.OK, "查询成功", userWishListPage);
    }



    private String getUserId() {
        //        获取用户id
        String id = tokenDecode.getUserInfo().get("id");
        return id;
    }
}
