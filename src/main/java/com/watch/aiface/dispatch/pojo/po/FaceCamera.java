package com.watch.aiface.dispatch.pojo.po;

import com.watch.aiface.base.pojo.po.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(uniqueConstraints = {})
@DynamicUpdate(true)
public class FaceCamera extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(columnDefinition = "decimal(15) comment 'plat_organization_sid'")
    private Long platOrganizationSid;

    @Column(columnDefinition = "decimal(15) comment 'version'")
    private Long version;

    @Column(columnDefinition = "varchar(100) comment '摄像头安装位置'")
    private String name;

    @Column(columnDefinition = "varchar(100) comment '摄像头序列号（设备编码）'")
    private String sn;

    @Column(columnDefinition = "int(11) comment '设备类型:1-人脸识别设备 2-移动侦测设备4-物品丢失设备8-人员区域入侵设备16-穿越围栏设备32-人员异常徘徊设备64-人员异常奔跑设备128 -安全帽预警'")
    private Integer type;

    @Column(columnDefinition = "varchar(100) comment '视频流地址'")
    private String videoUrl;

    @Column(columnDefinition = "decimal(15) comment '所属工地'")
    private Long workSiteId;

    @Column(columnDefinition = "tinyint(1) comment '所属工地'", nullable = false)
    private Integer faceSync;

    @Column(columnDefinition = "int(11) comment '所属工地'")
    private Integer pointPhotoHeight;

    @Column(columnDefinition = "int(11) comment '所属工地'")
    private Integer pointPhotoWidth;

    @Column(columnDefinition = "int(11) comment '离线周期'")
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
