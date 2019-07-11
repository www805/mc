package com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 38912 kB
 * </p>
 *
 * @author Mht
 * @since 2019-05-13
 */
public class Avstmt_tduser extends Model<Avstmt_tduser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备通道ssid
     */
    private String equipmenttdssid;

    /**
     * 会议人员ssid
     */
    private String userssid;

    /**
     * 会议人员别名
     */
    private String username;

    /**
     * 人员类型,1管理员，工作人员/2涉案人员
     */
    private Integer usertype;

   private String mtssid;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    private String string1;

    private String string2;

    private Integer integer1;

    private Integer integer2;

    private String ssid;

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getEquipmenttdssid() {
        return equipmenttdssid;
    }

    public void setEquipmenttdssid(String equipmenttdssid) {
        this.equipmenttdssid = equipmenttdssid;
    }
    public String getUserssid() {
        return userssid;
    }

    public void setUserssid(String userssid) {
        this.userssid = userssid;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getUsertype() {
        return usertype;
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
        return "Avstmt_tduser{" +
        "id=" + id +
        ", equipmenttdssid=" + equipmenttdssid +
        ", userssid=" + userssid +
        ", username=" + username +
        ", usertype=" + usertype +
        ", mtssid=" + mtssid +
        ", createtime=" + createtime +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
