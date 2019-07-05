package com.avst.meetingcontrol.feignclient.ec.vo.ss;

import com.avst.meetingcontrol.feignclient.ec.vo.ss.param.RecordSavepathParam;

import java.util.List;

public class GetSavepathVO {

    private String iid;

    private List<RecordSavepathParam> recordList;//文件集合

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public List<RecordSavepathParam> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordSavepathParam> recordList) {
        this.recordList = recordList;
    }
}
