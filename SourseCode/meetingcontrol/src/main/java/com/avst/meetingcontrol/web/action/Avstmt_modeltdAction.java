package com.avst.meetingcontrol.web.action;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.web.req.*;
import com.avst.meetingcontrol.web.service.Avstmt_modeltdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mc/avstmt_modeltd")
@RestController
public class Avstmt_modeltdAction extends BaseAction {
    @Autowired
    private Avstmt_modeltdService avstmt_modeltdService;

    @RequestMapping("/getAvstmt_modeltdList")
    public RResult getAvstmt_modeltdList(GetAvstmt_modeltdListParam param){
        RResult result=this.createNewResultOfFail();
        if(null==param){
            result.setMessage("参数错误");
        }else {
            avstmt_modeltdService.getAvstmt_modeltdList(result,param);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/getAvstmt_modeltdByssid")
    public RResult getAvstmt_modeltdByssid(GetAvstmt_modeltdByssidParam param){
        RResult result=this.createNewResultOfFail();
        if(null==param){
            result.setMessage("参数错误");
        }else {
            avstmt_modeltdService.getAvstmt_modeltdByssid(result,param);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/updateAvstmt_modeltd")
    public RResult updateAvstmt_modeltd(UpdateAvstmt_modeltdParam param){
        RResult result=this.createNewResultOfFail();
        if(null==param){
            result.setMessage("参数错误");
        }else {
            avstmt_modeltdService.updateAvstmt_modeltd(result,param);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/addAvstmt_modeltd")
    public RResult addAvstmt_modeltd(AddAvstmt_modeltdParam param){
        RResult result=this.createNewResultOfFail();
        if(null==param){
            result.setMessage("参数错误");
        }else {
            avstmt_modeltdService.addAvstmt_modeltd(result,param);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }




}
