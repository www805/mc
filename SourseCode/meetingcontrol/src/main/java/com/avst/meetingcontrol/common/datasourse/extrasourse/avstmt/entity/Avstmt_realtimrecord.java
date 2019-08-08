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
 * @since 2019-05-17
 */
public class Avstmt_realtimrecord extends Model<Avstmt_realtimrecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 会议实时记录表(一段一段的记录)
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会议ssid
     */
    private String mtssid;

    /**
     * 会议用户ssid
     */
    private String mtuserssid;

    /**
     * 实时录音/像地址
     */
    private String filesavessid;

    /**
     * 录音时长
     */
    private Long recordtime;

    /**
     * 语音识别开始时间
     */
    private Long starttime;

    /**
     * 排序
     */
    private Integer ordernum;

    /**
     * 翻译文字(一句话的翻译不可能超过255)
     */
    private String translatext;

    /**
     * 创建时间
     */
    private Date createtime;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }
    public String getFilesavessid() {
        return filesavessid;
    }

    public void setFilesavessid(String filesavessid) {
        this.filesavessid = filesavessid;
    }
    public Long getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Long recordtime) {
        this.recordtime = recordtime;
    }
    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }
    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }
    public String getTranslatext() {
        return translatext;
    }

    public void setTranslatext(String translatext) {
        this.translatext = translatext;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMtuserssid() {
        return mtuserssid;
    }

    public void setMtuserssid(String mtuserssid) {
        this.mtuserssid = mtuserssid;
    }

    @Override
    public String toString() {
        return "Avstmt_realtimrecord{" +
        "id=" + id +
        ", mtssid=" + mtssid +
        ", mtuserssid=" + mtuserssid +
        ", filesavessid=" + filesavessid +
        ", recordtime=" + recordtime +
        ", starttime=" + starttime +
        ", ordernum=" + ordernum +
        ", translatext=" + translatext +
        ", createtime=" + createtime +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
