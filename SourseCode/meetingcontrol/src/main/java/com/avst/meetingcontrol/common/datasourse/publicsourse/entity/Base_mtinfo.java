package com.avst.meetingcontrol.common.datasourse.publicsourse.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * InnoDB free: 38912 kB
 * </p>
 *
 * @author Mht
 * @since 2019-05-13
 */
public class Base_mtinfo extends Model<Base_mtinfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 1视频/2音频
     */
    private Integer meetingtype;

    /**
     * 直播地址
     */
    private String livingurl;

    /**
     * 会议音/视频文件ssid
     */
    private String filesavessid;

    /**
     * 是否公开,1开放/-1不开放
     */
    private Integer opened;

    /**
     * 是否录制,1录制/-1不录制
     */
    private Integer userecord;

    /**
     * 录制开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordstarttime;

    /**
     * 录制结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordendtime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    /**
     * 会议状态，0初始化，1进行中，2已结束，3暂停,4异常
     */
    private Integer mtstate=0;

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
    public Integer getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(Integer meetingtype) {
        this.meetingtype = meetingtype;
    }

    public String getFilesavessid() {
        return filesavessid;
    }

    public void setFilesavessid(String filesavessid) {
        this.filesavessid = filesavessid;
    }

    public String getLivingurl() {
        return livingurl;
    }

    public void setLivingurl(String livingurl) {
        this.livingurl = livingurl;
    }

    public Integer getOpened() {
        return opened;
    }

    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    public Integer getUserecord() {
        return userecord;
    }

    public void setUserecord(Integer userecord) {
        this.userecord = userecord;
    }
    public Date getRecordstarttime() {
        return recordstarttime;
    }

    public void setRecordstarttime(Date recordstarttime) {
        this.recordstarttime = recordstarttime;
    }
    public Date getRecordendtime() {
        return recordendtime;
    }

    public void setRecordendtime(Date recordendtime) {
        this.recordendtime = recordendtime;
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

    public Integer getMtstate() {
        return mtstate;
    }

    public void setMtstate(Integer mtstate) {
        this.mtstate = mtstate;
    }

    @Override
    public String toString() {
        return "Base_mtinfo{" +
        "id=" + id +
        ", meetingtype=" + meetingtype +
        ", livingurl=" + livingurl +
        ", filesavessid=" + filesavessid +
        ", opened=" + opened +
        ", userecord=" + userecord +
        ", recordstarttime=" + recordstarttime +
        ", recordendtime=" + recordendtime +
        ", createtime=" + createtime +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
         ",mtstate="+mtstate +
        "}";
    }
}
