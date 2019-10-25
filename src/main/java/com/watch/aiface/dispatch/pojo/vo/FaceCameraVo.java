package com.watch.aiface.dispatch.pojo.vo;


import com.watch.aiface.base.pojo.vo.BaseVo;

public class FaceCameraVo extends BaseVo {
    private static final long serialVersionUID = 1L;

    private Long platOrganizationSid;

    private Long version;

    private String name;

    private String sn;

    private Integer type;

    private String videoUrl;

    private Long workSiteId;

    private Integer faceSync;

    private Integer pointPhotoHeight;

    private Integer pointPhotoWidth;

    private Integer heartBeatRate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Long getWorkSiteId() {
        return workSiteId;
    }

    public void setWorkSiteId(Long workSiteId) {
        this.workSiteId = workSiteId;
    }

    public Integer getFaceSync() {
        return faceSync;
    }

    public void setFaceSync(Integer faceSync) {
        this.faceSync = faceSync;
    }

    public Integer getPointPhotoHeight() {
        return pointPhotoHeight;
    }

    public void setPointPhotoHeight(Integer pointPhotoHeight) {
        this.pointPhotoHeight = pointPhotoHeight;
    }

    public Integer getPointPhotoWidth() {
        return pointPhotoWidth;
    }

    public void setPointPhotoWidth(Integer pointPhotoWidth) {
        this.pointPhotoWidth = pointPhotoWidth;
    }

    public Integer getHeartBeatRate() {
        return heartBeatRate;
    }

    public void setHeartBeatRate(Integer heartBeatRate) {
        this.heartBeatRate = heartBeatRate;
    }
}
