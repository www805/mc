package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo;

import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndUserParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.param.TDAndUserParam;

import java.util.List;

public class InitMCVO {

    private String mtssid;

    private List<TDAndUserParam> tdlist;

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
