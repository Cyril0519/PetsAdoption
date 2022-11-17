package com.petsAdoption.pets.service;

import com.petsAdoption.pets.pojo.Pets;
import com.baomidou.mybatisplus.extension.service.IService;
import com.petsAdoption.pets.pojo.Template;

import java.util.List;

/**
* @author wuxingyu
* @description 针对表【t_pets】的数据库操作Service
* @createDate 2022-09-25 08:48:31
*/
public interface PetsService extends IService<Pets> {
    List<String> getAllKinds();

    List<String> getCategoryByKinds(String kind);

    List<Template> getTemplateByKindsAndCategory(String kind, String category);

    String getIdByKindsAndCategory(String kind, String category);
}
