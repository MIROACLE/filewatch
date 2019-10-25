package com.watch.aiface.dispatch.pojo.vo;

import com.watch.aiface.base.pojo.vo.AbstractVo;

public class PicDetectQueueVo extends AbstractVo {

    private static final long serialVersionUID = 1L;

    private String actionTime;

    private String equipmentSn;

    private String filePath;

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getEquipmentSn() {
        return equipmentSn;
    }

    public void setEquipmentSn(String equipmentSn) {
        this.equipmentSn = equipmentSn;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
