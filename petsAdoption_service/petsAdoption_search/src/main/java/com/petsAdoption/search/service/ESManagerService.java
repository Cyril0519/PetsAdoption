package com.petsAdoption.search.service;


/*
* @description:  实现增删
 * @param null
* @return:
* @author: "wuxingyu"
* @time: 2022/9/10 14:33
*/
public interface ESManagerService {
    // 创建索引库结构
    void createMappingAndIndex();

    // 导入全部数据进入es
    void importAll();

    // 根据petId查询PetList，再导入索引库
    void importDataByPetsId(String petId);

    // 根据petId删除es索引库中相关的pet数据
    void delDataByPetId(String petId);
}
