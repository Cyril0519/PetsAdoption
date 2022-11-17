package com.petsAdoption.search.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // 交换机名称
    public static final String PETS_UP_EXCHANGE = "pets_up_exchange";
    public static final String PETS_DOWN_EXCHANGE = "pets_down_exchange";
    // 定义队列名称
    public final static String PETS_OFF_SHELVE_QUEUE = "pets_off_shelve_queue"; // 宠物下架消息队列 （修改删除）
    public static final String PETS_SHELVE_QUEUE = "pets_shelve_queue";    // 宠物上架消息队列 （audited：0-->1）

    @Bean(PETS_SHELVE_QUEUE)
    public Queue PETS_SHELVE_QUEUE() {
        return new Queue(PETS_SHELVE_QUEUE);
    }

    @Bean(PETS_OFF_SHELVE_QUEUE)
    public Queue PETS_OFF_SHELVE_QUEUE() {
        return new Queue(PETS_OFF_SHELVE_QUEUE);
    }

    @Bean(PETS_UP_EXCHANGE)
    public Exchange PETS_UP_EXCHANGE() {
        return ExchangeBuilder.fanoutExchange(PETS_UP_EXCHANGE).durable(true).build();
    }

    @Bean(PETS_DOWN_EXCHANGE)
    public Exchange PETS_DOWN_EXCHANGE() {
        return ExchangeBuilder.fanoutExchange(PETS_DOWN_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding PETS_UP_EXCHANGE_BIND(@Qualifier(PETS_OFF_SHELVE_QUEUE) Queue queue, @Qualifier(PETS_DOWN_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

    @Bean
    public Binding PETS_DOWN_EXCHANGE_BIND(@Qualifier(PETS_SHELVE_QUEUE) Queue queue, @Qualifier(PETS_UP_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

}
