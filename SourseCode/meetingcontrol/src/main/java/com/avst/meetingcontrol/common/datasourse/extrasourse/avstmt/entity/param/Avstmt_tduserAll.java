package com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_tduser;

/**
 * 获取这个会议的当前用户的所有相关的信息
 * asr 测谎 嵌入式等
 */
public class Avstmt_tduserAll extends Avstmt_tduser {

    /**
     * 语音识别服务器ssid
     */
    private String asrserverssid;


    /**
     * 录音时长,Ms本次识别的总时长
     */
    private Integer recordtime;

    /**
     * asr开始时间,ms的形式
     */
    private long starttime;

    /**
     * 会议录音的开始时间
     */
    private long mtstartrecordtime;

    /**
     * 通道asr识别的asrid唯一识别码
     */
    private String asrid;

    /**
     * 通道asr识别的ssid
     */
    private String asrtdssid;

    public String getAsrserverssid() {
        return asrserverssid;
    }

    public void setAsrserverssid(String asrserverssid) {
        this.asrserverssid = asrserverssid;
    }


    public Integer getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Integer recordtime) {
        this.recordtime = recordtime;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getMtstartrecordtime() {
        return mtstartrecordtime;
    }

    public void setMtstartrecordtime(long mtstartrecordtime) {
        this.mtstartrecordtime = mtstartrecordtime;
    }

    public String getAsrtdssid() {
        return asrtdssid;
    }

    public void setAsrtdssid(String asrtdssid) {
        this.asrtdssid = asrtdssid;
    }

    public String getAsrid() {
        return asrid;
    }

    public void setAsrid(String asrid) {
        this.asrid = asrid;
    }
}
