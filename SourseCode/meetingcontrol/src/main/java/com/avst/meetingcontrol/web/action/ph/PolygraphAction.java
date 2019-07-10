package com.avst.meetingcontrol.web.action.ph;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutFlushbonadingListParam;
import com.avst.meetingcontrol.feignclient.ec.req.ph.AddOrUpdateToOutPolygraphParam;
import com.avst.meetingcontrol.feignclient.ec.req.ph.GetToOutPolygraphListParam;
import com.avst.meetingcontrol.web.service.ph.PolygraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mc/polygraph")
@RestController
public class PolygraphAction extends BaseAction {

    @Autowired
    private PolygraphService polygraphService;

    @RequestMapping("/getToOutPolygraphList")
    @ResponseBody
    public RResult getToOutPolygraphList(@RequestBody ReqParam<GetToOutPolygraphListParam> param){
        RResult result=this.createNewResultOfFail();
        GetToOutPolygraphListParam getToOutPolygraphListParam=param.getParam();
        if (null!=getToOutPolygraphListParam){
            polygraphService.getToOutPolygraphList(result,getToOutPolygraphListParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return  result;
    }

    @RequestMapping("/getToOutPolygraphById")
    @ResponseBody
    public RResult getToOutPolygraphById(@RequestBody ReqParam<GetToOutPolygraphListParam> param){
        RResult result=this.createNewResultOfFail();
        GetToOutPolygraphListParam getToOutPolygraphListParam=param.getParam();
        if (null!=getToOutPolygraphListParam){
            polygraphService.getToOutPolygraphById(result,getToOutPolygraphListParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/addToOutPolygraph")
    @ResponseBody
    public RResult addToOutPolygraph(@RequestBody ReqParam<AddOrUpdateToOutPolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        AddOrUpdateToOutPolygraphParam addOrUpdateToOutPolygraphParam=param.getParam();
        if (null!=addOrUpdateToOutPolygraphParam){
            polygraphService.addToOutPolygraph(result,addOrUpdateToOutPolygraphParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/updateToOutPolygraph")
    @ResponseBody
    public RResult updateToOutPolygraph(@RequestBody ReqParam<AddOrUpdateToOutPolygraphParam> param){
        RResult result=this.createNewResultOfFail();
        AddOrUpdateToOutPolygraphParam addOrUpdateToOutPolygraphParam=param.getParam();
        if (null!=addOrUpdateToOutPolygraphParam){
            polygraphService.updateToOutPolygraph(result,addOrUpdateToOutPolygraphParam);
        }else {
            result.setMessage("参数为空");
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
