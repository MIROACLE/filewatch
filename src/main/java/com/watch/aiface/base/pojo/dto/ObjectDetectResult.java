package com.watch.aiface.base.pojo.dto;

import com.watch.aiface.dispatch.pojo.dto.ObjectRect;

public class ObjectDetectResult {

	private String Label;
	private ObjectRect Position;
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public ObjectRect getPosition() {
		return Position;
	}
	public void setPosition(ObjectRect position) {
		Position = position;
	}
	
	

}
