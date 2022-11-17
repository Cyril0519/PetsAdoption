package com.petsAdoption.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.bussiness.pojo.Ad;
import com.petsAdoption.business.service.AdService;
import com.petsAdoption.business.mapper.AdMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author wuxingyu
* @description 针对表【t_ad】的数据库操作Service实现
* @createDate 2022-10-29 10:59:59
*/
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad>
    implements AdService{

    @Override
    public List<Ad> getAd() {
        Date now = new Date();
        QueryWrapper<Ad> query = new QueryWrapper<>();
        query.ge("end_date", now);
        query.le("begin_date", now);
        List<Ad> adList = this.list(query);

        return adList;
    }
}




