package com.petsAdoption.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.petsAdoption.pets.pojo.Pets;
import com.petsAdoption.pets.pojo.PetsDetail;
import com.petsAdoption.pets.serive.PetsDetailService;
import com.petsAdoption.pets.serive.PetsService;
import com.petsAdoption.search.mapper.ESManagerMapper;
import com.petsAdoption.search.pojo.PetsInfo;
import com.petsAdoption.search.service.ESManagerService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;

@Service
public class ESManagerServiceImpl implements ESManagerService {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @DubboReference
    private PetsDetailService petsDetailService;
    @DubboReference
    private PetsService petsService;
    @Autowired
    private ESManagerMapper esManagerMapper;

    /*
     * @Description: 创建索引，一开始系统会自动创建，需要先手动删除es索引
       * @param
     * @return: void
     * @Author: wuxingyu
     * @Date: 2022/9/25 9:59
     */
    @Override
    public void createMappingAndIndex() {
        boolean index = elasticsearchRestTemplate.createIndex(PetsInfo.class);
        elasticsearchRestTemplate.putMapping(PetsInfo.class);
    }

    /*
     * @Description: 导入所有数据
       * @param
     * @return: void
     * @Author: wuxingyu
     * @Date: 2022/9/25 10:00
     */
    @Override
    public void importAll() {
        List<PetsDetail> petsDetailList = petsDetailService.list(null);
        if (petsDetailList == null || petsDetailList.size() <= 0) {
            throw new RuntimeException("没有数据被查询到，无法导入索引库");
        }
        this.saveDataByList(petsDetailList);
    }

    @Override
    public void importDataByPetsId(String petId) {
        QueryWrapper<PetsDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("id", petId);
        List<PetsDetail> petsDetailList = petsDetailService.list(wrapper);

        this.saveDataByList(petsDetailList);
    }

    @Override
    public void delDataByPetId(String petId) {
        esManagerMapper.deleteById(Long.parseLong(petId));
    }

    private void saveDataByList(List<PetsDetail> petsDetailList) {
        String jsonString = JSON.toJSONString(petsDetailList);
        List<PetsInfo> petsInfos = JSON.parseArray(jsonString, PetsInfo.class);
        for (PetsInfo petsInfo : petsInfos) {
            String petsId = petsInfo.getPetsId();
            Pets pets = petsService.getById(petsId);  // 获取其所属的类
            assert pets !=null;  // 查询不是空

            String detailInfo = petsInfo.getDetailInfo();
            Map petDailInfoMap = JSON.parseObject(detailInfo, Map.class);  // 设置详细信息的map

            petsInfo.setDetailMap(petDailInfoMap);
            petsInfo.setKinds(pets.getKinds());   // 设置种类
            petsInfo.setCategory(pets.getCategory());  // 设置品种
        }
        esManagerMapper.saveAll(petsInfos);
    }

}
