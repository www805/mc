package com.avst.meetingcontrol.outside.interfacetoout.req;

import java.util.List;

public class GetMCAsrTxtBackParam_out extends BaseReqParam {

    private String mtssid;//会议ssid

    private String mcType;//会议采用版本，现阶段只有AVST

private List<GetMCAsrTxtBackParam2_userAndSort> userSortList;

    public List<GetMCAsrTxtBackParam2_userAndSort> getUserSortList() {
        return userSortList;
    }

    public void setUserSortList(List<GetMCAsrTxtBackParam2_userAndSort> userSortList) {
        this.userSortList = userSortList;
    }

    public String getMcType() {
        return mcType;
    }

    public void setMcType(String mcType) {
        this.mcType = mcType;
    }

    public String getMtssid() {
        return mtssid;
    }

    public void setMtssid(String mtssid) {
        this.mtssid = mtssid;
    }

}
