package com.watch.aiface.dispatch.pojo.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.watch.aiface.base.pojo.po.AbstractEntity;

@Entity
@Table(uniqueConstraints = {})
@DynamicUpdate(true)
public class PicDetectQueue extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "varchar(255) comment '操作时间'")
	private String actionTime;

	@Column(columnDefinition = "varchar(20) comment '设备序列码'")
	private String equipmentSn;

	@Column(columnDefinition = "varchar(200) comment '文件地址'")
	private String filePath;

	public String getActionTime() {
		return actionTime;
	}

	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}

	public String getEquipmentSn() {
		return equipmentSn;
	}

	public void setEquipmentSn(String equipmentSn) {
		this.equipmentSn = equipmentSn;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
