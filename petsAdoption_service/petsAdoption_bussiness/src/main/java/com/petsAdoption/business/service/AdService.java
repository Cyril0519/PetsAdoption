package com.petsAdoption.business.service;

import com.petsAdoption.bussiness.pojo.Ad;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wuxingyu
* @description 针对表【t_ad】的数据库操作Service
* @createDate 2022-10-29 10:59:59
*/
public interface AdService extends IService<Ad> {

    List<Ad> getAd();
}
