package com.avst.meetingcontrol.feignclient.ec.req.fd;

public class FDBaseParam {

    private String flushbonadingetinfossid;//嵌入式设备ssid

    private String fdType;//嵌入式设备选择的类型，avst设备使用FD_AVST

    public String getFlushbonadingetinfossid() {
        return flushbonadingetinfossid;
    }

    public void setFlushbonadingetinfossid(String flushbonadingetinfossid) {
        this.flushbonadingetinfossid = flushbonadingetinfossid;
    }

    public String getFdType() {
        return fdType;
    }

    public void setFdType(String fdType) {
        this.fdType = fdType;
    }
}
