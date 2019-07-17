package com.avst.meetingcontrol.web.action.asr;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.req.asr.AddOrUpdateToOutAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.asr.GetToOutAsrListParam;
import com.avst.meetingcontrol.web.req.UpdateBoolParam;
import com.avst.meetingcontrol.web.service.asr.AsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mc/asr")
@RestController
public class AsrAction extends BaseAction {

    @Autowired
    private AsrService asrService;

    @RequestMapping("/getToOutAsrList")
    @ResponseBody
    public RResult getToOutAsrList(@RequestBody ReqParam<GetToOutAsrListParam> param){
        RResult result=this.createNewResultOfFail();
        GetToOutAsrListParam getToOutAsrListParam=param.getParam();
        if (null!=getToOutAsrListParam){
            asrService.getToOutAsrList(result,getToOutAsrListParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/getToOutAsrById")
    @ResponseBody
    public RResult getToOutAsrById(@RequestBody ReqParam<GetToOutAsrListParam> param){
        RResult result=this.createNewResultOfFail();
        GetToOutAsrListParam getToOutAsrListParam=param.getParam();
        if (null!=getToOutAsrListParam){
            asrService.getToOutAsrById(result,getToOutAsrListParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/addToOutAsr")
    @ResponseBody
    public RResult addToOutAsr(@RequestBody ReqParam<AddOrUpdateToOutAsrParam> param){
        RResult result=this.createNewResultOfFail();
        AddOrUpdateToOutAsrParam addOrUpdateToOutAsrParam=param.getParam();
        if (null!=addOrUpdateToOutAsrParam){
            asrService.addToOutAsr(result,addOrUpdateToOutAsrParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/updateToOutAsr")
    @ResponseBody
    public RResult updateToOutAsr(@RequestBody ReqParam<AddOrUpdateToOutAsrParam> param){
        RResult result=this.createNewResultOfFail();
        AddOrUpdateToOutAsrParam addOrUpdateToOutAsrParam=param.getParam();
        if (null!=addOrUpdateToOutAsrParam){
            asrService.updateToOutAsr(result,addOrUpdateToOutAsrParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    //修改会议模板通道，是否需要测谎状态
    @RequestMapping("/updateUsepolygraphBool")
    @ResponseBody
    public RResult updateUsepolygraphBool(@RequestBody ReqParam<UpdateBoolParam> param){
        RResult result=this.createNewResultOfFail();
        UpdateBoolParam updateBoolParam=param.getParam();
        if (null!=updateBoolParam){
            asrService.updateUsepolygraphBool(result,updateBoolParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    //修改会议模板通道的语音识别开关状态
    @RequestMapping("/updateUseasrBool")
    @ResponseBody
    public RResult updateUseasrBool(@RequestBody ReqParam<UpdateBoolParam> param){
        RResult result=this.createNewResultOfFail();
        UpdateBoolParam updateBoolParam=param.getParam();
        if (null!=updateBoolParam){
            asrService.updateUseasrBool(result,updateBoolParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
