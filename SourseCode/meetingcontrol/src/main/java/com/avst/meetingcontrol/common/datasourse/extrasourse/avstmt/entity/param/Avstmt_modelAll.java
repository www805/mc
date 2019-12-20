package com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_model;
import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_modeltype;

import java.util.List;

/**
 * 关联模板的全部信息
 */
public class Avstmt_modelAll extends Avstmt_model {
    private Base_modeltype base_modeltype;//对应会议模板类型
    private List<Avstmt_modeltdAll> avstmt_modeltdAlls;//全部模板通道

    public List<Avstmt_modeltdAll> getAvstmt_modeltdAlls() {
        return avstmt_modeltdAlls;
    }

    public void setAvstmt_modeltdAlls(List<Avstmt_modeltdAll> avstmt_modeltdAlls) {
        this.avstmt_modeltdAlls = avstmt_modeltdAlls;
    }

    public Base_modeltype getBase_modeltype() {
        return base_modeltype;
    }

    public void setBase_modeltype(Base_modeltype base_modeltype) {
        this.base_modeltype = base_modeltype;
    }
}
