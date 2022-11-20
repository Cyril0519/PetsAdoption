package com.petsAdoption.pets.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.pets.pojo.Pets;
import com.petsAdoption.pets.pojo.Template;
import com.petsAdoption.pets.serive.PetsService;
import com.petsAdoption.pets.mapper.PetsMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuxingyu
 * @description 针对表【t_pets】的数据库操作Service实现
 * @createDate 2022-09-25 08:48:31
 */
@Service
@DubboService
public class PetsServiceImpl extends ServiceImpl<PetsMapper, Pets> implements PetsService {
    @Autowired
    private PetsMapper petsMapper;


    /*
     * @Description:  返回所有的宠物种类
       * @param
     * @return: java.util.List<java.lang.String>
     * @Author: wuxingyu
     * @Date: 2022/9/25 8:51
     */
    @Override
    public List<String> getAllKinds() {
        List<String> kindsList = petsMapper.getAllKinds();
        return kindsList;
    }

    @Override
    public List<String> getCategoryByKinds(String kind) {
        List<String> categoryList = petsMapper.getCategoryByKinds(kind);
        return categoryList;
    }

    @Override
    public List<Template> getTemplateByKindsAndCategory(String kind, String category) {
        List<Template> template = petsMapper.getTemplateByKindsAndCategory(kind, category);
        return template;
    }

    @Override
    public String getIdByKindsAndCategory(String kind, String category) {
        String id = petsMapper.getIdByKindsAndCategory(kind, category);
        return id;
    }
}




