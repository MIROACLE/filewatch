package com.watch.aiface.dispatch.pojo.vo;

import com.watch.aiface.base.pojo.vo.BaseVo;

public class FaceWarnRulePointVo extends BaseVo {

    private static final long serialVersionUID = 1L;

    private Integer pointX;

    private Integer pointY;

    private Integer pointHeight;

    private Integer pointWidth;

    private Long ruleId;

    private Long version;

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

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
