package com.watch.aiface.dispatch.constant;

public enum DetectConstant {

	OBJECT_DETECT(16, "穿越围栏"), HELMET_DETECT(128, "安全帽");

	private int code;
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private DetectConstant(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
