package com.petsAdoption.pets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petsAdoption.pets.pojo.PetsDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.petsAdoption.pets.pojo.UnauditedPet;

import java.util.List;

/**
* @author wuxingyu
* @description 针对表【t_pets_detail】的数据库操作Service
* @createDate 2022-09-25 08:48:43
*/
public interface PetsDetailService extends IService<PetsDetail> {
    void postPets(PetsDetail petsDetail);

    void updatePets(PetsDetail petsDetail);

    Page<PetsDetail> getUserPets(String uid, Integer page, Integer size);

    void updateCount(PetsDetail petsDetail);

    Page<UnauditedPet> getUnauthorisedList(Integer current, Integer size);

    void authenticationOk(String id);

    void authenticationFailure(String id);

    void refund(String petId, Integer petCount);

    void deduct(String petId, int count);
}
