package com.watch.aiface.dispatch.pojo.po;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.watch.aiface.base.pojo.po.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints = {})
@DynamicUpdate(true)
public class FaceWarnRulePoint extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "int(11) comment '位置x'",name="point_x")
	private Integer pointX;

	@Column(columnDefinition = "int(11) comment '位置y'",name="point_y")
	private Integer pointY;

	@Column(columnDefinition = "int(11) comment '位置height'")
	private Integer pointHeight;

	@Column(columnDefinition = "int(11) comment '位置width'")
	private Integer pointWidth;

	@Column(columnDefinition = "decimal(15)")
	private Long version;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "ruleId", referencedColumnName = "id", columnDefinition = "bigint(20) comment '规则主键'")
	@JsonIgnore
	private FaceWarnRule faceWarnRule;

	public Integer getPointX() {
		return pointX;
	}

	public void setPointX(Integer pointX) {
		this.pointX = pointX;
	}

	public Integer getPointY() {
		return pointY;
	}

	public void setPointY(Integer pointY) {
		this.pointY = pointY;
	}

	public Integer getPointHeight() {
		return pointHeight;
	}

	public void setPointHeight(Integer pointHeight) {
		this.pointHeight = pointHeight;
	}

	public Integer getPointWidth() {
		return pointWidth;
	}

	public void setPointWidth(Integer pointWidth) {
		this.pointWidth = pointWidth;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public FaceWarnRule getFaceWarnRule() {
		return faceWarnRule;
	}

	public void setFaceWarnRule(FaceWarnRule faceWarnRule) {
		this.faceWarnRule = faceWarnRule;
	}

}
