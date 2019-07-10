package com.avst.meetingcontrol.web.action.flushbonading;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.AddOrUpdateToOutFlushbonadingParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutFlushbonadingListParam;
import com.avst.meetingcontrol.web.service.flushbonading.FlushbonadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mc/flushbonading")
@RestController
public class FlushbonadingAction extends BaseAction {

    @Autowired
    private FlushbonadingService flushbonadingService;


    @RequestMapping("/getToOutFlushbonadingList")
    @ResponseBody
    public RResult getToOutFlushbonadingList(@RequestBody ReqParam<GetToOutFlushbonadingListParam> param){
        RResult result=this.createNewResultOfFail();
        GetToOutFlushbonadingListParam getToOutFlushbonadingListParam=param.getParam();
        if (null!=getToOutFlushbonadingListParam){
            flushbonadingService.getToOutFlushbonadingList(result,getToOutFlushbonadingListParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/getToOutFlushbonadingById")
    @ResponseBody
    public RResult getToOutFlushbonadingById(@RequestBody ReqParam<GetToOutFlushbonadingListParam> param){
        RResult result=this.createNewResultOfFail();
        GetToOutFlushbonadingListParam getToOutFlushbonadingListParam=param.getParam();
        if (null!=getToOutFlushbonadingListParam){
            flushbonadingService.getToOutFlushbonadingById(result,getToOutFlushbonadingListParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/addToOutFlushbonading")
    @ResponseBody
    public RResult addToOutFlushbonading(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingParam> param){
        RResult result=this.createNewResultOfFail();
        AddOrUpdateToOutFlushbonadingParam addOrUpdateToOutFlushbonadingParam=param.getParam();
        if (null!=addOrUpdateToOutFlushbonadingParam){
            flushbonadingService.addToOutFlushbonading(result,addOrUpdateToOutFlushbonadingParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/updateToOutFlushbonading")
    @ResponseBody
    public RResult updateToOutFlushbonading(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingParam> param){
        RResult result=this.createNewResultOfFail();
        AddOrUpdateToOutFlushbonadingParam addOrUpdateToOutFlushbonadingParam=param.getParam();
        if (null!=addOrUpdateToOutFlushbonadingParam){
            flushbonadingService.updateToOutFlushbonading(result,addOrUpdateToOutFlushbonadingParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


}
