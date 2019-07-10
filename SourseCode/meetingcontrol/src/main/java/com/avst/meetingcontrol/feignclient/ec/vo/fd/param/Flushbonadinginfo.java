package com.avst.meetingcontrol.feignclient.ec.vo.fd.param;


/**
 * 详细的审讯设备信息
 */
public class Flushbonadinginfo  {

    /**
     * 设备编号
     */
    private String etnum;

    /**
     * 设备IP
     */
    private String etip;

    /**
     * 设备类型ssid
     */
    private String etypessid;

    ///////////////////////////////////////////////////////////////////
    /**
     * 设备类型
     */
    private String ettypenum;

    /**
     * 审讯主机
     */
    private Integer id;

    /**
     * 设备ssid
     */
    private String equipmentssid;

    /**
     * 直播地址
     */
    private String livingurl;

    /**
     * 开放接口的端口
     */
    private Integer port;

    private String user;

    private String passwd;

    /**
     * ftp上传存储备设路径,只是一级路径，其实就是集中管理里面的本机设备ID，就是用来ftp上传时加以及路径，方便区分
     */
    private String uploadbasepath;

    /**
     * 中文解释
     */
    private String explain;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;


    public String getEtnum() {
        return etnum;
    }

    public void setEtnum(String etnum) {
        this.etnum = etnum;
    }

    public String getEtip() {
        return etip;
    }

    public void setEtip(String etip) {
        this.etip = etip;
    }

    public String getEtypessid() {
        return etypessid;
    }

    public void setEtypessid(String etypessid) {
        this.etypessid = etypessid;
    }

    public String getEttypenum() {
        return ettypenum;
    }

    public void setEttypenum(String ettypenum) {
        this.ettypenum = ettypenum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquipmentssid() {
        return equipmentssid;
    }

    public void setEquipmentssid(String equipmentssid) {
        this.equipmentssid = equipmentssid;
    }

    public String getLivingurl() {
        return livingurl;
    }

    public void setLivingurl(String livingurl) {
        this.livingurl = livingurl;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUploadbasepath() {
        return uploadbasepath;
    }

    public void setUploadbasepath(String uploadbasepath) {
        this.uploadbasepath = uploadbasepath;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public Integer getInteger1() {
        return integer1;
    }

    public void setInteger1(Integer integer1) {
        this.integer1 = integer1;
    }

    public Integer getInteger2() {
        return integer2;
    }

    public void setInteger2(Integer integer2) {
        this.integer2 = integer2;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
