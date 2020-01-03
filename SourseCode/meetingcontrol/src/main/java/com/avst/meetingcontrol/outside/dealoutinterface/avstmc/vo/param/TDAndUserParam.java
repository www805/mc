package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.param;

public class TDAndUserParam {

    private String tdssid;

    private String fdssid;

    private String mttduserssid;

    private String mtuserssid;

    private int usepolygraph;//是否使用身心监护

    private int useasr;//是否使用语言识别，1使用，-1 不使用

    private int userecord;//是否使用设备录像，1使用，-1 不使用

    private String polygraphssid;//身心监护ssid

    private String asrssid;//语言识别ssid

    public int getUserecord() {
        return userecord;
    }

    public void setUserecord(int userecord) {
        this.userecord = userecord;
    }

    public String getFdssid() {
        return fdssid;
    }

    public void setFdssid(String fdssid) {
        this.fdssid = fdssid;
    }

    public int getUsepolygraph() {
        return usepolygraph;
    }

    public void setUsepolygraph(int usepolygraph) {
        this.usepolygraph = usepolygraph;
    }

    public int getUseasr() {
        return useasr;
    }

    public void setUseasr(int useasr) {
        this.useasr = useasr;
    }

    public String getPolygraphssid() {
        return polygraphssid;
    }

    public void setPolygraphssid(String polygraphssid) {
        this.polygraphssid = polygraphssid;
    }

    public String getAsrssid() {
        return asrssid;
    }

    public void setAsrssid(String asrssid) {
        this.asrssid = asrssid;
    }

    public String getMtuserssid() {
        return mtuserssid;
    }

    public void setMtuserssid(String mtuserssid) {
        this.mtuserssid = mtuserssid;
    }

    public String getTdssid() {
        return tdssid;
    }

    public void setTdssid(String tdssid) {
        this.tdssid = tdssid;
    }

    public String getMttduserssid() {
        return mttduserssid;
    }

    public void setMttduserssid(String mttduserssid) {
        this.mttduserssid = mttduserssid;
    }
}
