package com.avst.meetingcontrol.feignclient.ec.req;

import com.avst.meetingcontrol.feignclient.ec.req.fd.FDBaseParam;

import java.util.List;

public class GetToOutFlushbonadingEttdByListParam extends FDBaseParam {

    private List<Avstmt_tduserParam> pagelist;//ssid

    public List<Avstmt_tduserParam> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<Avstmt_tduserParam> pagelist) {
        this.pagelist = pagelist;
    }
}
