package com.avst.meetingcontrol.outside.interfacetoout.vo;

import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrTxtParam_toout;

import java.util.List;

public class GetMCVO {
    private List<AsrTxtParam_toout> list;

    private String iid;//直播数据存储iid

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public List<AsrTxtParam_toout> getList() {
        return list;
    }

    public void setList(List<AsrTxtParam_toout> list) {
        this.list = list;
    }
}
