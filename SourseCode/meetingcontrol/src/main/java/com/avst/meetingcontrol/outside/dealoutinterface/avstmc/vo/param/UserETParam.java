package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.param;

public class UserETParam {

    private String userssid;//会议用户ssid

    private String fdssid;//嵌入式设备ssid

    private String livingurl;//设备直播地址

    public String getUserssid() {
        return userssid;
    }

    public void setUserssid(String userssid) {
        this.userssid = userssid;
    }

    public String getFdssid() {
        return fdssid;
    }

    public void setFdssid(String fdssid) {
        this.fdssid = fdssid;
    }

    public String getLivingurl() {
        return livingurl;
    }

    public void setLivingurl(String livingurl) {
        this.livingurl = livingurl;
    }
}
