package com.watch.aiface.task;

import java.util.Date;
import java.util.List;

import com.watch.aiface.dispatch.pojo.po.FaceCamera;
import com.watch.aiface.dispatch.pojo.po.FaceConnectRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import com.watch.aiface.dispatch.repository.FaceCameraRepository;
import com.watch.aiface.dispatch.repository.FaceConnectRecordRepository;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Sorts;
import com.github.wenhao.jpa.Sorts.Builder;
import com.github.wenhao.jpa.Specifications;

/**
 * 摄像头心跳检测，记录上线、离线记录
 */
@Configuration
@EnableScheduling
public class HeartBeatDetectTask {

	private static Logger logger = LoggerFactory.getLogger(HeartBeatDetectTask.class);
	@Autowired
	private FaceCameraRepository faceCameraRepository;
	@Autowired
	private FaceConnectRecordRepository faceConnectRecordRepository;

	@Scheduled(cron = "0/2 * * * * *")
	public void heartBeatDetect() {
		// 取出数据库的所有设备集合
		PredicateBuilder<FaceCamera> pb = Specifications.<FaceCamera>and();
		pb.eq("status", "1");
		Builder sb = Sorts.builder();
		sb.asc("id");
		List<FaceCamera> faceCameraList = faceCameraRepository.findAll(pb.build(), sb.build());

		if (!CollectionUtils.isEmpty(faceCameraList)) {
			for (FaceCamera faceCamera : faceCameraList) {
				String cameraSn = faceCamera.getSn();
				Integer heartBeatRate = faceCamera.getHeartBeatRate();
				String lastConnectTimeStr = System.getProperty(cameraSn);
				String cameraStatus = System.getProperty(cameraSn + "_status");
				long currentTime = new Date().getTime();
				// lastConnectTimeStr为null说明项目才启动，默认设备为离线状态
				if (lastConnectTimeStr==null) {
					System.setProperty(cameraSn + "_status", "0");
					continue;
				}
				Long lastConnectTime = Long.parseLong(lastConnectTimeStr);
				// 计算时间间隔，如果大于阈值
				if ((currentTime - lastConnectTime) > heartBeatRate * 1000) {
					// 如果上一次的状态是在线，修改为离线状态，插入离线记录
					if ("1".equals(cameraStatus)) {
						FaceConnectRecord record = new FaceConnectRecord();
						record.setCameraSn(cameraSn);
						record.setEventTime(currentTime);
						record.setType(0);
						record.setCreatedBy("");
						faceConnectRecordRepository.save(record);
						System.setProperty(cameraSn + "_status", "0");
					}
				} else {
					// 如果上一次的状态是离线，修改为上线状态，插入上线记录
					if ("0".equals(cameraStatus)) {
						FaceConnectRecord record = new FaceConnectRecord();
						record.setCameraSn(cameraSn);
						record.setEventTime(currentTime);
						record.setType(1);
						record.setCreatedBy("");
						faceConnectRecordRepository.save(record);
						System.setProperty(cameraSn + "_status", "1");
					}
				}
			}
		} else {
			logger.info("未查询到可用的设备！");
		}
	}
}
