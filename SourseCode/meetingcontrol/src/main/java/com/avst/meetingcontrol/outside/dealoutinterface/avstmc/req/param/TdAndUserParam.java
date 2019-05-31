package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param;

/**
 * 用户对应会议设备通道
 */
public class TdAndUserParam {

    private String tdssid;//设备通道ssid

    private String username;//会议用户名

    private String userssid;//会议用户的ssid

    private int grade;//1主麦，2副麦，有时需要一些特殊的处理(主麦只有一个)

    private int usepolygraph;//是否使用测谎仪

    private int useasr;//是否使用语言识别，1使用，-1 不使用

    private int userecord;//是否使用录像，1使用，-1 不使用

    private String polygraphssid;//测谎仪ssid

    private String asrssid;//语言识别ssid

    private int useeuipment;//是否使用设备，1使用，-1 不使用

    private String fdeuipmentssid;//嵌入式设备的ssid

    public int getUserecord() {
        return userecord;
    }

    public void setUserecord(int userecord) {
        this.userecord = userecord;
    }

    public int getUseeuipment() {
        return useeuipment;
    }

    public void setUseeuipment(int useeuipment) {
        this.useeuipment = useeuipment;
    }

    public String getFdeuipmentssid() {
        return fdeuipmentssid;
    }

    public void setFdeuipmentssid(String fdeuipmentssid) {
        this.fdeuipmentssid = fdeuipmentssid;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTdssid() {
        return tdssid;
    }

    public void setTdssid(String tdssid) {
        this.tdssid = tdssid;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserssid() {
        return userssid;
    }

    public void setUserssid(String userssid) {
        this.userssid = userssid;
    }

}
