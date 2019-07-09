package com.avst.meetingcontrol.web.req;

import com.avst.meetingcontrol.common.util.baseaction.Page;

public class GetAvstmt_modeltdListParam extends Page {
    private Integer usepolygraph;
    private Integer useasr;
    private Integer grade;
    private String startdate;
    private String enddate;

    private String mtmodelssid;//会议模板ssid

    public String getMtmodelssid() {
        return mtmodelssid;
    }

    public void setMtmodelssid(String mtmodelssid) {
        this.mtmodelssid = mtmodelssid;
    }

    public Integer getUsepolygraph() {
        return usepolygraph;
    }

    public void setUsepolygraph(Integer usepolygraph) {
        this.usepolygraph = usepolygraph;
    }

    public Integer getUseasr() {
        return useasr;
    }

    public void setUseasr(Integer useasr) {
        this.useasr = useasr;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

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
}
