package com.petsAdoption.pets.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.pets.pojo.PetsTemplate;
import com.petsAdoption.pets.service.CharacterTemplateService;
import com.petsAdoption.pets.mapper.CharacterTemplateMapper;
import org.springframework.stereotype.Service;

/**
* @author wuxingyu
* @description 针对表【t_character_template】的数据库操作Service实现
* @createDate 2022-09-25 08:48:49
*/
@Service
public class CharacterTemplateServiceImpl extends ServiceImpl<CharacterTemplateMapper, PetsTemplate>
    implements CharacterTemplateService{

}




