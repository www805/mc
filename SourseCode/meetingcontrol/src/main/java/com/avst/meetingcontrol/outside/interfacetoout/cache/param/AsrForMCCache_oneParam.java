package com.avst.meetingcontrol.outside.interfacetoout.cache.param;

import java.util.List;

/**
 *  会议的单个会议用户的语音识别信息
 */
public class AsrForMCCache_oneParam {

    private  String asrid;//本次识别的唯一识别码

    private  List<AsrTxtParam_toout> asrTxtList;//本次语音识别的

    private  String userssid;//会议人员

    public String getAsrid() {
        return asrid;
    }

    public void setAsrid(String asrid) {
        this.asrid = asrid;
    }

    public List<AsrTxtParam_toout> getAsrTxtList() {
        return asrTxtList;
    }

    public void setAsrTxtList(List<AsrTxtParam_toout> asrTxtList) {
        this.asrTxtList = asrTxtList;
    }

    public String getUserssid() {
        return userssid;
    }

    public void setUserssid(String userssid) {
        this.userssid = userssid;
    }
}
