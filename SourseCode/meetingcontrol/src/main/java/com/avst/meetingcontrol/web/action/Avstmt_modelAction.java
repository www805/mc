package com.avst.meetingcontrol.web.action;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.web.req.AddAvstmt_modelParam;
import com.avst.meetingcontrol.web.req.GetAvstmt_modelByssidParam;
import com.avst.meetingcontrol.web.req.GetAvstmt_modelListParam;
import com.avst.meetingcontrol.web.req.UpdateAvstmt_modelParam;
import com.avst.meetingcontrol.web.service.Avstmt_modelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RequestMapping("/mc/avstmt_model")
@RestController
public class Avstmt_modelAction extends BaseAction {

    @Autowired
    private Avstmt_modelService avstmt_modelService;

    @RequestMapping("/getAvstmt_modelList")
    public RResult getAvstmt_modelList(GetAvstmt_modelListParam param){
        RResult result=this.createNewResultOfFail();
        if(null==param){
           result.setMessage("参数错误");
        }else {
            avstmt_modelService.getAvstmt_modelList(result,param);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/updateAvstmt_model")
    public RResult updateAvstmt_model(UpdateAvstmt_modelParam param){
        RResult result=this.createNewResultOfFail();
        if(null==param){
            result.setMessage("参数错误");
        }else {
            avstmt_modelService.updateAvstmt_model(result,param);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    @RequestMapping("/addAvstmt_model")
    public RResult addAvstmt_model(AddAvstmt_modelParam param){
        RResult result=this.createNewResultOfFail();
        if(null==param){
            result.setMessage("参数错误");
        }else {
            avstmt_modelService.addAvstmt_model(result,param);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/getAvstmt_modelByssid")
    public RResult getAvstmt_modelByssid(GetAvstmt_modelByssidParam param){
        RResult result=this.createNewResultOfFail();
        if(null==param){
            result.setMessage("参数错误");
        }else {
            avstmt_modelService.getAvstmt_modelByssid(result,param);
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }










}
