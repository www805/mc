package com.avst.meetingcontrol.common.datasourse.publicsourse.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 88064 kB
 * </p>
 *
 * @author Admin
 * @since 2019-05-30
 */
public class Base_mttodatasave extends Model<Base_mttodatasave> {

    private static final long serialVersionUID = 1L;

    /**
     * 会议数据存储对应表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会议ssid
     */
    private String mtssid;

    /**
     * 文件的唯一标识
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
        return "Base_mttodatasave{" +
                "id=" + id +
                ", mtssid='" + mtssid + '\'' +
                ", iid='" + iid + '\'' +
                ", createtime=" + createtime +
                ", string1='" + string1 + '\'' +
                ", string2='" + string2 + '\'' +
                ", integer1=" + integer1 +
                ", integer2=" + integer2 +
                ", ssid='" + ssid + '\'' +
                '}';
    }
}
