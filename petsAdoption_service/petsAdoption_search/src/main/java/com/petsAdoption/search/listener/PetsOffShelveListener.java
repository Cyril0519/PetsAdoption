package com.petsAdoption.search.listener;

import com.petsAdoption.search.config.RabbitMQConfig;
import com.petsAdoption.search.service.ESManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.stereotype.Component;

@Component
public class PetsOffShelveListener {
    @Autowired
    private ESManagerService esManagerService;

    @RabbitListener(queues = RabbitMQConfig.PETS_OFF_SHELVE_QUEUE)
    public void receiveMessage(String id) {
        esManagerService.delDataByPetId(id);
    }
}
