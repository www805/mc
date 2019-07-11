package com.avst.meetingcontrol.web.req;

import com.avst.meetingcontrol.common.util.baseaction.Page;

public class GetAvstmt_tduserListParam extends Page {

    //会议的ssid
    private String mtssid;


    private String username;
    private String usertype;
    private String createtime_startdate;
    private String createtime_enddate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getCreatetime_startdate() {
        return createtime_startdate;
    }

    public void setCreatetime_startdate(String createtime_startdate) {
        this.createtime_startdate = createtime_startdate;
    }

    public String getCreatetime_enddate() {
        return createtime_enddate;
    }

    public void setCreatetime_enddate(String createtime_enddate) {
        this.createtime_enddate = createtime_enddate;
    }

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }
}
