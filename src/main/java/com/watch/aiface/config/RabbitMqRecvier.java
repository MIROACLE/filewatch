package com.watch.aiface.config;


import com.alibaba.fastjson.JSONObject;
import com.watch.aiface.base.constant.BaseConstant;
import com.watch.aiface.dispatch.pojo.po.FaceCamera;
import com.watch.aiface.dispatch.repository.FaceCameraRepository;
import com.watch.aiface.dispatch.service.FaceWarnRuleService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Component
@AutoConfigureAfter(RabbitMqConfig.class)
public class RabbitMqRecvier {


    @Autowired
    private Executor laputaExecutor;

    @Autowired
    private FaceWarnRuleService faceWarnRuleService;

    @Autowired
    private FaceCameraRepository faceCameraRepository;

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqRecvier.class);

    @Bean
    public String[] mqMsgQueues(RabbitAdmin rabbitAdmin) {
        List<FaceCamera> faceCameras = faceCameraRepository.findAll();
        String[] queueNames = faceCameras.stream().map(faceCamera -> {
            String queueName = faceCamera.getSn();
            bindMqQueues(queueName, rabbitAdmin);
            return queueName;
        }).toArray(String[]::new);
        return queueNames;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(CachingConnectionFactory connectionFactory, RabbitAdmin rabbitAdmin) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        String[] msgQueues = mqMsgQueues(rabbitAdmin);
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(msgQueues);
        container.setMessageListener(exampleListener());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setPrefetchCount(200);
        container.setConcurrentConsumers(msgQueues.length * 2);

        return container;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer2(CachingConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(BaseConstant.JPATH_DELETE);
        container.setMessageListener(exampleListener2());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setPrefetchCount(200);
        container.setConcurrentConsumers(60);
        return container;
    }

    @Bean
    public ChannelAwareMessageListener exampleListener() {
        return new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                String context = new String(message.getBody(), "UTF-8");
                logger.info("处理图片队列开始处理:" + context);
                laputaExecutor.execute(() -> {
                    try {
                        faceWarnRuleService.checkRuleBycameraEqpSn(JSONObject.parseObject(context));
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("处理失败！");
                    } finally {
                        try {
                            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
    }

    @Bean
    public ChannelAwareMessageListener exampleListener2() {
        return new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                String context = new String(message.getBody(), "UTF-8");
                logger.info("删除图片队列开始处理" + context);
                try {
                    deleteFile(context);
                } catch (Exception e) {
                    logger.error("处理失败！");
                } finally {
                    try {
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
    }

    private void deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public void bindMqQueues(String queueName, RabbitAdmin rabbitAdmin) {
        //立即消费队列
        Queue queue = new Queue(queueName);
        rabbitAdmin.declareQueue(queue);
        //创建一个延时队列
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", BaseConstant.IMMEDIATE_EXCHANGE + "_" + queueName);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", BaseConstant.IMMEDIATE_ROUTING_KEY + "_" + queueName);
        Queue delayQueue = new Queue("DELAY_QUEUE" + "_" + queueName, true, false, false, params);
        rabbitAdmin.declareQueue(delayQueue);
        //立即消费路由
        DirectExchange directExchange = new DirectExchange(BaseConstant.IMMEDIATE_EXCHANGE + "_" + queueName, true, false);
        rabbitAdmin.declareExchange(directExchange);
        //延迟消费路由
        DirectExchange deadExchange = new DirectExchange(BaseConstant.DEAD_LETTER_EXCHANGE + "_" + queueName, true, false);
        rabbitAdmin.declareExchange(deadExchange);
        //绑定
        Binding binding = BindingBuilder.bind(queue).to(directExchange).with(BaseConstant.IMMEDIATE_ROUTING_KEY + "_" + queueName);
        rabbitAdmin.declareBinding(binding);
        //绑定
        Binding deadBinding = BindingBuilder.bind(delayQueue).to(deadExchange).with(BaseConstant.DELAY_ROUTING_KEY + "_" + queueName);
        rabbitAdmin.declareBinding(deadBinding);
    }
}
