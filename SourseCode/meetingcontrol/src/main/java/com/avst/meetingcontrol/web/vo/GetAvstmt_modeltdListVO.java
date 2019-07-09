package com.avst.meetingcontrol.web.vo;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_modeltd;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param.Avstmt_modeltdAll;
import com.avst.meetingcontrol.web.req.GetAvstmt_modeltdListParam;

import java.util.List;

public class GetAvstmt_modeltdListVO {
    private List<Avstmt_modeltdAll> pagelist;
    private GetAvstmt_modeltdListParam pageparam;

    public List<Avstmt_modeltdAll> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<Avstmt_modeltdAll> pagelist) {
        this.pagelist = pagelist;
    }

    public GetAvstmt_modeltdListParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(GetAvstmt_modeltdListParam pageparam) {
        this.pageparam = pageparam;
    }
}
