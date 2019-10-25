package com.watch.aiface.dispatch.pojo.vo;

public class FaceConnectRecordVo {

	private static final long serialVersionUID = 1L;

	private String cameraSn;

	private Long eventTime;

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
