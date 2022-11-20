package com.petsAdoption.pets.controller;

import com.petsAdoption.pets.pojo.Pets;
import com.petsAdoption.pets.pojo.Template;
import com.petsAdoption.pets.serive.PetsService;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetsController {
    @Autowired
    private PetsService petsService;

    @GetMapping("/getKinds")
    public Result<List<String>> getKinds() {
        List<String> kinds = petsService.getAllKinds();
        return new Result<>(true, ResultCode.OK, "查询成功", kinds);
    }

    @GetMapping("/getCategory")
    public Result<List<String>> getCategory(@RequestParam("kind") String kind) {
        List<String> categoryList = petsService.getCategoryByKinds(kind);
        return new Result<>(true, ResultCode.OK, "查询成功", categoryList);
    }

    @GetMapping("/getTemplate")
    public Result<List<Template>> getTemplate(@RequestParam("kind") String kind, @RequestParam("category") String category) {
        List<Template> templates = petsService.getTemplateByKindsAndCategory(kind, category);
        return new Result<>(true, ResultCode.OK, "查询成功", templates);
    }

    @GetMapping("/getIdByKindsAndCategory")
    public Result<String> getIdByKindsAndCategory(@RequestParam("kind") String kind, @RequestParam("category") String category){
        String id = petsService.getIdByKindsAndCategory(kind, category);
        return new Result<>(true, ResultCode.OK, "查询成功", id);
    }
    /*
     * @Description: 通过id获取pets类,让petDetail信息完整
     * @param id ：t_pets表的id
     * @return: com.petsAdoption.pojo.Result<java.lang.String>
     * @Author: wuxingyu
     * @Date: 2022/9/25 16:11
     */
    @GetMapping("/getPetsById")
    public Result<Pets> getPetsById(@RequestParam("id") String id) {
        Pets petsInfo = petsService.getById(id);
        return new Result<>(true, ResultCode.OK, "查询成功", petsInfo);
    }

}
