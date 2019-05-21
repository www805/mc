package com.avst.meetingcontrol.outside.interfacetoout.v1.action;

import com.avst.meetingcontrol.common.util.JacksonUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.AsrForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.MCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrForMCCache_oneParam;
import com.avst.meetingcontrol.outside.interfacetoout.req.GetMCAsrTxtBackParam_out;
import com.avst.meetingcontrol.outside.interfacetoout.req.OverMCParam_out;
import com.avst.meetingcontrol.outside.interfacetoout.req.StartMCParam_out;
import com.avst.meetingcontrol.outside.interfacetoout.req.TdAndUserAndOtherParam;
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
        if(mctype.equals(MCCache.MCTYPE)){//根据类型选择对应的处理，这里要优化
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

    @RequestMapping("/getMCAsrTxtBack")
    @ResponseBody
    public RResult getMCAsrTxtBack(@RequestBody ReqParam<GetMCAsrTxtBackParam_out> param) {
        RResult result=createNewResultOfFail();

        result=getBaseDealMCInterfaceImpl(param.getParam().getMcType()).getMCAsrTxtBack(param,result);
        return result;
    }


    @RequestMapping("/ceshimc")
    public RResult ceshi(int type,String mtssid ) {
        RResult rResult=createNewResultOfFail();
        if(type==1){
            ReqParam<StartMCParam_out> param=new ReqParam<StartMCParam_out>();
            StartMCParam_out startMCParam_out=new StartMCParam_out();
            startMCParam_out.setMeetingtype(2);//音频会议
            startMCParam_out.setMcType("AVST");
            startMCParam_out.setModelbool(1);
            startMCParam_out.setMtmodelssid("asgfjry521784h67");
            List<TdAndUserAndOtherParam> tdList=new ArrayList<TdAndUserAndOtherParam>();
            TdAndUserAndOtherParam tdAndUserAndOtherParam=new TdAndUserAndOtherParam();
            tdAndUserAndOtherParam.setGrade(1);
            tdAndUserAndOtherParam.setUsername("zhangsan");
            tdAndUserAndOtherParam.setUserssid("1654");
            tdAndUserAndOtherParam.setAsrtype("AVST");
            tdList.add(tdAndUserAndOtherParam);
            tdAndUserAndOtherParam=new TdAndUserAndOtherParam();
            tdAndUserAndOtherParam.setGrade(2);
            tdAndUserAndOtherParam.setUsername("李四");
            tdAndUserAndOtherParam.setUserssid("1234567");
            tdAndUserAndOtherParam.setAsrtype("AVST");
            tdList.add(tdAndUserAndOtherParam);
            startMCParam_out.setTdList(tdList);
            param.setParam(startMCParam_out);
            rResult=startMC(param);
            System.out.println(JacksonUtil.objebtToString(rResult));
        }else if(type==2){
            ReqParam<OverMCParam_out> param=new ReqParam<OverMCParam_out>();
            OverMCParam_out overMCParam_out=new OverMCParam_out();
            overMCParam_out.setMtssid(mtssid);
            overMCParam_out.setMcType("AVST");
            param.setParam(overMCParam_out);
            rResult=overMC(param);
            System.out.println(JacksonUtil.objebtToString(rResult));
        }else if(type==3){

            GetMCAsrTxtBackParam_out getMCAsrTxtBackParam_out=new GetMCAsrTxtBackParam_out();
            getMCAsrTxtBackParam_out.setMcType("AVST");
            getMCAsrTxtBackParam_out.setMtssid(mtssid);
            ReqParam<GetMCAsrTxtBackParam_out> param=new ReqParam<GetMCAsrTxtBackParam_out>();
            param.setParam(getMCAsrTxtBackParam_out);
            rResult=getMCAsrTxtBack(param);
            System.out.println(JacksonUtil.objebtToString(rResult));
        }
        return rResult;
    }

}
