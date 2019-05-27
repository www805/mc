package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param;

/**
 * 用户对应会议设备通道
 */
public class TdAndAsrParam {

    private String mttduserssid;//会议用户设备通道ssid

    private String tdssid;//通道ssid

    private String fdssid;//设备ssid

    private String fdtype;////用的是哪一家的设备，avst公司自制的嵌入式设备fd_avst

    private int usepolygraph;//是否使用测谎仪

    private int useasr;//是否使用语言识别，1使用，-1 不使用

    private String polygraphssid;//测谎仪ssid

    private String asrssid;//语言识别ssid

    private String asrtype;//采用的是哪一种语音识别服务，avst公司自制的语音服务

    public String getFdssid() {
        return fdssid;
    }

    public void setFdssid(String fdssid) {
        this.fdssid = fdssid;
    }

    public String getFdtype() {
        return fdtype;
    }

    public void setFdtype(String fdtype) {
        this.fdtype = fdtype;
    }

    public String getTdssid() {
        return tdssid;
    }

    public void setTdssid(String tdssid) {
        this.tdssid = tdssid;
    }

    public String getAsrtype() {
        return asrtype;
    }

    public void setAsrtype(String asrtype) {
        this.asrtype = asrtype;
    }

    public String getMttduserssid() {
        return mttduserssid;
    }

    public void setMttduserssid(String mttduserssid) {
        this.mttduserssid = mttduserssid;
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
}
