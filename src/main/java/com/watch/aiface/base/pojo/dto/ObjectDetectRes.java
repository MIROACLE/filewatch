package com.watch.aiface.base.pojo.dto;

import java.util.List;

public class ObjectDetectRes {

	private int State;
	private String Message;
	private int IsDetect;
	private List<ObjectDetectResult> Result;

	public boolean isSuccess() {
		if (this.State == 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public int getIsDetect() {
		return IsDetect;
	}

	public void setIsDetect(int isDetect) {
		IsDetect = isDetect;
	}

	public List<ObjectDetectResult> getResult() {
		return Result;
	}

	public void setResult(List<ObjectDetectResult> result) {
		Result = result;
	}
	
	

}
