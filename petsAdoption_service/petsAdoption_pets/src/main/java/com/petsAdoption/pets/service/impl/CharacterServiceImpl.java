package com.petsAdoption.pets.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.pets.pojo.Template;
import com.petsAdoption.pets.serive.CharacterService;
import com.petsAdoption.pets.mapper.CharacterMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
* @author wuxingyu
* @description 针对表【t_character】的数据库操作Service实现
* @createDate 2022-09-25 08:48:55
*/
@Service
@DubboService
public class CharacterServiceImpl extends ServiceImpl<CharacterMapper, Template>
    implements CharacterService{

}




