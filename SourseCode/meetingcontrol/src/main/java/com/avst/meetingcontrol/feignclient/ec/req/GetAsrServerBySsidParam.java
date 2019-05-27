package com.avst.meetingcontrol.feignclient.ec.req;

public class GetAsrServerBySsidParam {

    private String asrEquipmentSsid;//语音服务ssid

    public String getAsrEquipmentSsid() {
        return asrEquipmentSsid;
    }

    public void setAsrEquipmentSsid(String asrEquipmentSsid) {
        this.asrEquipmentSsid = asrEquipmentSsid;
    }
}
