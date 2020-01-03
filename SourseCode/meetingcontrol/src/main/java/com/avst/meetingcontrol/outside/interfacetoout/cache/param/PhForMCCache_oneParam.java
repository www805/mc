package com.avst.meetingcontrol.outside.interfacetoout.cache.param;

import java.util.List;

/**
 *  会议的单个会议用户的身心监护信息
 */
public class PhForMCCache_oneParam<T> {

    private  String phssid;//本次识别的身心监护ssid

    private  List<PhDataParam_toout<T>> phDataList;//身心监护数据集合

    private  String userssid;//会议人员

    private String iid;//本次测谎数据存储对应的唯一值

    private String addfilepath;//添加测谎数据的文件的路径
    private int addcount=0;//已经往文件中写入测谎数据的次数

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getPhssid() {
        return phssid;
    }

    public void setPhssid(String phssid) {
        this.phssid = phssid;
    }

    public List<PhDataParam_toout<T>> getPhDataList() {
        return phDataList;
    }

    public void setPhDataList(List<PhDataParam_toout<T>> phDataList) {
        this.phDataList = phDataList;
    }

    public String getUserssid() {
        return userssid;
    }

    public void setUserssid(String userssid) {
        this.userssid = userssid;
    }

    public String getAddfilepath() {
        return addfilepath;
    }

    public void setAddfilepath(String addfilepath) {
        this.addfilepath = addfilepath;
    }

    public int getAddcount() {
        return addcount;
    }

    public void setAddcount(int addcount) {
        this.addcount = addcount;
    }
}
