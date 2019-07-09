package com.avst.meetingcontrol.web.req;

import com.avst.meetingcontrol.common.util.baseaction.Page;

public class GetBase_mtinfoListParam extends Page {
    private Integer meetingtype;//会议类型1视频/2音频
    private Integer mtstate;//会议状态，0初始化，1进行中，2已结束，3暂停,4异常
    private Integer opened;//是否公开
    private Integer userecord;//是否录制
    private String recordstarttime_startdate;
    private String recordstarttime_enddate;
    private String recordendtime_startdate;
    private String recordendtime_enddate;

    public Integer getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(Integer meetingtype) {
        this.meetingtype = meetingtype;
    }

    public Integer getMtstate() {
        return mtstate;
    }

    public void setMtstate(Integer mtstate) {
        this.mtstate = mtstate;
    }

    public Integer getOpened() {
        return opened;
    }

    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    public Integer getUserecord() {
        return userecord;
    }

    public void setUserecord(Integer userecord) {
        this.userecord = userecord;
    }

    public String getRecordstarttime_startdate() {
        return recordstarttime_startdate;
    }

    public void setRecordstarttime_startdate(String recordstarttime_startdate) {
        this.recordstarttime_startdate = recordstarttime_startdate;
    }

    public String getRecordstarttime_enddate() {
        return recordstarttime_enddate;
    }

    public void setRecordstarttime_enddate(String recordstarttime_enddate) {
        this.recordstarttime_enddate = recordstarttime_enddate;
    }

    public String getRecordendtime_startdate() {
        return recordendtime_startdate;
    }

    public void setRecordendtime_startdate(String recordendtime_startdate) {
        this.recordendtime_startdate = recordendtime_startdate;
    }

    public String getRecordendtime_enddate() {
        return recordendtime_enddate;
    }

    public void setRecordendtime_enddate(String recordendtime_enddate) {
        this.recordendtime_enddate = recordendtime_enddate;
    }
}
