package com.avst.meetingcontrol.feignclient.ec;

import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.req.*;
import com.avst.meetingcontrol.feignclient.ec.req.GetAsrServerBySsidParam;
import com.avst.meetingcontrol.feignclient.ec.req.asr.*;
import com.avst.meetingcontrol.feignclient.ec.req.asr.OverAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.asr.StartAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.*;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetFlushbonadingBySsidParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetFlushbonadingTDByETSsidParam;
import com.avst.meetingcontrol.feignclient.ec.req.ph.*;
import com.avst.meetingcontrol.feignclient.ec.req.ss.GetSavePathParam;
import com.avst.meetingcontrol.feignclient.ec.req.ss.SaveFile_localParam;
import com.avst.meetingcontrol.feignclient.ec.req.tts.AddOrUpdateToOutTtsEtinfoParam;
import com.avst.meetingcontrol.feignclient.ec.req.tts.GetToOutTtsEtinfoListParam;
import com.avst.meetingcontrol.feignclient.ec.vo.ph.*;
import com.avst.meetingcontrol.feignclient.ec.vo.asr.AsrTxtParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.req.TxtBackParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 设备控制的代理
 */
@FeignClient("ec")
public interface EquipmentControl {

    //asr
    @RequestMapping( value = "/asr/v1/toClientForTxtBack")
    @ResponseBody
    public RResult<List<AsrTxtParam_toout>> txtBack(@RequestBody TxtBackParam txtBackParam);

    @RequestMapping("/asr/v1/startAsr")
    @ResponseBody
    public RResult startAsr(@RequestBody ReqParam<StartAsrParam> param);

    @RequestMapping("/asr/v1/pauseOrContinueAsr")
    @ResponseBody
    public RResult pauseOrContinueAsr(@RequestBody ReqParam<PauseOrContinueAsrParam> param);

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

    //暂停/继续工作
    @RequestMapping("/flushbonading/v1/workPauseOrContinue")
    @ResponseBody
    public RResult workPauseOrContinue(@RequestBody ReqParam<WorkPauseOrContinueParam> param);



    //ph身心监护
    /**
     * 检测身心监护状态
     * @param param
     * @return
     */
    @RequestMapping("/ph/v1/checkPolygraphState")
    @ResponseBody
    public RResult checkPolygraphState(@RequestBody  ReqParam<CheckPolygraphStateParam> param);

    /**
     * 开启身心监护
     * @param param
     * @return
     */
    @RequestMapping("/ph/v1/startPolygraph")
    @ResponseBody
    public RResult startPolygraph(@RequestBody  ReqParam<StartPolygraphParam> param);

    /**
     * //结束身心监护
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
     * //获取身心监护心理分析的实时图像
     * @param param
     * @return
     */
    @RequestMapping("/ph/v1/getPolygraphRealTimeImage")
    @ResponseBody
    public RResult getPolygraphRealTimeImage(@RequestBody  ReqParam<GetPolygraphRealTimeImageParam> param);


    /**
     * 存储本地服务的数据
     * @param param
     * @return
     */
    @RequestMapping("/ss/v1/saveFile_local")
    @ResponseBody
    public RResult saveFile_local(@RequestBody SaveFile_localParam param);


    @RequestMapping("/ss/v1/getSavePath")
    @ResponseBody
    public RResult getSavePath(@RequestBody GetSavePathParam param);

    //通过会议通道ssid查询指定的直播地址
    @RequestMapping("/flushbonadingEttd/v1/getFlushbonadingEttdByMcSsid")
    @ResponseBody
    public RResult getFlushbonadingEttdByMcSsid(@RequestBody ReqParam<GetToOutFlushbonadingEttdByListParam> param);

    @RequestMapping("/flushbonading/v1/workOver_Accident")
    @ResponseBody
    public RResult workOver_Accident(@RequestBody ReqParam<WorkOver_AccidentParam> param);


    /*--------------------------------以下为：提供给会议后台显示的接口---start--------------------------------------*/

    //审讯设备
    @RequestMapping("/flushbonading/v1/getToOutFlushbonadingList")
    @ResponseBody
    public RResult getToOutFlushbonadingList(@RequestBody ReqParam<GetToOutFlushbonadingListParam> param);

    @RequestMapping("/flushbonading/v1/getToOutFlushbonadingById")
    @ResponseBody
    public RResult getToOutFlushbonadingById(@RequestBody ReqParam<GetToOutFlushbonadingListParam> param);

    @RequestMapping("/flushbonading/v1/addToOutFlushbonading")
    @ResponseBody
    public RResult addToOutFlushbonading(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingParam> param);

    @RequestMapping("/flushbonading/v1/updateToOutFlushbonading")
    @ResponseBody
    public RResult updateToOutFlushbonading(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingParam> param);


    @RequestMapping("/flushbonading/v1/getptdjconst")
    @ResponseBody
    public RResult getptdjconst(@RequestBody ReqParam<GetToOutptdjconstParam> param);

