package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req;

import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndAsrParam;

import java.util.List;

/**
 * 开始会议的参数
 */
public class StartMCParam extends  BaseReqParam {

    private List<TdAndAsrParam> tdAserList;//如果用的是模板的话，这里面参数k可以只设置用户相关和grade，其他的如果为空也可以用模板的参数，

    private String mtssid;//会议ssid

    private int modelbool;//是否需要会议模板，1需要/-1不需要

    private String mtmodelssid;//会议模板ssid

    private int asrtype=1;//1对单单语音识别，2单对多语音识别

    public int getAsrtype() {
        return asrtype;
    }

    public void setAsrtype(int asrtype) {
        this.asrtype = asrtype;
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

    public List<TdAndAsrParam> getTdAserList() {
        return tdAserList;
    }

    public void setTdAserList(List<TdAndAsrParam> tdAserList) {
        this.tdAserList = tdAserList;
    }

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }
}
