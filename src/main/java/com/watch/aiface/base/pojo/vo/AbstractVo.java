package com.watch.aiface.base.pojo.vo;

import java.io.Serializable;

public class AbstractVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
