package com.petsAdoption.wishList.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petsAdoption.wishList.pojo.WishList;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wuxingyu
* @description 针对表【t_wish_list】的数据库操作Service
* @createDate 2022-09-05 16:20:58
*/
public interface WishListService extends IService<WishList> {
    List<WishList> getAllByUid(@Param("uid") String uid);

    Page<WishList>  findUserWishListPage(int current, int size, String uid);
}
