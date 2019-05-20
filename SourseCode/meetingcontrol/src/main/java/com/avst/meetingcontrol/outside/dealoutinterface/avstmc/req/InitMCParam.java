package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req;

import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndUserParam;

import java.util.List;

/**
* 初始化会议参数
 */
public class InitMCParam extends  BaseReqParam {

    private int meetingtype;//会议类型，1视频/2音频

    private int modelbool;//是否需要会议模板，1需要/-1不需要

    private String mtmodelssid;//会议模板ssid

    private List<TdAndUserParam> tdAndUserList;//会议用户关联通道

    public List<TdAndUserParam> getTdAndUserList() {
        return tdAndUserList;
    }

    public void setTdAndUserList(List<TdAndUserParam> tdAndUserList) {
        this.tdAndUserList = tdAndUserList;
    }

    public int getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(int meetingtype) {
        this.meetingtype = meetingtype;
    }

    public int getModelbool() {
        return modelbool;
    }

    public void setModelbool(int modelbool) {
        this.modelbool = modelbool;
    }

    public String getMtmodelssid() {
        return mtmodelssid;
    }

    public void setMtmodelssid(String mtmodelssid) {
        this.mtmodelssid = mtmodelssid;
    }
}
