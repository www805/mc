package com.avst.meetingcontrol.web.vo;

public class GetHomeVO {
    private  Integer fdcount;
    private  Integer phcount;
    private  Integer asrcount;
    private  Integer ttscount;


    private Integer modelcount;//会议模板个数
    private Integer modeltdcount;//模板通道个数

    private Integer mtinfo_count;//会议总个数
    private Integer mtinfo_initcount;//会议初始化个数
    private Integer mtinfo_ingcount;//会议进行中个数
    private Integer mtinfo_endtcount;//会议已结束个数
    private Integer mtinfo_pausecount;//会议暂停个数
    private Integer mtinfo_abnormalcount;//会议异常个数

    public Integer getMtinfo_count() {
        return mtinfo_count;
    }

    public void setMtinfo_count(Integer mtinfo_count) {
        this.mtinfo_count = mtinfo_count;
    }

    public Integer getFdcount() {
        return fdcount;
    }

    public void setFdcount(Integer fdcount) {
        this.fdcount = fdcount;
    }

    public Integer getPhcount() {
        return phcount;
    }

    public void setPhcount(Integer phcount) {
        this.phcount = phcount;
    }

    public Integer getAsrcount() {
        return asrcount;
    }

    public void setAsrcount(Integer asrcount) {
        this.asrcount = asrcount;
    }

    public Integer getTtscount() {
        return ttscount;
    }

    public void setTtscount(Integer ttscount) {
        this.ttscount = ttscount;
    }

    public Integer getModelcount() {
        return modelcount;
    }

    public void setModelcount(Integer modelcount) {
        this.modelcount = modelcount;
    }

    public Integer getModeltdcount() {
        return modeltdcount;
    }

    public void setModeltdcount(Integer modeltdcount) {
        this.modeltdcount = modeltdcount;
    }

    public Integer getMtinfo_initcount() {
        return mtinfo_initcount;
    }

    public void setMtinfo_initcount(Integer mtinfo_initcount) {
        this.mtinfo_initcount = mtinfo_initcount;
    }

    public Integer getMtinfo_ingcount() {
        return mtinfo_ingcount;
    }

    public void setMtinfo_ingcount(Integer mtinfo_ingcount) {
        this.mtinfo_ingcount = mtinfo_ingcount;
    }

    public Integer getMtinfo_endtcount() {
        return mtinfo_endtcount;
    }

    public void setMtinfo_endtcount(Integer mtinfo_endtcount) {
        this.mtinfo_endtcount = mtinfo_endtcount;
    }

    public Integer getMtinfo_pausecount() {
        return mtinfo_pausecount;
    }

    public void setMtinfo_pausecount(Integer mtinfo_pausecount) {
        this.mtinfo_pausecount = mtinfo_pausecount;
    }

    public Integer getMtinfo_abnormalcount() {
        return mtinfo_abnormalcount;
    }

    public void setMtinfo_abnormalcount(Integer mtinfo_abnormalcount) {
        this.mtinfo_abnormalcount = mtinfo_abnormalcount;
    }
}
