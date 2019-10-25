package com.watch.aiface.dispatch.util;

import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import com.watch.aiface.base.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.watch.aiface.base.util.DegistUtil;
import com.watch.aiface.base.util.HttpUtil;
import com.watch.aiface.dispatch.pojo.po.FaceWarnRule;

public class AlarmUtil {

	private static Logger logger = LoggerFactory.getLogger(AlarmUtil.class);

	// 告警服务器(生产)
	private static final String WARN_SERVER = "http://127.0.0.1:10001/wzsmartsite/face/warn/uploadWarnRecord";


	public static void alarm(FaceWarnRule rule, String fp, JSONObject cameraEqpMap) {
		ConcurrentHashMap<String, Object> params = new ConcurrentHashMap<String, Object>();
		params.put("securityCode", DegistUtil.securityCode());
		params.put("type", rule.getWarnType());
		params.put("equipmentId", rule.getCameraId());
		params.put("actionTime", cameraEqpMap.get("actionTime"));
		params.put("actionSn", UUIDUtil.getUUID());
		params.put("personNumber", cameraEqpMap.get("personNumber"));
		ConcurrentHashMap<String, Object> files = new ConcurrentHashMap<String, Object>();
		if (fp != null) {
			files.put("alarmPhoto", fp);
		}
		logger.info(JSON.toJSONString(params));
		String res = HttpUtil.post(WARN_SERVER, params, files);
//		不删除dest目录下的文件，使用定时任务，每天清理一次
//		if (fp != null) {
//			File f = new File(fp);
//			if (f.exists()) {
//				f.delete();
//			}
//		}
		logger.info("告警上传返回参数：{}", JSON.toJSONString(res));
	}

}
