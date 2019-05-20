package com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 38912 kB
 * </p>
 *
 * @author Mht
 * @since 2019-05-13
 */
public class Avstmt_asrtd extends Model<Avstmt_asrtd> {

    private static final long serialVersionUID = 1L;

    /**
     * 语音识别记录表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会议人员设备通道ssid
     */
    private String mttduserssid;

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
     * 录音开始时间,ms的形式
     */
    private long starttime;

    /**
     * 创建时间
     */
    private Date createtime;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

    private String backtxtinterface;

    /**
     * avst语音服务本次识别的唯一识别码
     */
    private String asrid;

    public String getAsrid() {
        return asrid;
    }

    public void setAsrid(String asrid) {
        this.asrid = asrid;
    }

    public String getAsrserverssid() {
        return asrserverssid;
    }

    public void setAsrserverssid(String asrserverssid) {
        this.asrserverssid = asrserverssid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBacktxtinterface() {
        return backtxtinterface;
    }

    public void setBacktxtinterface(String backtxtinterface) {
        this.backtxtinterface = backtxtinterface;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getMttduserssid() {
        return mttduserssid;
    }

    public void setMttduserssid(String mttduserssid) {
        this.mttduserssid = mttduserssid;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Avstmt_asrtd{" +
        "id=" + id +
        ", mttduserssid=" + mttduserssid +
        ", filesavessid=" + filesavessid +
        ", recordtime=" + recordtime +
        ", starttime=" + starttime +
        ", createtime=" + createtime +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
