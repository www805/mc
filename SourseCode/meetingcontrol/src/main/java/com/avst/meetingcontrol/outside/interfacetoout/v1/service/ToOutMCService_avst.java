package com.avst.meetingcontrol.outside.interfacetoout.v1.service;

import com.avst.meetingcontrol.common.conf.ASRType;
import com.avst.meetingcontrol.common.util.JacksonUtil;
import com.avst.meetingcontrol.common.util.ReadWriteFile;
import com.avst.meetingcontrol.common.util.baseaction.RRParam;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.bl.REMControl;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.GetAsrServerBySsidParam;
import com.avst.meetingcontrol.feignclient.ec.vo.GetAsrServerBySsidVO;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.cache.AVSTMCCache;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.InitMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.OverMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.StartMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndAsrParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndUserParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.v1.action.AvstMCImpl;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.InitMCVO;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.param.TDAndUserParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.AsrForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.MCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrTxtParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.MCCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.conf.MCOverThread;
import com.avst.meetingcontrol.outside.interfacetoout.req.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToOutMCService_avst implements BaseDealMCInterface {

    @Autowired
    private EquipmentControl equipmentControl;

    @Autowired
    private REMControl remControl;

    @Override
    public RResult startMC(ReqParam<StartMCParam_out> param, RResult result) {


        StartMCParam_out startMCParam_out=param.getParam();

        //实例化会议
        InitMCParam initMCParam=new InitMCParam();
        initMCParam.setMeetingtype(startMCParam_out.getMeetingtype());
        initMCParam.setModelbool(startMCParam_out.getModelbool());
        initMCParam.setMtmodelssid(startMCParam_out.getMtmodelssid());
        initMCParam.setYwSystemType(startMCParam_out.getYwSystemType());
        List<TdAndUserParam> tdAndUserList=new ArrayList<TdAndUserParam>();
        Gson gson = new Gson();
        List<TdAndUserAndOtherParam> tdlist=startMCParam_out.getTdList();
        if(null!=tdlist&&tdlist.size() > 0){

            for(TdAndUserAndOtherParam td:tdlist){
                TdAndUserParam tdAndUserParam=gson.fromJson(gson.toJson(td),TdAndUserParam.class);
                tdAndUserList.add(tdAndUserParam);
            }
            initMCParam.setTdAndUserList(tdAndUserList);
            RRParam<InitMCVO> rr= AvstMCImpl.initMC(initMCParam);
            if(null==rr||rr.getCode()!=1||null==rr.getT()){//实例化会议失败
                System.out.println("实例化会议失败---rr.getMessage():"+rr.getMessage()+"----startMCParam_out.getModelbool():"+startMCParam_out.getModelbool());
                result.setMessage("实例化会议失败");
                return result;
            }
            //开启会议
            String mtssid=rr.getT().getMtssid();//本次会议的ssid
            List<TDAndUserParam> rrtdlist=rr.getT().getTdlist();
            if(null==rrtdlist || rrtdlist.size() == 0){
                System.out.println(rrtdlist.size()+":rrtdlist.size() AvstMCImpl.initMC 设置会议人员绑定设备通道失败---");
                result.setMessage("设置会议人员绑定设备通道失败---");
                return result;
            }
            StartMCParam startMCParam=new StartMCParam();
            startMCParam.setModelbool(startMCParam_out.getModelbool());
            startMCParam.setMtmodelssid(startMCParam_out.getMtmodelssid());
            startMCParam.setMtssid(mtssid);//实例化返回的会议ssid
            List<TdAndAsrParam> tdAndAsrList=new ArrayList<TdAndAsrParam>();
            gson = new Gson();
            for(TdAndUserAndOtherParam td:tdlist){
                TdAndAsrParam tdAndAsrParam=gson.fromJson(gson.toJson(td),TdAndAsrParam.class);
                //还有5个参数没有设置
                for(TDAndUserParam p:rrtdlist){
                    if(p.getMtuserssid().equals(td.getUserssid())){//把init会议生成的会议人员通道ssid填进去
                        tdAndAsrParam.setMttduserssid(p.getMttduserssid());
                        tdAndAsrParam.setTdssid(p.getTdssid());
                        tdAndAsrParam.setUsepolygraph(p.getUsepolygraph());
                        tdAndAsrParam.setUseasr(p.getUseasr());
                        tdAndAsrParam.setPolygraphssid(p.getPolygraphssid());
                        tdAndAsrParam.setAsrssid(p.getAsrssid());
                        break;
                    }
                }
                tdAndAsrList.add(tdAndAsrParam);
            }
            startMCParam.setTdAserList(tdAndAsrList);
            RRParam<Boolean> rr2= AvstMCImpl.startMC(startMCParam);
            if(null!=rr2&&rr2.getCode()==1&&null!=rr.getT()&&rr2.getT().equals(true)){//开启会议成功
                result.changeToTrue(mtssid);//返回本次会议的ssid
            }
        }
        return result;
    }

    @Override
    public RResult overMC(ReqParam<OverMCParam_out> param, RResult result) {

        //关闭会议
        OverMCParam_out overMCParam_out=param.getParam();
        String mtssid=overMCParam_out.getMtssid();
        OverMCParam overparam=new OverMCParam();
        overparam.setMtssid(mtssid);
        RRParam<Boolean> rr= AvstMCImpl.overMC(overparam);;
        if(null!=rr&&rr.getCode()==1&&null!=rr.getT()&&rr.getT().equals(true)){//开启会议成功
            result.changeToTrue(true);//返回
        }

        //开启关闭的线程
        MCOverThread mcOverThread=new MCOverThread(mtssid);
        mcOverThread.start();
        return result;
    }

    @Override
    public boolean setMCAsrTxtBack(ReqParam<SetMCAsrTxtBackParam_out> param) {

        SetMCAsrTxtBackParam_out mcAsrTxtBackParam_out=param.getParam();
        String asrid=mcAsrTxtBackParam_out.getAsrid();
        String mtssid=AsrForMCCache.getMTssidByAsrid(asrid);
        if(null==mcAsrTxtBackParam_out.getAsrTxtParam_toout()){
            return false;
        }
        String userssid=MCCache.getUserSsidByAsrid(mtssid,asrid);

        AsrTxtParam_toout asrtxt= null;
        Gson gson=new Gson();
        asrtxt = gson.fromJson(gson.toJson(mcAsrTxtBackParam_out.getAsrTxtParam_toout()), AsrTxtParam_toout.class);

        if(null!=asrtxt){

            System.out.println(userssid+":userssid 运行中---");
            AsrForMCCache.runbool=false;
            asrtxt.setUserssid(userssid);
            AsrForMCCache.addAsrTxt(mtssid,asrid,asrtxt,userssid);
            AsrForMCCache.runbool=true;

            //向业务平台推送数据
            try {
                MCCacheParam mcCacheParam=MCCache.getMCCacheParam(mtssid);
                if(null!=mcCacheParam){
                    AsrTxtParam_toout asrTxtParam_toout=AsrForMCCache.getNewestAsrTxtBymtssid(mtssid);
                    ReqParam<AsrTxtParam_toout> pparam=new ReqParam<AsrTxtParam_toout>();
                    pparam.setParam(asrTxtParam_toout);
                    if(mcCacheParam.getYwSystemType().equals("TRM_AVST")){//avst版本的的笔录系统的类型
                        return remControl.setRercordAsrTxtBack(pparam);//调用对应的feign请求，返回TXT数据
                    }//以后还有其他的系统
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        return false;
    }



}
