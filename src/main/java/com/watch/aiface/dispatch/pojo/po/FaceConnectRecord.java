package com.watch.aiface.dispatch.pojo.po;

import com.watch.aiface.base.pojo.po.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(uniqueConstraints = {})
@DynamicUpdate(true)
public class FaceConnectRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "varchar(100) comment '摄像头序列号（设备编码）'")
	private String cameraSn;

	@Column(columnDefinition = "bigint(20) comment '事件时间'")
	private Long eventTime;

	@Column(columnDefinition = "int(1) comment '数据状态,上线:1,下线:0'")
	private Integer type;

	public String getCameraSn() {
		return cameraSn;
	}

	public void setCameraSn(String cameraSn) {
		this.cameraSn = cameraSn;
	}

	public Long getEventTime() {
		return eventTime;
	}

	public void setEventTime(Long eventTime) {
		this.eventTime = eventTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
