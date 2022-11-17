package com.petsAdoption.search.mapper;

import com.petsAdoption.search.pojo.PetsInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Component;

@EnableElasticsearchRepositories
public interface ESManagerMapper extends ElasticsearchRepository<PetsInfo, Long> {
}
