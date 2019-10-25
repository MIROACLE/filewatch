package com.watch.aiface.base.pojo.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class PageVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Range(min = 0)
	private Integer number;

	@NotNull
	@Range(min = 1, max = 1000)
	private Integer size;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
