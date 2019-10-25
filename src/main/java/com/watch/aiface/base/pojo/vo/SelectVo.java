package com.watch.aiface.base.pojo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Object id;

	private Object text;

	private Object pinyin;

	private Object match;

	private Object isLeaf;

	private List<SelectVo> children = new ArrayList<>();

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Object getText() {
		return text;
	}

	public void setText(Object text) {
		this.text = text;
	}

	public Object getPinyin() {
		return pinyin;
	}

	public void setPinyin(Object pinyin) {
		this.pinyin = pinyin;
	}

	public Object getMatch() {
		return match;
	}

	public void setMatch(Object match) {
		this.match = match;
	}

	public Object getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Object isLeaf) {
		this.isLeaf = isLeaf;
	}

	public List<SelectVo> getChildren() {
		return children;
	}

	public void setChildren(List<SelectVo> children) {
		this.children = children;
	}
}
