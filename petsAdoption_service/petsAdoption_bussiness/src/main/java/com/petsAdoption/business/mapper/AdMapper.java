package com.petsAdoption.business.mapper;

import com.petsAdoption.bussiness.pojo.Ad;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wuxingyu
* @description 针对表【t_ad】的数据库操作Mapper
* @createDate 2022-10-29 10:59:59
* @Entity com.petsAdoption.bussiness.pojo.Ad
*/
@Mapper
public interface AdMapper extends BaseMapper<Ad> {

}