    @RequestMapping("/flushbonading/v1/getToOutMiddleware_FTP")
    @ResponseBody
    public RResult getToOutMiddleware_FTP(@RequestBody ReqParam<GetToOutMiddleware_FTPParam> param);

    @RequestMapping("/flushbonading/v1/setToOutMiddleware_FTP")
    @ResponseBody
    public RResult setToOutMiddleware_FTP(@RequestBody ReqParam<SetToOutMiddleware_FTPParam> param);


    //身心监护
    @RequestMapping("/polygraph/v1/getToOutPolygraphList")
    @ResponseBody
    public RResult getToOutPolygraphList(@RequestBody ReqParam<GetToOutPolygraphListParam> param);

    @RequestMapping("/polygraph/v1/getToOutPolygraphById")
    @ResponseBody
    public RResult getToOutPolygraphById(@RequestBody ReqParam<GetToOutPolygraphListParam> param);

    @RequestMapping("/polygraph/v1/addToOutPolygraph")
    @ResponseBody
    public RResult addToOutPolygraph(@RequestBody ReqParam<AddOrUpdateToOutPolygraphParam> param);

    @RequestMapping("/polygraph/v1/updateToOutPolygraph")
    @ResponseBody
    public RResult updateToOutPolygraph(@RequestBody ReqParam<AddOrUpdateToOutPolygraphParam> param);



    //语音识别服务
    @RequestMapping("/asr/v1/getToOutAsrList")
    @ResponseBody
    public RResult getToOutAsrList(@RequestBody ReqParam<GetToOutAsrListParam> param);

    @RequestMapping("/asr/v1/getToOutAsrById")
    @ResponseBody
    public RResult getToOutAsrById(@RequestBody ReqParam<GetToOutAsrListParam> param);

    @RequestMapping("/asr/v1/addToOutAsr")
    @ResponseBody
    public RResult addToOutAsr(@RequestBody ReqParam<AddOrUpdateToOutAsrParam> param);

    @RequestMapping("/asr/v1/updateToOutAsr")
    @ResponseBody
    public RResult updateToOutAsr(@RequestBody ReqParam<AddOrUpdateToOutAsrParam> param);



    //设备通道
    @RequestMapping("/flushbonadingEttd/v1/getToOutFlushbonadingEttdList")
    @ResponseBody
    public RResult getToOutFlushbonadingEttdList(@RequestBody ReqParam<GetToOutFlushbonadingEttdListParam> param);


    @RequestMapping("/flushbonadingEttd/v1/getToOutFlushbonadingEttdById")
    @ResponseBody
    public RResult getToOutFlushbonadingEttdById(@RequestBody ReqParam<GetToOutFlushbonadingEttdListParam> param);

    @RequestMapping("/flushbonadingEttd/v1/addToOutFlushbonadingEttd")
    @ResponseBody
    public RResult addToOutFlushbonadingEttd(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingEttdParam> param);

    @RequestMapping("/flushbonadingEttd/v1/updateToOutFlushbonadingEttd")
    @ResponseBody
    public RResult updateToOutFlushbonadingEttd(@RequestBody ReqParam<AddOrUpdateToOutFlushbonadingEttdParam> param);


    //tts :未启用会议这边不需要tts显示
    @RequestMapping("/tts/v1/getToOutTtsEtinfoList")
    @ResponseBody
    public RResult getToOutTtsEtinfoList(@RequestBody ReqParam<GetToOutTtsEtinfoListParam> param);

    @RequestMapping("/tts/v1/getToOutTtsEtinfoById")
    @ResponseBody
    public RResult getToOutTtsEtinfoById(@RequestBody ReqParam<GetToOutTtsEtinfoListParam> param);

    @RequestMapping("/tts/v1/addToOutTtsEtinfo")
    @ResponseBody
    public RResult addToOutTtsEtinfo(@RequestBody ReqParam<AddOrUpdateToOutTtsEtinfoParam> param);

    @RequestMapping("/tts/v1/updateToOutTtsEtinfo")
    @ResponseBody
    public RResult updateToOutTtsEtinfo(@RequestBody ReqParam<AddOrUpdateToOutTtsEtinfoParam> param);


    //设备数
    @RequestMapping("/base/v1/main/gethome")
    @ResponseBody
    public RResult gethome(@RequestBody ReqParam param);

    //获取所有基础设备
    @PostMapping(value = "/base/v1/main/getToOutBaseEc")
    @ResponseBody
    public RResult getToOutBaseEc(@RequestBody GetToOutBaseEcParam param);

    /*--------------------------------以下为：提供给会议后台显示的接口---end--------------------------------------*/

    /**
     * 提供查询基本类型的接口
     * @param param
     * @return
     */
    @RequestMapping("/base/v1/main/getToOutBaseList")
    @ResponseBody
    public RResult getToOutBaseList(@RequestBody GetToOutBaseListParam param);









}
