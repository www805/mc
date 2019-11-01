package com.avst.meetingcontrol.feignclient.ec.req.fd;

public class GetToOutptdjconstParam extends FDBaseParam {

    private boolean mustUpdateBool=false;//是否强制修改数据库中保存的片头

    public boolean isMustUpdateBool() {
        return mustUpdateBool;
    }

    public void setMustUpdateBool(boolean mustUpdateBool) {
        this.mustUpdateBool = mustUpdateBool;
    }
}
