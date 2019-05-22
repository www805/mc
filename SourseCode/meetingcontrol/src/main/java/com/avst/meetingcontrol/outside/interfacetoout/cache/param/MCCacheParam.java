package com.avst.meetingcontrol.outside.interfacetoout.cache.param;


import java.util.List;

public class MCCacheParam {

    private int meetingtype;//会议类型，1视频/2音频

    private String mtssid;//会议的ssid

    private List<TdAndUserAndOtherCacheParam> tdList;//会议中所有用户的使用信息

    private String mcType;//会议采用版本，现阶段只有AVST

    private String ywSystemType;//业务系统类型，TRM_AVST AVst版本的笔录系统

    public String getMcType() {
        return mcType;
    }

    public void setMcType(String mcType) {
        this.mcType = mcType;
    }

    public String getYwSystemType() {
        return ywSystemType;
    }

    public void setYwSystemType(String ywSystemType) {
        this.ywSystemType = ywSystemType;
    }

    public int getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(int meetingtype) {
        this.meetingtype = meetingtype;
    }

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }

    public List<TdAndUserAndOtherCacheParam> getTdList() {
        return tdList;
    }

    public void setTdList(List<TdAndUserAndOtherCacheParam> tdList) {
        this.tdList = tdList;
    }
}
