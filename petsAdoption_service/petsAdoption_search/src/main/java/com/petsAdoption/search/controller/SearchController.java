package com.petsAdoption.search.controller;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import com.petsAdoption.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("")
    public Result search(@RequestParam Map<String, String> searchMap) {
        Map search = searchService.search(searchMap);
        return new Result(true, ResultCode.OK, "查询成功", search);
    }

}
