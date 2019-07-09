package com.avst.meetingcontrol.web.vo;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_model;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param.Avstmt_modelAll;
import com.avst.meetingcontrol.web.req.GetAvstmt_modelListParam;

import java.util.List;

public class GetAvstmt_modelListVO {
    private GetAvstmt_modelListParam pageparam;

    private List<Avstmt_modelAll> pagelist;

    public GetAvstmt_modelListParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(GetAvstmt_modelListParam pageparam) {
        this.pageparam = pageparam;
    }

    public List<Avstmt_modelAll> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<Avstmt_modelAll> pagelist) {
        this.pagelist = pagelist;
    }
}
