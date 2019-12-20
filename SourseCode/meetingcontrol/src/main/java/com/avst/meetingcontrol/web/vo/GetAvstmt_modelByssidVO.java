package com.avst.meetingcontrol.web.vo;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_model;
import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_modeltype;

import java.util.List;

public class GetAvstmt_modelByssidVO {
    private Avstmt_model avstmt_model;
    private List<Base_modeltype> base_modeltypes;//全部会议模板类型

    public List<Base_modeltype> getBase_modeltypes() {
        return base_modeltypes;
    }

    public void setBase_modeltypes(List<Base_modeltype> base_modeltypes) {
        this.base_modeltypes = base_modeltypes;
    }

    public Avstmt_model getAvstmt_model() {
        return avstmt_model;
    }

    public void setAvstmt_model(Avstmt_model avstmt_model) {
        this.avstmt_model = avstmt_model;
    }
}
