package com.avst.meetingcontrol.outside.interfacetoout.req;

public class OverMCParam_out extends BaseReqParam{
    

    private String mtssid;

    private String mcType;

    public String getMcType() {
        return mcType;
    }

    public void setMcType(String mcType) {
        this.mcType = mcType;
    }

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }
}
