package com.petsAdoption.business.controller;

import com.petsAdoption.business.service.AdService;
import com.petsAdoption.bussiness.pojo.Ad;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController {
    @Autowired
    private AdService adService;

    @GetMapping("")
    public Result<List<Ad>> getAd() {
        List<Ad> ad = adService.getAd();
        return new Result<>(true, ResultCode.OK, "查询成功", ad);
    }

    @PostMapping("add")
    public Result<Void> add(@RequestBody Ad ad) {
        adService.save(ad);
        return new Result<>(true, ResultCode.OK, "保存成功");
    }

    @PutMapping("update")
    public Result<Void> update(@RequestBody Ad ad) {
        adService.updateById(ad);
        return new Result<>(true, ResultCode.OK, "修改成功");
    }
}
