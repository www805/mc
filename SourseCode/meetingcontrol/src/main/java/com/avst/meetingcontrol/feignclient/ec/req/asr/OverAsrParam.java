package com.avst.meetingcontrol.feignclient.ec.req.asr;

/**
 * 关闭asr服务需要带的参数
 */
public class OverAsrParam<T> extends  BaseReqParam {

    private String asrid;//语音识别唯一码

    private int asrServerModel=1;//1对单单语音识别，2单对多语音识别

    public int getAsrServerModel() {
        return asrServerModel;
    }

    public void setAsrServerModel(int asrServerModel) {
        this.asrServerModel = asrServerModel;
    }

    public String getAsrid() {
        return asrid;
    }

    public void setAsrid(String asrid) {
        this.asrid = asrid;
    }
}
