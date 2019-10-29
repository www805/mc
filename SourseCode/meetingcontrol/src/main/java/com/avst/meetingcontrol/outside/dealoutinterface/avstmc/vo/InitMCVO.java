package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo;

import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndUserParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.param.TDAndUserParam;

import java.util.List;

public class InitMCVO {

    private String mtssid;

    private int asrtype=1;//1对单单语音识别，2单对多语音识别

    private List<TDAndUserParam> tdlist;

    public int getAsrtype() {
        return asrtype;
    }

    public void setAsrtype(int asrtype) {
        this.asrtype = asrtype;
    }

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }

    public List<TDAndUserParam> getTdlist() {
        return tdlist;
    }

    public void setTdlist(List<TDAndUserParam> tdlist) {
        this.tdlist = tdlist;
    }
}
