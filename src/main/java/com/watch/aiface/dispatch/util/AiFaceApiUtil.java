package com.watch.aiface.dispatch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.watch.aiface.base.pojo.dto.ObjectDetectRes;
import com.watch.aiface.base.pojo.dto.ObjectDetectResult;
import com.watch.aiface.base.util.HttpUtil;
import com.watch.aiface.base.util.NullUtil;
import com.watch.aiface.dispatch.pojo.dto.ObjectRect;
import com.watch.aiface.dispatch.pojo.po.FaceWarnRule;
import com.watch.aiface.dispatch.pojo.po.FaceWarnRulePoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AiFaceApiUtil {

    private static Logger logger = LoggerFactory.getLogger(AiFaceApiUtil.class);

    private static String OBJECT_SERVER = "http://ip:port";// 围栏算法服务器
    private static String HELME_SERVER = "http://ip:port";// 安全帽算法服务器

    /**
     * 穿越围栏算法分析
     *
     * @param cameraEqpMap
     */
    public static void objectDetect(FaceWarnRule rule, JSONObject cameraEqpMap) {
        String url = OBJECT_SERVER + "/api/object_detect";
        if (NullUtil.isNull(rule.getFaceWarnRulePoints())) {
            return;
        }
        List<ObjectRect> rects = initRects(rule.getFaceWarnRulePoints());
        String rectJson = JSON.toJSONString(rects);
        ConcurrentHashMap<String, Object> params = new ConcurrentHashMap<String, Object>();
        params.put("Thresh", rule.getThresh());
        params.put("DetectRegion", rectJson);
        ConcurrentHashMap<String, Object> files = new ConcurrentHashMap<String, Object>();
        files.put("DetectImage", cameraEqpMap.get("destPath"));
        String res = HttpUtil.post(url, params, files);
        ObjectDetectRes detectRes = JSON.parseObject(res, ObjectDetectRes.class);
        /**
         * 检测物体出现
         */
        if (detectRes.getIsDetect() == 1 && NullUtil.isNotNull(detectRes.getResult())) {
            List<ObjectRect> objectRects = new ArrayList<ObjectRect>();
            for (ObjectDetectResult result : detectRes.getResult()) {
                if (result.getLabel().equals("person")) {
                    objectRects.add(result.getPosition());
                }
            }
            // 人数
            cameraEqpMap.put("personNumber", objectRects.size() + "");
            logger.info("当前摄像头：" + cameraEqpMap.get("cameraEqpSn") + "当前人数：" + objectRects.size());
            // fp
            String fp = drawRects(cameraEqpMap.getString("destPath"), objectRects);
            AlarmUtil.alarm(rule, fp, cameraEqpMap);
        } else {
            cameraEqpMap.put("personNumber", 0 + "");
            logger.info("当前摄像头：" + cameraEqpMap.get("cameraEqpSn") + "当前人数：" + 0);
            AlarmUtil.alarm(rule, cameraEqpMap.getString("destPath"), cameraEqpMap);
        }
    }

    /**
     * 安全帽算法分析
     */
    public static void helmetDetect(FaceWarnRule rule, JSONObject cameraEqpMap) {
        String url = HELME_SERVER + "/api/helmet_detect";
        if (NullUtil.isNull(rule.getFaceWarnRulePoints())) {
            return;
        }
        List<ObjectRect> rects = initRects(rule.getFaceWarnRulePoints());
        String rectJson = JSON.toJSONString(rects);
        ConcurrentHashMap<String, Object> params = new ConcurrentHashMap<String, Object>();
        params.put("Thresh", rule.getThresh());
        params.put("DetectRegion", rectJson);
        ConcurrentHashMap<String, Object> files = new ConcurrentHashMap<String, Object>();
        files.put("DetectImage", cameraEqpMap.get("destPath"));

        String res = HttpUtil.post(url, params, files);
        ObjectDetectRes detectRes = JSON.parseObject(res, ObjectDetectRes.class);
        /**
         * 检测物体出现
         */
        if (detectRes.getIsDetect() == 1 && NullUtil.isNotNull(detectRes.getResult())) {
            List<ObjectRect> norects = new ArrayList<ObjectRect>();
            List<ObjectRect> hrects = new ArrayList<ObjectRect>();
            for (ObjectDetectResult result : detectRes.getResult()) {
                if (result.getLabel().equals("nohelmet")) {
                    norects.add(result.getPosition());
                } else if (result.getLabel().equals("helmet")) {
                    hrects.add(result.getPosition());
                }
            }
            // 人数
            cameraEqpMap.put("personNumber", norects.size() + "");
            logger.info("当前摄像头：" + cameraEqpMap.get("cameraEqpSn") + "当前人数：" + norects.size());
            // 画图
            String fp = drawRects(cameraEqpMap.getString("destPath"), norects);
            AlarmUtil.alarm(rule, fp, cameraEqpMap);
        } else {
            cameraEqpMap.put("personNumber", 0 + "");
            logger.info("当前摄像头：" + cameraEqpMap.get("cameraEqpSn") + "当前人数：" + 0);
            AlarmUtil.alarm(rule, cameraEqpMap.getString("destPath"), cameraEqpMap);
        }
    }

    private static List<ObjectRect> initRects(List<FaceWarnRulePoint> points) {
        List<ObjectRect> rects = new ArrayList<ObjectRect>();
        for (FaceWarnRulePoint p : points) {
            ObjectRect rect = new ObjectRect();
            rect.setX(p.getPointX());
            rect.setY(p.getPointY());
            rect.setWidth(p.getPointWidth());
            rect.setHeight(p.getPointHeight());
            rects.add(rect);
        }
        return rects;
    }

    private static String drawRects(String filePath, List<ObjectRect> rects) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            Graphics g = image.getGraphics();
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.RED);// 画笔颜色
            g2.setStroke(new BasicStroke(3.0f));// 画笔直径
            for (ObjectRect rect : rects) {
                g2.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
            FileOutputStream out = new FileOutputStream(filePath);
            ImageIO.write(image, "jpeg", out);
        } catch (IOException e) {
            logger.info("打开文件出现错误：" + e);
        }
        return filePath;
    }
}
