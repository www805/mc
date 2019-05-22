package com.avst.meetingcontrol.outside.interfacetoout.req;

public class GetMCAsrTxtBackParam2_userAndSort  {

    private String userssid;//这个用户的ssid

    private Integer asrsort;//这个用户的当前识别的返回的序号

    public String getUserssid() {
        return userssid;
    }

    public void setUserssid(String userssid) {
        this.userssid = userssid;
    }

    public Integer getAsrsort() {
        return asrsort;
    }

    public void setAsrsort(Integer asrsort) {
        this.asrsort = asrsort;
    }
}
