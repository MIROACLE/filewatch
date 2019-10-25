package com.watch.aiface.base.pojo.po;

import com.watch.aiface.base.constant.BaseConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(columnDefinition = "varchar(50) comment '创建人'", nullable = false)
    protected String createdBy;

    @CreatedDate
    @Column(columnDefinition = "bigint(20) comment '创建时间'", nullable = false)
    protected Long createdDt;

    @LastModifiedBy
    @Column(columnDefinition = "varchar(50) comment '最后修改人'")
    protected String updatedBy;

    @LastModifiedDate
    @Column(columnDefinition = "bigint(20) comment '最后修改时间'")
    protected Long updatedDt;

    @Column(columnDefinition = "int(1) comment '数据状态,有效:1,无效:0'", nullable = false)
    protected Integer status = 1;

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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Long updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonIgnore
    public boolean isValid() {
        return BaseConstant.ENTITY_STATUS_VALID == getStatus();
    }
}
