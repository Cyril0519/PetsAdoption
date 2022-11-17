package com.petsAdoption.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.petsAdoption.canal.config.RabbitMQConfig;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@CanalEventListener
public class PetsDetailListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @ListenPoint(schema = "petsAdoption_pets",table = "t_pets_detail")
    public void petsDetailChangeListener(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        System.out.println("宠物表数据发生改变");
        //  获取改变前的数据并将这部分转为map
        Map<String, String> oldData = new HashMap<>();
        rowData.getBeforeColumnsList().forEach((c)->{
            oldData.put(c.getName(), c.getValue());
        });
        // 获取改变后的数据并将这部分转为map
        Map<String, String> newData = new HashMap<>();
        rowData.getAfterColumnsList().forEach((c)->{
            newData.put(c.getName(), c.getValue());
        });

        // 审核成功上架 0->1
        if ("0".equals(oldData.get("audited")) && "1".equals(newData.get("audited"))) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.PETS_UP_EXCHANGE,"", newData.get("id"));
        }

        // 审核失败下架 1->0
        else if ("1".equals(oldData.get("audited")) && "0".equals(newData.get("audited"))) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.PETS_DOWN_EXCHANGE, "", newData.get("id"));
        }

        // 宠物取消删除上架
        else if ("1".equals(oldData.get("is_deleted")) && "0".equals(newData.get("is_deleted"))) {
            // 将商品的spu发送到mq
            rabbitTemplate.convertAndSend(RabbitMQConfig.PETS_UP_EXCHANGE, "", newData.get("id"));
        }

        // 宠物删除下架
        else if ("0".equals(oldData.get("is_deleted")) && "1".equals(newData.get("is_deleted"))) {
            // 将商品的spu发送到mq
            rabbitTemplate.convertAndSend(RabbitMQConfig.PETS_DOWN_EXCHANGE, "", newData.get("id"));
        }

    }
}
