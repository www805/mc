package com.avst.meetingcontrol.feignclient.ec.req;

public class GetToOutBaseEcParam {

    private String baseType;
    private String etip;

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public String getEtip() {
        return etip;
    }

    public void setEtip(String etip) {
        this.etip = etip;
    }
}
