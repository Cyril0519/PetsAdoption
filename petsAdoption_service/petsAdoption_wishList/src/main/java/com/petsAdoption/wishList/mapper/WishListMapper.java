package com.petsAdoption.wishList.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.petsAdoption.wishList.pojo.WishList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wuxingyu
* @description 针对表【t_wish_list】的数据库操作Mapper
* @createDate 2022-09-05 16:20:58
* @Entity com.petsAdoption.wishList.pojo.WishList
*/
@Mapper
public interface WishListMapper extends BaseMapper<WishList> {
    List<WishList> getAllByUid(@Param("uid") String uid);

}




