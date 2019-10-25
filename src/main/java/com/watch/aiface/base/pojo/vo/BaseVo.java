package com.watch.aiface.base.pojo.vo;

public class BaseVo extends AbstractVo {
    private static final long serialVersionUID = 1L;

    private String createdBy;

    private Long createdDt;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Long createdDt) {
        this.createdDt = createdDt;
    }
}
