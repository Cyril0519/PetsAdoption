package com.petsAdoption.search.controller;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import com.petsAdoption.search.service.ESManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ESManagerController {
    @Autowired
    private ESManagerService esManagerService;

    @GetMapping("/create")
    public Result<Void> create() {

        esManagerService.createMappingAndIndex();
        return new Result<>(true, ResultCode.OK, "创建索引库成功");
    }

    // 导入全部数据
    @GetMapping("/importAll")
    public Result importAll(){
        esManagerService.importAll();
        return new Result(true, ResultCode.OK, "导入全部数据成功");
    }

    // 导入全部数据
    @GetMapping("/importById")
    public Result importById(@RequestParam("id") String id){
        esManagerService.importDataByPetsId(id);
        return new Result(true, ResultCode.OK, "导入数据成功");
    }

    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") String id){
        esManagerService.delDataByPetId(id);
        return new Result(true, ResultCode.OK, "导入数据成功");
    }
}
