package com.petsAdoption.search.service;

import com.petsAdoption.search.pojo.PetsInfo;

import java.util.List;
import java.util.Map;

public interface SearchService {
    // 按照查询条件进行数据查询
    Map search(Map<String, String> searchMap);


}
