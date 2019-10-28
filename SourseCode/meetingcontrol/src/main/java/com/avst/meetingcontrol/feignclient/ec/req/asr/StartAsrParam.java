package com.avst.meetingcontrol.feignclient.ec.req.asr;

/**
 * 开启asr服务需要带的参数
 */
public class StartAsrParam<T> extends  BaseReqParam {

    //如果里面有,就需要切割，因为这个可能是多个音频一块开启
    private String tdssid;//通道ssid，也可以是这次语音识别提供音频一方的音频唯一识别码，通过它找到音频

    private int asrchannel=1;//语音识别路数，当tdssid是有,组合的通道集合这个参数才有用

    public int getAsrchannel() {
        return asrchannel;
    }

    public void setAsrchannel(int asrchannel) {
        this.asrchannel = asrchannel;
    }

    public String getTdssid() {
        return tdssid;
    }

    public void setTdssid(String tdssid) {
        this.tdssid = tdssid;
    }

}
