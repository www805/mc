package com.avst.meetingcontrol.common.datasourse.publicsourse.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Admin
 * @since 2019-12-20
 */
public class Base_modeltype extends Model<Base_modeltype> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模板类型名称
     */
    private String modeltypename;

    /**
     * 模板类型编号,0/1/2/3/4(一般0是默认)
     */
    private Integer modeltypenum;

    /**
     * 分支类型
     */
    private String branchtype;

    /**
     * 分支名称
     */
    private String branchname;

    /**
     * 默认模板类型,1默认，0一般
     */
    private Integer defaultmodeltype;

    /**
     * 模板类型状态,1正常、0暂停、-1删除
     */
    private Integer modeltypestate;

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
    public String getModeltypename() {
        return modeltypename;
    }

    public void setModeltypename(String modeltypename) {
        this.modeltypename = modeltypename;
    }
    public Integer getModeltypenum() {
        return modeltypenum;
    }

    public void setModeltypenum(Integer modeltypenum) {
        this.modeltypenum = modeltypenum;
    }
    public String getBranchtype() {
        return branchtype;
    }

    public void setBranchtype(String branchtype) {
        this.branchtype = branchtype;
    }
    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }
    public Integer getDefaultmodeltype() {
        return defaultmodeltype;
    }

    public void setDefaultmodeltype(Integer defaultmodeltype) {
        this.defaultmodeltype = defaultmodeltype;
    }
    public Integer getModeltypestate() {
        return modeltypestate;
    }

    public void setModeltypestate(Integer modeltypestate) {
        this.modeltypestate = modeltypestate;
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
        return "Base_modeltype{" +
        "id=" + id +
        ", modeltypename=" + modeltypename +
        ", modeltypenum=" + modeltypenum +
        ", branchtype=" + branchtype +
        ", branchname=" + branchname +
        ", defaultmodeltype=" + defaultmodeltype +
        ", modeltypestate=" + modeltypestate +
        ", string1=" + string1 +
        ", string2=" + string2 +
        ", integer1=" + integer1 +
        ", integer2=" + integer2 +
        ", ssid=" + ssid +
        "}";
    }
}
