package com.watch.aiface.config;


import com.alibaba.fastjson.JSONObject;
import com.watch.aiface.base.constant.BaseConstant;
import com.watch.aiface.base.util.JodaTimeUtil;
import com.watch.aiface.dispatch.constant.DetectConstant;
import com.watch.aiface.dispatch.pojo.po.FaceWarnRule;
import com.watch.aiface.dispatch.repository.FaceWarnRuleRepository;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RabbitMqSender implements RabbitTemplate.ConfirmCallback {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);

    private RabbitTemplate rabbitTemplate;

    @Value("${WatchPath}")
    private String WatchPath;

    @Autowired
    private FaceWarnRuleRepository faceWarnRuleRepository;

    @Resource
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitMqRecvier rabbitMqRecvier;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        logger.info("confirm: " + correlationData.getId());
    }

    /**
     * 发送到 指定routekey的指定queue
     */
    public void sendRabbitmqDirect(Path context) {
        //如果是摄像头，则动态添加队列
        if (Files.isDirectory(context.toAbsolutePath())) {
            String rootPath = context.toString().substring(WatchPath.length());
            if (rootPath.indexOf(File.separator) == -1) {
                rabbitMqRecvier.bindMqQueues(rootPath, rabbitAdmin);
            }
            return;
        }
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        logger.info("send: " + correlationData.getId());
        ConcurrentHashMap<String, String> cameraEqpMap = getCameraEqpSn(context.toString(), WatchPath);
        String cameraEqpSn = cameraEqpMap.get("cameraEqpSn");
        String fileName = cameraEqpMap.get("fileName");
        PredicateBuilder<FaceWarnRule> pb = Specifications.<FaceWarnRule>and();
        pb.eq("cameraEqpSn", cameraEqpSn);
        FaceWarnRule faceWarnRule = faceWarnRuleRepository.findOne(pb.build());
        // 判断当前处理的Sn有无对应规则，当前处理的图片是否符合报警频率
        if (faceWarnRule != null && checkWarnRate(fileName, faceWarnRule.getWarnRate(), cameraEqpSn, faceWarnRule.getWarnType())) {
            String cameraEqpMapJson = JSONObject.toJSONString(cameraEqpMap);
            String faceWarnRuleJson = JSONObject.toJSONString(faceWarnRule);
            JSONObject sendJson = new JSONObject();
            sendJson.put("cameraEqpMap", cameraEqpMapJson);
            sendJson.put("faceWarnRule", faceWarnRuleJson);
            this.rabbitTemplate.convertAndSend(BaseConstant.DEAD_LETTER_EXCHANGE + "_" + cameraEqpSn, BaseConstant.DELAY_ROUTING_KEY + "_" + cameraEqpSn, JSONObject.toJSONString(sendJson), message -> {
                message.getMessageProperties().setExpiration("2000");
                return message;
            }, correlationData);
        } else {
            this.rabbitTemplate.convertAndSend(BaseConstant.DEAD_DELETE_LETTER_EXCHANGE, BaseConstant.DELAY_DELETE_ROUTING_KEY, context.toString(), message -> {
                message.getMessageProperties().setExpiration("2000");
                return message;
            }, correlationData);
        }
    }

    private ConcurrentHashMap<String, String> getCameraEqpSn(String context, String path) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("absolutePath", context);// 绝对路径
        String rootPath = context.substring(path.length());
        map.put("cameraEqpSn", rootPath.substring(0, rootPath.indexOf(File.separator)));// 摄像头Sn
        map.put("fileName", context.substring(context.lastIndexOf(File.separator) + 1));// 文件名
        return map;
    }

    private boolean checkWarnRate(String fileName, Integer WarnRate, String cameraEqpSn, Integer warnType) {
        boolean flag = false;
        if (warnType == DetectConstant.OBJECT_DETECT.getCode()) return true;
        String[] str1 = fileName.split("_");
        long currentFileDate = JodaTimeUtil.strToDate(str1[2], JodaTimeUtil.FORMAT_YMDHMSS).getTime();
        String lastFileDate = System.getProperty(cameraEqpSn);
        if (StringUtils.isBlank(lastFileDate)) {
            System.setProperty(cameraEqpSn, currentFileDate + "");
            flag = true;
        } else {
            if (currentFileDate - Long.parseLong(lastFileDate) >= WarnRate.intValue() * 1000) {
                System.setProperty(cameraEqpSn, currentFileDate + "");
                flag = true;
            }
        }
        return flag;
    }
}
