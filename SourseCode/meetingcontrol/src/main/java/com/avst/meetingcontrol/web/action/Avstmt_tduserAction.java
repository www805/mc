package com.avst.meetingcontrol.web.action;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.web.req.GetAvstmt_tduserListParam;
import com.avst.meetingcontrol.web.req.GetBase_mtinfoListParam;
import com.avst.meetingcontrol.web.service.Avstmt_tduserService;
import com.avst.meetingcontrol.web.service.Base_mtinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mc/avstmt_tduser")
public class Avstmt_tduserAction extends BaseAction {
    @Autowired
    private Avstmt_tduserService avstmt_tduserService;

    //会议人员设备通道信息
    @RequestMapping("/getAvstmt_tduserList")
    public RResult getAvstmt_tduserList(GetAvstmt_tduserListParam param){
        RResult result=this.createNewResultOfFail();
        if(null==param){
            result.setMessage("参数错误");
        }else {
            avstmt_tduserService.getAvstmt_tduserList(result,param);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


}
