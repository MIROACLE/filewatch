package com.watch.aiface.base.pojo.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class TreeVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Range(min = 1)
	private Long parentId;

	@NotNull()
	@Range(min = 0)
	private Integer level;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
