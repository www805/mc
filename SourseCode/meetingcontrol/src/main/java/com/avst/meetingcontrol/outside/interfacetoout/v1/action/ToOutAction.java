package com.avst.meetingcontrol.outside.interfacetoout.v1.action;

import com.avst.meetingcontrol.common.conf.*;
import com.avst.meetingcontrol.common.util.JacksonUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.outside.interfacetoout.req.*;
import com.avst.meetingcontrol.outside.interfacetoout.v1.service.BaseDealMCInterface;
import com.avst.meetingcontrol.outside.interfacetoout.v1.service.ToOutMCService_avst;
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
     * @param param 会废弃
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
     * 获取会议数据
     * @param param
     * @return
     */
    @RequestMapping("/getMCdata")
    @ResponseBody
    public RResult getMCdata(@RequestBody ReqParam<GetMCdataParam_out> param) {
        RResult result=createNewResultOfFail();
        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getMCdata(param,result);
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
            System.out.println(JacksonUtil.objebtToString(rResult));
        }else if(type==2){
            ReqParam<OverMCParam_out> param=new ReqParam<OverMCParam_out>();
            OverMCParam_out overMCParam_out=new OverMCParam_out();
            overMCParam_out.setMtssid(mtssid);
            overMCParam_out.setMcType(MCType.AVST);
            param.setParam(overMCParam_out);
            rResult=overMC(param);
            System.out.println(JacksonUtil.objebtToString(rResult));
        }else if(type==3){

            GetMCAsrTxtBackParam_out getMCAsrTxtBackParam_out=new GetMCAsrTxtBackParam_out();
            getMCAsrTxtBackParam_out.setMcType(MCType.AVST);
            getMCAsrTxtBackParam_out.setMtssid(mtssid);
            ReqParam<GetMCAsrTxtBackParam_out> param=new ReqParam<GetMCAsrTxtBackParam_out>();
            param.setParam(getMCAsrTxtBackParam_out);
            rResult=getMCAsrTxtBack(param);
            System.out.println(JacksonUtil.objebtToString(rResult));
        }
        return rResult;
    }

}
