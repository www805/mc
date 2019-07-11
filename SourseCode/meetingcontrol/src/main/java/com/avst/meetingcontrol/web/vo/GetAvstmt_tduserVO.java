package com.avst.meetingcontrol.web.vo;

import com.avst.meetingcontrol.web.req.GetAvstmt_tduserListParam;

import java.util.List;

public class GetAvstmt_tduserVO {

    private List<AvstmtTduserVO> pagelist;
    private GetAvstmt_tduserListParam pageparam;

    public List<AvstmtTduserVO> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<AvstmtTduserVO> pagelist) {
        this.pagelist = pagelist;
    }

    public GetAvstmt_tduserListParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(GetAvstmt_tduserListParam pageparam) {
        this.pageparam = pageparam;
    }
}
