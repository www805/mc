package com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_model;

import java.util.List;

/**
 * 关联模板的全部信息
 */
public class Avstmt_modelAll extends Avstmt_model {
    private List<Avstmt_modeltdAll> avstmt_modeltdAlls;//全部模板通道

    public List<Avstmt_modeltdAll> getAvstmt_modeltdAlls() {
        return avstmt_modeltdAlls;
    }

    public void setAvstmt_modeltdAlls(List<Avstmt_modeltdAll> avstmt_modeltdAlls) {
        this.avstmt_modeltdAlls = avstmt_modeltdAlls;
    }
}
