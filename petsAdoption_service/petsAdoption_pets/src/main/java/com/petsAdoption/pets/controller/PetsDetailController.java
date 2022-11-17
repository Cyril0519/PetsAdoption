package com.petsAdoption.pets.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petsAdoption.pets.config.TokenDecode;
import com.petsAdoption.pets.pojo.PetFrezze;
import com.petsAdoption.pets.pojo.PetsDetail;
import com.petsAdoption.pets.pojo.UnauditedPet;
import com.petsAdoption.pets.service.PetFrezzeService;
import com.petsAdoption.pets.service.PetsDetailService;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/petDetail")
public class PetsDetailController {
    @Autowired
    private PetsDetailService petsDetailService;
    @Autowired
    private TokenDecode tokenDecode;
    @Autowired
    private PetFrezzeService petFrezzeService;

    /*
     * @Description:
     * @param id
     * @return: com.petsAdoption.pojo.Result
     * @Author: wuxingyu
     * @Date: 2022/9/24 16:52
     */
    @GetMapping("/getById")
    public Result<List<PetsDetail>> getById(@RequestParam("id") String id) {
        List<PetsDetail> petsDetailList;
        if (id.equals("all")) {
            petsDetailList = petsDetailService.list();
        } else {
            petsDetailList = petsDetailService.listByIds(Arrays.asList(id.split(",")));
        }
        return new Result<>(true, ResultCode.OK, "查询成功", petsDetailList);
    }


    // 获取未认证的宠物列表
    @GetMapping("/unAuditList/{current}/{size}")
    public Result<Page<UnauditedPet>> unauditedList(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {
        Page<UnauditedPet> unauthorisedList = petsDetailService.getUnauthorisedList(current,size);
        return new Result<>(true, ResultCode.OK, "查询成功", unauthorisedList);
    }

    // 认证成功
    @PutMapping("/authenticationOk")
    public Result<Void> authenticationOk(@RequestParam("id") String id) {
        petsDetailService.authenticationOk(id);
        return new Result<>(true, ResultCode.OK, "审核通过");
    }

    // 认证失败
    @PutMapping("/authenticationFailure")
    public Result<Void> authenticationFailure(@RequestParam("id") String id) {
        petsDetailService.authenticationOk(id);
        return new Result<>(true, ResultCode.OK, "审核失败");
    }

    /*
     * @Description:  发布宠物
     * @param petsDetail 宠物实体类
     * @return: com.petsAdoption.pojo.Result<java.lang.Void>
     * @Author: wuxingyu
     * @Date: 2022/9/30 19:35
     */
    @PostMapping("/postPets")
    public Result<Void> postPets(@RequestBody PetsDetail petsDetail) {
        petsDetailService.postPets(petsDetail);
        return new Result<>(true, ResultCode.OK, "保存成功");
    }

    @PutMapping("/updatePets")
    public Result<Void> updatePets(@RequestBody PetsDetail petsDetail) {
        petsDetailService.updatePets(petsDetail);
        return new Result<>(true, ResultCode.OK, "修改成功");
    }

    @DeleteMapping("/deleteById")
    public Result<Void> deleteById(@RequestParam("id") String id) {
        boolean b = petsDetailService.removeById(id);
        return new Result<>(true, ResultCode.OK, "删除成功");
    }

    @GetMapping("/getUserPets/{current}/{size}")
    public Result<Page<PetsDetail>> getUserPets(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {
        String userId = tokenDecode.getUserInfo().get("id");
        Page<PetsDetail> resultList = petsDetailService.getUserPets(userId,current, size);
        return new Result<>(true, ResultCode.OK, "查询成功", resultList);
    }

    @PutMapping("/updateCount")
    public Result<Void> updateCount(@RequestBody PetsDetail petsDetail) {
        petsDetailService.updateCount(petsDetail);
        return new Result<>(true, ResultCode.OK, "修改成功");
    }

    @PutMapping("/deduct")
    public Result<Void> deduct(@RequestParam("petId") String petId, @RequestBody Integer count) {
        petFrezzeService.deduct(petId, count);
        return new Result<>(true, ResultCode.OK, "修改成功");
    }
}
