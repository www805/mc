package com.avst.meetingcontrol.outside.interfacetoout.cache.param;


import java.util.List;

public class MCCacheParam {

    private int meetingtype;//会议类型，1视频/2音频

    private String mtssid;//会议的ssid

    private List<TdAndUserAndOtherCacheParam> tdList;//会议中所有用户的使用信息

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
