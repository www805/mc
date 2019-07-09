package com.avst.meetingcontrol.web.action;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.web.req.GetBase_mtinfoListParam;
import com.avst.meetingcontrol.web.service.Base_mtinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mc/base_mtinfo")
@RestController
public class Base_mtinfoAction extends BaseAction {
    @Autowired
    private Base_mtinfoService base_mtinfoService;

    @RequestMapping("/getBase_mtinfoList")
    public RResult getBase_mtinfoList(GetBase_mtinfoListParam param){
        RResult result=this.createNewResultOfFail();
        if(null==param){
            result.setMessage("参数错误");
        }else {
            base_mtinfoService.getBase_mtinfoList(result,param);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }




}
