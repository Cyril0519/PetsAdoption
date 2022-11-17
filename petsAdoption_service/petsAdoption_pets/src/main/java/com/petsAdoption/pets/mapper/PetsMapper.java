package com.petsAdoption.pets.mapper;

import com.petsAdoption.pets.pojo.Pets;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petsAdoption.pets.pojo.Template;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wuxingyu
* @description 针对表【t_pets】的数据库操作Mapper
* @createDate 2022-09-25 08:48:31
* @Entity com.petsAdoption.pets.pojo.Pets
*/
@Mapper
public interface PetsMapper extends BaseMapper<Pets> {

    List<String> getAllKinds();

    List<String> getCategoryByKinds(@Param("kind") String kind);

    String getIdByKindsAndCategory(@Param("kinds") String kinds, @Param("category") String category);

    List<Template> getTemplateByKindsAndCategory(@Param("kind") String kind, @Param("category") String category);

}




