package com.avst.meetingcontrol.feignclient.ec.req.fd;

public class GetFlushbonadingBySsidParam {

    private String flushbonadingEquipmentSsid;//嵌入式审讯设备ssid（不是基本设备ssid）

    public String getFlushbonadingEquipmentSsid() {
        return flushbonadingEquipmentSsid;
    }

    public void setFlushbonadingEquipmentSsid(String flushbonadingEquipmentSsid) {
        this.flushbonadingEquipmentSsid = flushbonadingEquipmentSsid;
    }
}
