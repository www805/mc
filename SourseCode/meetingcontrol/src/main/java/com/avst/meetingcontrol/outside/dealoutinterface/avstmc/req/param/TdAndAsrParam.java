package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param;

/**
 * 用户对应会议设备通道
 */
public class TdAndAsrParam {

    private String mttduserssid;//会议用户设备通道ssid

    private String tdssid;//通道ssid

    private int tdgreade;//通道的编号

    private String fdssid;//设备ssid

    private int userecord;//是否需要录像，1使用，-1 不使用

    private String fdtype;////用的是哪一家的设备，avst公司自制的嵌入式设备fd_avst

    private int usepolygraph;//是否使用身心监护

    private String polygraphtype;//采用的是哪一种身心监护服务

    private int useasr;//是否使用语音识别，1使用，-1 不使用

    private String polygraphssid;//身心监护ssid

    private String asrssid;//语言识别ssid

    private String asrtype;//采用的是哪一种语音识别服务，avst公司自制的语音服务

    public int getTdgreade() {
        return tdgreade;
    }

    public void setTdgreade(int tdgreade) {
        this.tdgreade = tdgreade;
    }

    public String getPolygraphtype() {
        return polygraphtype;
    }

    public void setPolygraphtype(String polygraphtype) {
        this.polygraphtype = polygraphtype;
    }

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
