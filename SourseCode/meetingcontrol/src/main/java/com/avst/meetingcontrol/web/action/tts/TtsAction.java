package com.avst.meetingcontrol.web.action.tts;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.req.tts.AddOrUpdateToOutTtsEtinfoParam;
import com.avst.meetingcontrol.feignclient.ec.req.tts.GetToOutTtsEtinfoListParam;
import com.avst.meetingcontrol.web.service.tts.TtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mc/tts")
@RestController
public class TtsAction extends BaseAction {
    @Autowired
    private TtsService ttsService;

    @RequestMapping("/getToOutTtsEtinfoList")
    @ResponseBody
    public RResult getToOutTtsEtinfoList(@RequestBody ReqParam<GetToOutTtsEtinfoListParam> param){
        RResult result=this.createNewResultOfFail();
        GetToOutTtsEtinfoListParam getToOutTtsEtinfoListParam=param.getParam();
        if (null!=getToOutTtsEtinfoListParam){
            ttsService.getToOutTtsEtinfoList(result,getToOutTtsEtinfoListParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/getToOutTtsEtinfoById")
    @ResponseBody
    public RResult getToOutTtsEtinfoById(@RequestBody ReqParam<GetToOutTtsEtinfoListParam> param){
        RResult result=this.createNewResultOfFail();
        GetToOutTtsEtinfoListParam getToOutTtsEtinfoListParam=param.getParam();
        if (null!=getToOutTtsEtinfoListParam){
            ttsService.getToOutTtsEtinfoById(result,getToOutTtsEtinfoListParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/addToOutTtsEtinfo")
    @ResponseBody
    public RResult addToOutTtsEtinfo(@RequestBody ReqParam<AddOrUpdateToOutTtsEtinfoParam> param){
        RResult result=this.createNewResultOfFail();
        AddOrUpdateToOutTtsEtinfoParam addOrUpdateToOutTtsEtinfoParam=param.getParam();
        if (null!=addOrUpdateToOutTtsEtinfoParam){
            ttsService.addToOutTtsEtinfo(result,addOrUpdateToOutTtsEtinfoParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/updateToOutTtsEtinfo")
    @ResponseBody
    public RResult updateToOutTtsEtinfo(@RequestBody ReqParam<AddOrUpdateToOutTtsEtinfoParam> param){
        RResult result=this.createNewResultOfFail();
        AddOrUpdateToOutTtsEtinfoParam addOrUpdateToOutTtsEtinfoParam=param.getParam();
        if (null!=addOrUpdateToOutTtsEtinfoParam){
            ttsService.updateToOutTtsEtinfo(result,addOrUpdateToOutTtsEtinfoParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }
}
