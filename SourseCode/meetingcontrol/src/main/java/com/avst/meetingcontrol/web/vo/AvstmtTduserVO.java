package com.avst.meetingcontrol.web.vo;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_tduser;

public class AvstmtTduserVO extends Avstmt_tduser {

    //直播地址
    private String livingurl;

    public String getLivingurl() {
        return livingurl;
    }

    public void setLivingurl(String livingurl) {
        this.livingurl = livingurl;
    }
}
