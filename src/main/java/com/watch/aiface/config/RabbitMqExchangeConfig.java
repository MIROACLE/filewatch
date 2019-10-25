package com.watch.aiface.config;


import com.watch.aiface.base.constant.BaseConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class RabbitMqExchangeConfig {

    @Bean
    public Queue queueFileDelete(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(BaseConstant.JPATH_DELETE);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    // 创建一个延时删除队列
    @Bean
    public Queue delayDeleteQueue(RabbitAdmin rabbitAdmin) {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", BaseConstant.IMMEDIATE_DELETE_EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", BaseConstant.IMMEDIATE_DELETE_ROUTING_KEY);
        Queue queue = new Queue("DELAY_DELETE_QUEUE", true, false, false, params);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public DirectExchange immediateDeleteExchange(RabbitAdmin rabbitAdmin) {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        DirectExchange directExchange = new DirectExchange(BaseConstant.IMMEDIATE_DELETE_EXCHANGE, true, false);
        rabbitAdmin.declareExchange(directExchange);
        return directExchange;
    }

    @Bean
    public DirectExchange deadLetterDeleteExchange(RabbitAdmin rabbitAdmin) {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        DirectExchange directExchange = new DirectExchange(BaseConstant.DEAD_DELETE_LETTER_EXCHANGE, true, false);
        rabbitAdmin.declareExchange(directExchange);
        return directExchange;
    }

    @Bean
    //把立即消费的队列和立即消费的exchange绑定在一起
    public Binding immediateDeleteBinding(Queue queueFileDelete, DirectExchange immediateDeleteExchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueFileDelete).to(immediateDeleteExchange).with(BaseConstant.IMMEDIATE_DELETE_ROUTING_KEY);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    //把延时消费的队列和延时消费的exchange绑定在一起
    public Binding delayDeleteBinding(Queue delayDeleteQueue, DirectExchange deadLetterDeleteExchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(delayDeleteQueue).to(deadLetterDeleteExchange).with(BaseConstant.DELAY_DELETE_ROUTING_KEY);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }
}
