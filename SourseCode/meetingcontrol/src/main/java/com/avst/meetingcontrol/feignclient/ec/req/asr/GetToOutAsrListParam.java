package com.avst.meetingcontrol.feignclient.ec.req.asr;

public class GetToOutAsrListParam extends BaseReqParam {

    private String ssid;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
