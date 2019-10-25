package com.watch.aiface.dispatch.pojo.po;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.watch.aiface.base.pojo.po.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints = {})
@DynamicUpdate(true)
public class FaceWarnRule extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "decimal(15) comment '设备id（关联预警设备维护表的id）'")
	private Long cameraId;

	@Column(columnDefinition = "varchar(32) comment '摄像头设备编码'")
	private String cameraEqpSn;

	@Column(columnDefinition = "varchar(20) comment '预警时间段起'")
	private String warnTimeStart;

	@Column(columnDefinition = "varchar(20) comment '预警时间段止'")
	private String warnTimeEnd;

	@Column(columnDefinition = "int(11) comment '预警频率：同一类型的告警多少时间段内不重复预警(单位秒)'")
	private Integer warnRate;

	@Column(columnDefinition = "int(11) comment '设备预警类型:1-人脸识别设备 2-移动侦测设备4-物品丢失设备8-人员区域入侵设备16-穿越围栏设备32-人员异常徘徊设备64-人员异常奔跑设备128 -安全帽预'")
	private Integer warnType;

	@Column(columnDefinition = "decimal(15) comment '所属工地的id'")
	private Long worksiteId;

	@Column(columnDefinition = "decimal(15)")
	private Long platOrganizationSid;

	@Column(columnDefinition = "decimal(15)")
	private Long version;

	@Column(columnDefinition = "decimal(2,1) comment '算法识别阈值0 ~ 1之间'")
	private Float thresh;

	@OneToMany(mappedBy = "faceWarnRule", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<FaceWarnRulePoint> faceWarnRulePoints;

	public Float getThresh() {
		return thresh;
	}

	public void setThresh(Float thresh) {
		this.thresh = thresh;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getWorksiteId() {
		return worksiteId;
	}

	public void setWorksiteId(Long worksiteId) {
		this.worksiteId = worksiteId;
	}

	public Long getPlatOrganizationSid() {
		return platOrganizationSid;
	}

	public void setPlatOrganizationSid(Long platOrganizationSid) {
		this.platOrganizationSid = platOrganizationSid;
	}

	public Long getCameraId() {
		return cameraId;
	}

	public void setCameraId(Long cameraId) {
		this.cameraId = cameraId;
	}

	public String getCameraEqpSn() {
		return cameraEqpSn;
	}

	public void setCameraEqpSn(String cameraEqpSn) {
		this.cameraEqpSn = cameraEqpSn;
	}

	public String getWarnTimeStart() {
		return warnTimeStart;
	}

	public void setWarnTimeStart(String warnTimeStart) {
		this.warnTimeStart = warnTimeStart;
	}

	public String getWarnTimeEnd() {
		return warnTimeEnd;
	}

	public void setWarnTimeEnd(String warnTimeEnd) {
		this.warnTimeEnd = warnTimeEnd;
	}

	public Integer getWarnRate() {
		return warnRate;
	}

	public void setWarnRate(Integer warnRate) {
		this.warnRate = warnRate;
	}

	public Integer getWarnType() {
		return warnType;
	}

	public void setWarnType(Integer warnType) {
		this.warnType = warnType;
	}

	public List<FaceWarnRulePoint> getFaceWarnRulePoints() {
		return faceWarnRulePoints;
	}

	public void setFaceWarnRulePoints(List<FaceWarnRulePoint> faceWarnRulePoints) {
		this.faceWarnRulePoints = faceWarnRulePoints;
	}

}
