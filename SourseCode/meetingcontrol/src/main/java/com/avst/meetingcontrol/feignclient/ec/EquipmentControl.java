package com.avst.meetingcontrol.feignclient.ec;

import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.req.*;
import com.avst.meetingcontrol.feignclient.ec.req.asr.OverAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.asr.StartAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetFlushbonadingBySsidParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetFlushbonadingTDByETSsidParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.WorkOverParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.WorkStartParam;
import com.avst.meetingcontrol.feignclient.ec.req.ph.*;
import com.avst.meetingcontrol.feignclient.ec.vo.ph.*;
import com.avst.meetingcontrol.feignclient.ec.vo.asr.AsrTxtParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.req.TxtBackParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 设备控制的代理
 */
@FeignClient(value="ec",url="localhost:8081/")
public interface EquipmentControl {

    //asr
    @RequestMapping( value = "/asr/v1/toClientForTxtBack")
    @ResponseBody
    public RResult<List<AsrTxtParam_toout>> txtBack(@RequestBody TxtBackParam txtBackParam);

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

    //开始录像
    @RequestMapping("/flushbonading/v1/workStart")
    @ResponseBody
    public RResult workStart(@RequestBody ReqParam<WorkStartParam> param);

    //结束录像
    @RequestMapping("/flushbonading/v1/workOver")
    @ResponseBody
    public RResult workOver(@RequestBody ReqParam<WorkOverParam> param);



    //ph测谎仪
    /**
     * 检测测谎仪状态
     * @param param
     * @return
     */
    @RequestMapping("/ph/v1/checkPolygraphState")
    @ResponseBody
    public RResult checkPolygraphState(@RequestBody  ReqParam<CheckPolygraphStateParam> param);

    /**
     * 开启测谎仪
     * @param param
     * @return
     */
    @RequestMapping("/ph/v1/startPolygraph")
    @ResponseBody
    public RResult startPolygraph(@RequestBody  ReqParam<StartPolygraphParam> param);

    /**
     * //结束测谎仪
     * @param param
     * @return
     */
    @RequestMapping("/ph/v1/overPolygraph")
    @ResponseBody
    public RResult overPolygraph(@RequestBody  ReqParam<OverPolygraphParam> param);

    /**
     * //获取测谎心里分析数据
     * @param param
     * @return
     */
    @RequestMapping("/ph/v1/getPolygraphAnalysis")
    @ResponseBody
    public RResult<GetPolygraphAnalysisVO> getPolygraphAnalysis(@RequestBody  ReqParam<GetPolygraphAnalysisParam> param);

    /**
     * //获取测谎仪心理分析的实时图像
     * @param param
     * @return
     */
    @RequestMapping("/ph/v1/getPolygraphRealTimeImage")
    @ResponseBody
    public RResult getPolygraphRealTimeImage(@RequestBody  ReqParam<GetPolygraphRealTimeImageParam> param);


}
