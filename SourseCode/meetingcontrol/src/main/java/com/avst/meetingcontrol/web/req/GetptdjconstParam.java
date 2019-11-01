package com.avst.meetingcontrol.web.req;

import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutptdjconstParam;

public class GetptdjconstParam extends GetToOutptdjconstParam {
    private String flushbonadingetinfossid;//嵌入式设备ssid

    private boolean mustUpdateBool=false;//是否强制修改数据库中保存的片头

    @Override
    public String getFlushbonadingetinfossid() {
        return flushbonadingetinfossid;
    }

    @Override
    public void setFlushbonadingetinfossid(String flushbonadingetinfossid) {
        this.flushbonadingetinfossid = flushbonadingetinfossid;
    }

    @Override
    public boolean isMustUpdateBool() {
        return mustUpdateBool;
    }

    @Override
    public void setMustUpdateBool(boolean mustUpdateBool) {
        this.mustUpdateBool = mustUpdateBool;
    }
}
