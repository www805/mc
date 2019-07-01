package com.avst.meetingcontrol.outside.interfacetoout.cache.param;

import java.util.List;

public class PhForMCCacheParam<T> {

    private String mtssid;

    private int mttype;//音频会议/视频会议

    private String mctype;//采用的是谁的会议处理模块，AVST

    private List<PhForMCCache_oneParam<T>> phForMCCache_oneParamList;//所有用户的测谎数据

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

    public List<PhForMCCache_oneParam<T>> getPhForMCCache_oneParamList() {
        return phForMCCache_oneParamList;
    }

    public void setPhForMCCache_oneParamList(List<PhForMCCache_oneParam<T>> phForMCCache_oneParamList) {
        this.phForMCCache_oneParamList = phForMCCache_oneParamList;
    }
}
