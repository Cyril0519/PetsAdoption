package com.petsAdoption.pets.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.pets.config.TokenDecode;
import com.petsAdoption.pets.pojo.PetsDetail;
import com.petsAdoption.pets.pojo.UnauditedPet;
import com.petsAdoption.pets.service.PetsDetailService;
import com.petsAdoption.pets.mapper.PetsDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author wuxingyu
* @description 针对表【t_pets_detail】的数据库操作Service实现
* @createDate 2022-09-25 08:48:43
*/
@Service
public class PetsDetailServiceImpl extends ServiceImpl<PetsDetailMapper, PetsDetail> implements PetsDetailService{

    @Autowired
    private TokenDecode tokenDecode;
    @Autowired
    private PetsDetailMapper petsDetailMapper;

    public void postPets(PetsDetail petsDetail){
        petsDetail.setAudited(0);
        petsDetail.setPostTime(new Date());

        String owner_id = tokenDecode.getUserInfo().get("id");
        petsDetail.setOwnerId(owner_id);

        this.save(petsDetail);
    }

    @Override
    public void updatePets(PetsDetail petsDetail) {
        petsDetail.setAudited(0);
        if (petsDetail.getNumber()<0) {
            throw new RuntimeException("数量不允许小于0");
        }
        this.updateById(petsDetail);
    }

    @Override
    public Page<PetsDetail> getUserPets(String uid,Integer current, Integer size) {
        Page<PetsDetail> page = new Page<>(current, size);
        QueryWrapper<PetsDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("owner_id", uid);
        Page<PetsDetail> pageResult = petsDetailMapper.selectPage(page, queryWrapper);
        return pageResult;
    }

    @Override
    public void updateCount(PetsDetail petsDetail) {
        UpdateWrapper<PetsDetail> query = new UpdateWrapper<>();
        query.eq("id", petsDetail.getId()).set("number", petsDetail.getNumber());
        this.update(query);
    }

    @Override
    public Page<UnauditedPet> getUnauthorisedList(Integer current, Integer size) {
        Page<UnauditedPet> page = new Page<>(current, size);
        Page<UnauditedPet> pageResult = petsDetailMapper.getUnauditedPet(page);

        return pageResult;
    }

    @Override
    public void authenticationOk(String id) {
        UpdateWrapper<PetsDetail> query = new UpdateWrapper<>();
        query.eq("id", id);
        query.set("audited","1");
        this.update(query);
    }

    @Override
    public void authenticationFailure(String id) {
        UpdateWrapper<PetsDetail> query = new UpdateWrapper<>();
        query.eq("id", id);
        query.set("audited","0");
        this.update(query);
    }

    @Override
    public void refund(String petId, Integer petCount) {
        petsDetailMapper.refund(petId, count());
    }

    @Override
    public void deduct(String petId, int count) {
        PetsDetail petsDetail = petsDetailMapper.selectById(petId);
        if (petsDetail.getNumber()>=count) {
            petsDetailMapper.deduct(petId, count());
        }else{
            throw new RuntimeException("宠物数量不足");
        }
    }

}




