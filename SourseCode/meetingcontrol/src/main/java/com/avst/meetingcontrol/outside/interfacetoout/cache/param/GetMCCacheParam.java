package com.avst.meetingcontrol.outside.interfacetoout.cache.param;

import java.util.Date;

public class GetMCCacheParam {
    private String mtssid;

    private String userssid;

    private String txt;

    private String starttime;

    private int asrnum=0;

    private String asrtime;

    private int asrsort;

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }

    public String getUserssid() {
        return userssid;
    }

    public void setUserssid(String userssid) {
        this.userssid = userssid;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public int getAsrnum() {
        return asrnum;
    }

    public void setAsrnum(int asrnum) {
        this.asrnum = asrnum;
    }

    public String getAsrtime() {
        return asrtime;
    }

    public void setAsrtime(String asrtime) {
        this.asrtime = asrtime;
    }

    public int getAsrsort() {
        return asrsort;
    }

    public void setAsrsort(int asrsort) {
        this.asrsort = asrsort;
    }
}
