package com.avst.meetingcontrol.outside.interfacetoout.vo;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_modeltd;

import java.util.List;

public class GetTdByModelSsidVO {
    private List<Avstmt_modeltd> modeltds;

    public List<Avstmt_modeltd> getModeltds() {
        return modeltds;
    }

    public void setModeltds(List<Avstmt_modeltd> modeltds) {
        this.modeltds = modeltds;
    }
}
