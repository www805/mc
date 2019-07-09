package com.avst.meetingcontrol.web.vo;

import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_mtinfo;
import com.avst.meetingcontrol.web.req.GetBase_mtinfoListParam;

import java.util.List;

public class GetBase_mtinfoListVO {
    private List<Base_mtinfo> pagelist;
    private GetBase_mtinfoListParam pageparam;

    public List<Base_mtinfo> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<Base_mtinfo> pagelist) {
        this.pagelist = pagelist;
    }

    public GetBase_mtinfoListParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(GetBase_mtinfoListParam pageparam) {
        this.pageparam = pageparam;
    }
}
