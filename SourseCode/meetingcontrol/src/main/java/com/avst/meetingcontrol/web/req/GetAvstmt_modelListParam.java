package com.avst.meetingcontrol.web.req;

import com.avst.meetingcontrol.common.util.baseaction.Page;

public class GetAvstmt_modelListParam extends Page {
    private Integer meetingtype;
    private Integer opened;
    private Integer userecord;
    private String explain;

    private String startdate;//开始日期
    private String enddate;//结束日期

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Integer getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(Integer meetingtype) {
        this.meetingtype = meetingtype;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
