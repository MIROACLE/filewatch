package com.watch.aiface.dispatch.service;

import com.alibaba.fastjson.JSONObject;
import com.watch.aiface.dispatch.constant.DetectConstant;
import com.watch.aiface.dispatch.pojo.po.FaceWarnRule;
import com.watch.aiface.dispatch.util.AiFaceApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PicDetectService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 算法分析之后，推送告警
     *
     * @param faceWarnRule 告警规则
     * @param cameraEqpMap 包含绝对路径、摄像头Sn、文件名（包含报警时间）
     * @throws Exception
     */
    public void dealFileWithAi(FaceWarnRule faceWarnRule, JSONObject cameraEqpMap) {
        // 使用faceWarnRule和cameraEqpMap组装对象
        Integer warnType = faceWarnRule.getWarnType();

        if (DetectConstant.OBJECT_DETECT.getCode() == warnType) {// 围栏
            AiFaceApiUtil.objectDetect(faceWarnRule, cameraEqpMap);
        } else if (DetectConstant.HELMET_DETECT.getCode() == warnType) {// 安全帽
            AiFaceApiUtil.helmetDetect(faceWarnRule, cameraEqpMap);
        } else {
        }

    }

}
