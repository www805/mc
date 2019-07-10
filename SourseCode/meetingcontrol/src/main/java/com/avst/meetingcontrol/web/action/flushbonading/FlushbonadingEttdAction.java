package com.avst.meetingcontrol.web.action.flushbonading;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.AddOrUpdateToOutFlushbonadingEttdParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutFlushbonadingEttdListParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutFlushbonadingListParam;
import com.avst.meetingcontrol.web.service.flushbonading.FlushbonadingEttdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mc/flushbonadingettd")
@RestController
public class FlushbonadingEttdAction extends BaseAction {
    @Autowired
    private FlushbonadingEttdService flushbonadingEttdService;

    @RequestMapping("/getToOutFlushbonadingEttdList")
    @ResponseBody
    public RResult getToOutFlushbonadingEttdList(@RequestBody ReqParam<GetToOutFlushbonadingEttdListParam> param){
        RResult result=this.createNewResultOfFail();
        GetToOutFlushbonadingEttdListParam getToOutFlushbonadingEttdListParam=param.getParam();
        if (null!=getToOutFlushbonadingEttdListParam){
            flushbonadingEttdService.getToOutFlushbonadingEttdList(result,getToOutFlushbonadingEttdListParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    @RequestMapping("/getToOutFlushbonadingEttdById")
    @ResponseBody
    public RResult getToOutFlushbonadingEttdById(@RequestBody ReqParam<GetToOutFlushbonadingEttdListParam> param){
        RResult result=this.createNewResultOfFail();
        GetToOutFlushbonadingEttdListParam getToOutFlushbonadingEttdListParam=param.getParam();
        if (null!=getToOutFlushbonadingEttdListParam){
            flushbonadingEttdService.getToOutFlushbonadingEttdById(result,getToOutFlushbonadingEttdListParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/addToOutFlushbonadingEttd")
    @ResponseBody
    public RResult addToOutFlushbonadingEttd(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        AddOrUpdateToOutFlushbonadingEttdParam addOrUpdateToOutFlushbonadingEttdParam=param.getParam();
        if (null!=addOrUpdateToOutFlushbonadingEttdParam){
            flushbonadingEttdService.addToOutFlushbonadingEttd(result,addOrUpdateToOutFlushbonadingEttdParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/updateToOutFlushbonadingEttd")
    @ResponseBody
    public RResult updateToOutFlushbonadingEttd(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingEttdParam> param){
        RResult result=this.createNewResultOfFail();
        AddOrUpdateToOutFlushbonadingEttdParam addOrUpdateToOutFlushbonadingEttdParam=param.getParam();
        if (null!=addOrUpdateToOutFlushbonadingEttdParam){
            flushbonadingEttdService.updateToOutFlushbonadingEttd(result,addOrUpdateToOutFlushbonadingEttdParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


}
