package com.avst.meetingcontrol.outside.interfacetoout.req;

public class GetMc_modelParam_out {
    private String mcType;//会议采用版本，现阶段只有AVST

    public String getMcType() {
        return mcType;
    }

    public void setMcType(String mcType) {
        this.mcType = mcType;
    }
}