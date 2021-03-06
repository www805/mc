package com.avst.meetingcontrol.outside.interfacetoout.v1.action;

import com.avst.meetingcontrol.common.conf.*;
import com.avst.meetingcontrol.common.util.JacksonUtil;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.outside.interfacetoout.req.*;
import com.avst.meetingcontrol.outside.interfacetoout.v1.service.BaseDealMCInterface;
import com.avst.meetingcontrol.outside.interfacetoout.v1.service.ToOutMCService_avst;
import com.avst.meetingcontrol.outside.interfacetoout.v1.service.ToOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/mt/v1")
/**
 * 会议对外接口
 */
public class ToOutAction extends BaseAction {

    @Autowired
    private ToOutService toOutService;


    @Autowired
    private ToOutMCService_avst toOutMCService_avst;

    private BaseDealMCInterface getBaseDealMCInterfaceImpl(String mctype){
        if(mctype.trim().equals(MCType.AVST)){//根据类型选择对应的处理，这里要优化
            return toOutMCService_avst;
        }
        return null;
    };

    @RequestMapping("/startMC")
    @ResponseBody
    public RResult startMC(@RequestBody ReqParam<StartMCParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).startMC(param,result);
        return result;
    }

    @RequestMapping("/overMC")
    @ResponseBody
    public RResult overMC(@RequestBody ReqParam<OverMCParam_out> param) {
        RResult result=createNewResultOfFail();

        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).overMC(param,result);
        return result;
    }

    /**
     * 会议暂停或者继续
     * @param param
     * @return
     */
    @RequestMapping("/pauseOrContinueMC")
    @ResponseBody
    public RResult pauseOrContinueMC(@RequestBody ReqParam<PauseOrContinueMCParam_out> param) {
        RResult result=createNewResultOfFail();
        if(null!=param&&null!=param.getParam()){
            result= getBaseDealMCInterfaceImpl(param.getParam().getMcType()).pauseOrContinueMC(param.getParam(),result);
        }else{
            result.setMessage("参数异常");
        }
        return result;
    }

    /**
     * 提供给其他业务获取语音识别数据，暂时弃用
     * @param param
     * @return
     */
    @RequestMapping("/getMCAsrTxtBack")
    @ResponseBody
    public RResult getMCAsrTxtBack(@RequestBody ReqParam<GetMCAsrTxtBackParam_out> param) {
        RResult result=createNewResultOfFail();

        //result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getMCAsrTxtBack(param,result);
        return result;
    }

    /**
     * 提供给下级语音服务推送识别结果的接口
     * @param param
     * @return
     */
    @RequestMapping("/setMCAsrTxtBack")
    @ResponseBody
    public boolean setMCAsrTxtBack(@RequestBody ReqParam<SetMCAsrTxtBackParam_out> param) {
        boolean bool=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).setMCAsrTxtBack(param);
        return bool;
    }

    /**提供下级会议实时数据
     * @param param
     * @return
     */
    @RequestMapping("/getMC")
    @ResponseBody
    public RResult getMC(@RequestBody ReqParam<GetMCParam_out> param) {
        RResult result=createNewResultOfFail();
         result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getMC(param,result);
        return result;
    }

    /**
     * 提供下级会议实时数据(会议进行中)
     * @param param
     * @return
     */
    @RequestMapping("/getMCaLLUserAsrTxtList")
    @ResponseBody
    public RResult getMCaLLUserAsrTxtList(@RequestBody ReqParam<GetMCaLLUserAsrTxtListParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getMCaLLUserAsrTxtList(param,result);
        return result;
    }

    /**
     * 获取会议状态
     * @param param
     * @return
     */
    @RequestMapping("/getMCState")
    @ResponseBody
    public RResult getMCState(@RequestBody ReqParam<GetMCStateParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getMCState(param,result);
        return result;
    }

    /**
     * 根据会议获取身心监护ssid
     * @param param
     * @return
     */
    @RequestMapping("/getPhssidByMTssid")
    @ResponseBody
    public RResult getPhssidByMTssid(@RequestBody ReqParam<GetPhssidByMTssidParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getPhssidByMTssid(param,result);
        return result;
    }

    /**
     * 提供给其他业务调用身心监护数据
     * @param param
     * @return
     */
    @RequestMapping("/getPHData")
    @ResponseBody
    public RResult getPHData(@RequestBody ReqParam<GetPHDataParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getPHData(param,result);
        return result;
    }

    @RequestMapping("/getPHDataBack")
    @ResponseBody
    public RResult getPHDataBack(@RequestBody ReqParam<GetPHDataBackParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getPHDataBack(param,result);
        return result;
    }



    /**
     * 获取会议缓存
     * @param param
     * @return
     */
    @RequestMapping("/getMCCacheParamByMTssid")
    @ResponseBody
    public RResult getMCCacheParamByMTssid(@RequestBody ReqParam<GetMCCacheParamByMTssidParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getMCCacheParamByMTssid(param,result);
        return result;
    }

    /**
     * 获取会议用户通道缓存
     * @param param
     * @return
     */
    @RequestMapping("/getTDCacheParamByMTssid")
    @ResponseBody
    public RResult getTDCacheParamByMTssid(@RequestBody ReqParam<GetTDCacheParamByMTssidParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getTDCacheParamByMTssid(param,result);
        return result;
    }

    /**
     * 获取到模板-通道列表-单通道（所有信息）
     * @param param
     * @return
     */
    @RequestMapping("/getTDByMTList")
    @ResponseBody
    public RResult getTDByMTList(@RequestBody ReqParam<GetTDCacheParamByMTssidParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getTDByMTList(param,result);
        return result;
    }





    /**
     *  获取会议全部模板
     * @param param
     * @return
     */
    @RequestMapping("/getMc_model")
    @ResponseBody
    public RResult getMc_model(@RequestBody ReqParam<GetMc_modelParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getMc_model(param,result);
        return result;
    }

    /**
     *  获取模板会议通道
     * @param param
     * @return
     */
    @RequestMapping("/getTdByModelSsid")
    @ResponseBody
    public RResult getTdByModelSsid(@RequestBody ReqParam<GetTdByModelSsidParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getTdByModelSsid(param,result);
        return result;
    }

    /**
     *  提供给上层业务平台，用于关闭突然中断的会议以及设备
     * @param param
     * @return
     */
    @RequestMapping("/overAccidentMT")
    @ResponseBody
    public RResult overAccidentMT(@RequestBody ReqParam<OverAccidentMTParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).overAccidentMT(param.getParam(),result);
        return result;
    }


    /**
     * 提供给总控的心跳检测
     * @return
     */
    @RequestMapping("/checkClient")
    @ResponseBody
    public RResult checkClient(@RequestBody ReqParam param){
        RResult rresult=createNewResultOfFail();
        rresult=toOutService.checkClient(rresult,param);
        return rresult;
    }


    /**
     * 更新会议缓存语音识别打点标记文本
     * @param param
     * @return
     */
    @RequestMapping("/setMCTagTxt")
    @ResponseBody
    public RResult setMCTagTxt(@RequestBody ReqParam<SetMCTagTxtParam_out> param){
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).setMCTagTxt(param.getParam(),result);
        return result;
    }

    /**
     * 获取默认会议模板
     * @param param
     * @return
     */
    @RequestMapping("/getDefaultMTModel")
    @ResponseBody
    public RResult getDefaultMTModel(@RequestBody GetDefaultMTModelParam param){
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getMcType()).getDefaultMTModel(param,result);
        return result;
    }




    @RequestMapping("/ceshimc")
    public RResult ceshi(int type,String mtssid ) {
        RResult rResult=createNewResultOfFail();
        if(type==1){
            ReqParam<StartMCParam_out> param=new ReqParam<StartMCParam_out>();
            StartMCParam_out startMCParam_out=new StartMCParam_out();
            startMCParam_out.setMeetingtype(2);//音频会议
            startMCParam_out.setMcType(MCType.AVST);
            startMCParam_out.setModelbool(1);
            startMCParam_out.setMtmodelssid("asgfjry521784h67");
            startMCParam_out.setYwSystemType(YWType.RECORD_TRM);
            List<TdAndUserAndOtherParam> tdList=new ArrayList<TdAndUserAndOtherParam>();
            TdAndUserAndOtherParam tdAndUserAndOtherParam=new TdAndUserAndOtherParam();
            tdAndUserAndOtherParam.setGrade(1);
            tdAndUserAndOtherParam.setUsername("zhangsan");
            tdAndUserAndOtherParam.setUserssid("1654");
            tdAndUserAndOtherParam.setAsrtype(ASRType.AVST);
            tdAndUserAndOtherParam.setFdtype(FDType.FD_AVST);
            tdAndUserAndOtherParam.setUserecord(1);
            tdAndUserAndOtherParam.setUseasr(1);
            tdList.add(tdAndUserAndOtherParam);
            tdAndUserAndOtherParam=new TdAndUserAndOtherParam();
            tdAndUserAndOtherParam.setGrade(2);
            tdAndUserAndOtherParam.setUsername("李四");
            tdAndUserAndOtherParam.setUserssid("1234567");
            tdAndUserAndOtherParam.setAsrtype(ASRType.AVST);
            tdAndUserAndOtherParam.setFdtype(FDType.FD_AVST);
            tdAndUserAndOtherParam.setUserecord(1);
            tdAndUserAndOtherParam.setUseasr(1);
            tdAndUserAndOtherParam.setUsepolygraph(1);
            tdAndUserAndOtherParam.setPolygraphtype(PHType.CMCROSS);
            tdList.add(tdAndUserAndOtherParam);
            startMCParam_out.setTdList(tdList);
            param.setParam(startMCParam_out);
            rResult=startMC(param);
            LogUtil.intoLog(this.getClass(),JacksonUtil.objebtToString(rResult));
        }else if(type==2){
            ReqParam<OverMCParam_out> param=new ReqParam<OverMCParam_out>();
            OverMCParam_out overMCParam_out=new OverMCParam_out();
            overMCParam_out.setMtssid(mtssid);
            overMCParam_out.setMcType(MCType.AVST);
            param.setParam(overMCParam_out);
            rResult=overMC(param);
            LogUtil.intoLog(this.getClass(),JacksonUtil.objebtToString(rResult));
        }else if(type==3){

            GetMCAsrTxtBackParam_out getMCAsrTxtBackParam_out=new GetMCAsrTxtBackParam_out();
            getMCAsrTxtBackParam_out.setMcType(MCType.AVST);
            getMCAsrTxtBackParam_out.setMtssid(mtssid);
            ReqParam<GetMCAsrTxtBackParam_out> param=new ReqParam<GetMCAsrTxtBackParam_out>();
            param.setParam(getMCAsrTxtBackParam_out);
            rResult=getMCAsrTxtBack(param);
            LogUtil.intoLog(this.getClass(),JacksonUtil.objebtToString(rResult));
        }
        return rResult;
    }

}
