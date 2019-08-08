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
public class Avstmt_tdpolygraph extends Model<Avstmt_tdpolygraph> {

    private static final long serialVersionUID = 1L;

    /**
     * 会议人员通道测谎表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会议人员设备通道ssid
     */
    private String mttduserssid;

    /**
     * 测谎服务ssid
     */
    private String polygraphssid;

    /**
     * 开始读取ph的时间
     */
    private long starttime;

    /**
     * 本次会议的录像开始时间
     */
    private long mtstartrecordtime;


    /**
     * 存储用的唯一标识iid
     */
    private String iid;

    /**
     * 创建时间
     */
    private Date createtime;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPolygraphssid() {
        return polygraphssid;
    }

    public void setPolygraphssid(String polygraphssid) {
        this.polygraphssid = polygraphssid;
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
        return "Avstmt_tdpolygraph{" +
        "id=" + id +
        ", mttduserssid=" + mttduserssid +
        ", createtime=" + createtime +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
