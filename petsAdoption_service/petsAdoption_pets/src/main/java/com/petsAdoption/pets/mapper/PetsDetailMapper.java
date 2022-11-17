package com.petsAdoption.pets.mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petsAdoption.pets.pojo.UnauditedPet;
import org.apache.ibatis.annotations.Param;

import com.petsAdoption.pets.pojo.PetsDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* @author wuxingyu
* @description 针对表【t_pets_detail】的数据库操作Mapper
* @createDate 2022-09-25 08:48:42
* @Entity com.petsAdoption.pets.pojo.PetsDetail
*/
@Mapper
public interface PetsDetailMapper extends BaseMapper<PetsDetail> {

    List<PetsDetail> getAllByOwnerId(@Param("ownerId") String ownerId);

    Page<UnauditedPet> getUnauditedPet(Page<UnauditedPet> page);

    void refund(@Param("petId") String petId,@Param("count") int count);

    void deduct(@Param("petId") String petId,@Param("count") int count);
}




