
package com.watch.aiface.dispatch.service;

import com.alibaba.fastjson.JSONObject;
import com.watch.aiface.base.util.JodaTimeUtil;
import com.watch.aiface.dispatch.pojo.po.FaceWarnRule;
import com.watch.aiface.dispatch.pojo.po.PicDetectQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;

@Service
public class FaceWarnRuleService {

    private static Logger logger = LoggerFactory.getLogger(FaceWarnRuleService.class);

    // 存放图片路径
    @Value("${DestPath}")
    private String DEST_FILE_DIR;

    @Autowired
    private PicDetectService picDetectService;

    public void checkRuleBycameraEqpSn(JSONObject context) {
        JSONObject cameraEqpMap = context.getJSONObject("cameraEqpMap");
        FaceWarnRule faceWarnRule = JSONObject.parseObject(context.getString("faceWarnRule"), FaceWarnRule.class);
        String cameraEqpSn = cameraEqpMap.getString("cameraEqpSn");
        String absolutePath = cameraEqpMap.getString("absolutePath");
        // 判断文件类型和文件是否已经处理
        boolean detectPics = detectPics(cameraEqpSn, cameraEqpMap.getString("fileName"), absolutePath, cameraEqpMap);
        if (!detectPics)
            return;
        picDetectService.dealFileWithAi(faceWarnRule, cameraEqpMap);
    }

    private void deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    private boolean detectPics(String cameraEqpSn, String fileName, String filePath,
                               JSONObject cameraEqpMap) {
        boolean flag = false;

        String destPath = DEST_FILE_DIR + cameraEqpSn + "\\" + fileName;
        File directory = new File(DEST_FILE_DIR + cameraEqpSn);
        if (!directory.exists()) {//如果文件夹不存在
            directory.mkdir();//创建文件夹
        }
        cameraEqpMap.put("destPath", destPath);
        File f = new File(filePath);
        // 是合法jpeg图片的时候放入待检测队列，非法图片直接丢弃
        //Boolean jpeg = ImageUtil.isJPEG(destPath);
        if (filePath.toLowerCase().endsWith("jpg")) {
            // 是满足要求的图片的时候
            File dest = new File(destPath);
            // 拷贝到目的地
            if (!dest.exists()) {
                try {
                    Files.copy(f.toPath(), dest.toPath());
                    deleteFile(filePath);// 删除原始文件
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] str1 = f.getName().split("_");
                Date date = JodaTimeUtil.strToDate(str1[2], JodaTimeUtil.FORMAT_YMDHMSS);
                PicDetectQueue q = new PicDetectQueue();
                q.setFilePath(destPath);
                String actionTime = JodaTimeUtil.dateToStr(date, JodaTimeUtil.FORMAT_Y_M_D_H_M_SS);
                cameraEqpMap.put("actionTime",actionTime);
                flag = true;
            } else {
                // 如果dest目录中已经存在该图片，说明该图片已经处理过。直接停止
                logger.info(fileName + "该图片已经处理过!跳过该图片");
                deleteFile(filePath);// 删除原始文件
                flag = false;
            }
        }
        return flag;
    }
}
