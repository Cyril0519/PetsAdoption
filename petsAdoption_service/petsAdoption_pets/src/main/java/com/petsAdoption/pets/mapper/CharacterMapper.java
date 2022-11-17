package com.petsAdoption.pets.mapper;

import com.petsAdoption.pets.pojo.Template;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wuxingyu
* @description 针对表【t_character】的数据库操作Mapper
* @createDate 2022-09-25 08:48:55
* @Entity com.petsAdoption.pets.pojo.Character
*/
@Mapper
public interface CharacterMapper extends BaseMapper<Template> {

}




