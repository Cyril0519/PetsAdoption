package com.petsAdoption.address.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.petsAdoption.address.pojo.Region;
import com.petsAdoption.address.service.impl.RegionServiceImpl;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionServiceImpl regionService;

    /*
     * @Description: 通过pid获取当前地址的下一级地址
     * @param pid ：
     * @return: com.petsAdoption.pojo.Result<java.util.List<com.petsAdoption.address.pojo.Region>>
     * @Author: wuxingyu
     * @Date: 2022/10/14 19:55
     */
    @GetMapping("/getSubregion")
    public Result<List<Region>> getById(@RequestParam(value = "pid", required = false, defaultValue = "100000") String pid) {
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        List<Region> regionList = regionService.list(queryWrapper);
        return new Result<>(true, ResultCode.OK, "查询成功", regionList);
    }

    /*
     * @Description: 通过id获取当前的地址
     * @param id 需要查询的地址id
     * @return: com.petsAdoption.pojo.Result<com.petsAdoption.address.pojo.Region>
     * @Author: wuxingyu
     * @Date: 2022/10/14 19:57
     */
    @GetMapping("/getRegion")
    public Result<Region> getRegion(@RequestParam("id") String id) {
        Region region = regionService.getById(id);
        return new Result<>(true, ResultCode.OK, "查询成功", region);
    }
}
