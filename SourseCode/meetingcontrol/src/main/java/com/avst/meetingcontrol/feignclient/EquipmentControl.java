package com.avst.meetingcontrol.feignclient;

import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.req.*;
import com.avst.meetingcontrol.feignclient.vo.AsrTxtParam_toout;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 设备控制的代理
 */
@FeignClient(value="ec",url="localhost:8081/")
public interface EquipmentControl {

    //avstmt
    @RequestMapping( value = "/asr/v1/toClientForTxtBack")
    @ResponseBody
    public RResult<AsrTxtParam_toout> txtBack(@RequestBody String asrid);

    @RequestMapping("/asr/v1/startAsr")
    @ResponseBody
    public RResult startAsr(@RequestBody ReqParam<StartAsrParam> param);

    @RequestMapping("/asr/v1/overAsr")
    @ResponseBody
    public RResult overAsr(@RequestBody ReqParam<OverAsrParam> param);

    @RequestMapping("/asr/v1/getAsrServerBySsid")
    @ResponseBody
    public RResult getAsrServerBySsid(@RequestBody ReqParam<GetAsrServerBySsidParam> param);

    @RequestMapping("/asr/v1/ceshi")
    @ResponseBody
    public String ceshi(@RequestBody String param);

    //flushbonading
    @RequestMapping("/flushbonading/v1/getFlushbonadingBySsid")
    @ResponseBody
    public RResult getFlushbonadingBySsid(@RequestBody ReqParam<GetFlushbonadingBySsidParam> param);

    @RequestMapping("/flushbonading/v1/getFlushbonadingTDByETSsid")
    @ResponseBody
    public RResult getFlushbonadingTDByETSsid(@RequestBody ReqParam<GetFlushbonadingTDByETSsidParam> param);


}
