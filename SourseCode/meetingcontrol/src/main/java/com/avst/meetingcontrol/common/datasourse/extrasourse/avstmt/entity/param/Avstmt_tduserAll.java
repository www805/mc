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
     * 实时录音/像地址,本次识别的全程录音/像
     */
    private String filesavessid;

    /**
     * 录音时长,Ms本次识别的总时长
     */
    private Integer recordtime;

    /**
     * asr开始时间,ms的形式
     */
    private long starttime;

    /**
     * 语音识别对应的音频的录音开始时间
     */
    private long startrecordtime;

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

    public String getFilesavessid() {
        return filesavessid;
    }

    public void setFilesavessid(String filesavessid) {
        this.filesavessid = filesavessid;
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

    public long getStartrecordtime() {
        return startrecordtime;
    }

    public void setStartrecordtime(long startrecordtime) {
        this.startrecordtime = startrecordtime;
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
