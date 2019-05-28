package com.avst.meetingcontrol.outside.interfacetoout.vo;

import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrTxtParam_toout;

public class SetMCAsrTxtBackVO extends AsrTxtParam_toout {
    private String mtssid;//会议ssid

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }
}
