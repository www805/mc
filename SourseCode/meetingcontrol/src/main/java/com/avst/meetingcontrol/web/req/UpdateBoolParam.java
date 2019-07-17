package com.avst.meetingcontrol.web.req;

public class UpdateBoolParam {

    private String ssid;
    private Integer shieldbool;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Integer getShieldbool() {
        return shieldbool;
    }

    public void setShieldbool(Integer shieldbool) {
        this.shieldbool = shieldbool;
    }
}
