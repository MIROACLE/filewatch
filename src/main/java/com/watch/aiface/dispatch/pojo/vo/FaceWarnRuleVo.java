package com.watch.aiface.dispatch.pojo.vo;

import com.watch.aiface.base.pojo.vo.BaseVo;

public class FaceWarnRuleVo extends BaseVo {

    private static final long serialVersionUID = 1L;

    private Long cameraId;

    private String cameraEqpSn;

    private String warnTimeStart;

    private String warnTimeEnd;

    private Integer warnRate;

    private Integer warnType;

    private Long worksiteId;

    private Long platOrganizationSid;

    private Long version;

    private Long thresh;

    public Long getThresh() {
        return thresh;
    }

    public void setThresh(Long thresh) {
        this.thresh = thresh;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
