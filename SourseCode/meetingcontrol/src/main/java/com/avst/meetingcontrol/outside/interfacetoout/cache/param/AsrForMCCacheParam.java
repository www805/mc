package com.avst.meetingcontrol.outside.interfacetoout.cache.param;

import java.util.List;

/**
 *  会议中所有用户的语音识别信息
 */
public class AsrForMCCacheParam {

    private String mtssid;

    private int mttype;//音频会议/视频会议

    private String mctype;//采用的是谁的会议处理模块，AVST

    private List<AsrForMCCache_oneParam> asrForMCCache_oneParamList;

    public List<AsrForMCCache_oneParam> getAsrForMCCache_oneParamList() {
        return asrForMCCache_oneParamList;
    }

    public void setAsrForMCCache_oneParamList(List<AsrForMCCache_oneParam> asrForMCCache_oneParamList) {
        this.asrForMCCache_oneParamList = asrForMCCache_oneParamList;
    }

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }

    public int getMttype() {
        return mttype;
    }

    public void setMttype(int mttype) {
        this.mttype = mttype;
    }

    public String getMctype() {
        return mctype;
    }

    public void setMctype(String mctype) {
        this.mctype = mctype;
    }
}
