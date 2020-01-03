package com.avst.meetingcontrol.outside.interfacetoout.cache.param;


import java.util.List;

public class MCCacheParam {

    private int meetingtype;//会议类型，1视频/2音频

    private String mtssid;//会议的ssid

    private List<TdAndUserAndOtherCacheParam> tdList;//会议中所有用户的使用信息

    private String mcType;//会议采用版本，现阶段只有AVST

    private String ywSystemType;//业务系统类型，TRM_AVST AVst版本的笔录系统

    private Integer mtstate;//会议状态 0初始化，1进行中，2已结束，3暂停

    private int recordnum=0;//本次会议开启的录音/像个数
    private int asrnum=0;//本次会议开启的语音识别个数
    private int polygraphnum=0;//本次会议开启的身心监护个数

    private int asrServerModel=1;//1对单单语音识别，2单对多语音识别

    private long mtstarttime;//会议开始时间，标记会议的开始时间，关联asr、ph、录像的开始时间，用于同步

    public int getAsrServerModel() {
        return asrServerModel;
    }

    public void setAsrServerModel(int asrServerModel) {
        this.asrServerModel = asrServerModel;
    }

    public long getMtstarttime() {
        return mtstarttime;
    }

    public void setMtstarttime(long mtstarttime) {
        this.mtstarttime = mtstarttime;
    }

    public int getRecordnum() {
        return recordnum;
    }

    public void setRecordnum(int recordnum) {
        this.recordnum = recordnum;
    }

    public int getAsrnum() {
        return asrnum;
    }

    public void setAsrnum(int asrnum) {
        this.asrnum = asrnum;
    }

    public int getPolygraphnum() {
        return polygraphnum;
    }

    public void setPolygraphnum(int polygraphnum) {
        this.polygraphnum = polygraphnum;
    }

    public Integer getMtstate() {
        return mtstate;
    }

    public void setMtstate(Integer mtstate) {
        this.mtstate = mtstate;
    }

    public String getMcType() {
        return mcType;
    }

    public void setMcType(String mcType) {
        this.mcType = mcType;
    }

    public String getYwSystemType() {
        return ywSystemType;
    }

    public void setYwSystemType(String ywSystemType) {
        this.ywSystemType = ywSystemType;
    }

    public int getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(int meetingtype) {
        this.meetingtype = meetingtype;
    }

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }

    public List<TdAndUserAndOtherCacheParam> getTdList() {
        return tdList;
    }

    public void setTdList(List<TdAndUserAndOtherCacheParam> tdList) {
        this.tdList = tdList;
    }


}
