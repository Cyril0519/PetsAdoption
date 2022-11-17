package com.petsAdoption.search.listener;

import com.petsAdoption.search.config.RabbitMQConfig;
import com.petsAdoption.search.service.ESManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 宠物上架监听类
@Component
public class PetsShelveListener {

    @Autowired
    private ESManagerService esManagerService;

    @RabbitListener(queues = RabbitMQConfig.PETS_SHELVE_QUEUE)
    public void receiveMessage(String id) {
        esManagerService.importDataByPetsId(id);
    }
}
