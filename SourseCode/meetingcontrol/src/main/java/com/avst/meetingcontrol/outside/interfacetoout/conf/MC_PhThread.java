package com.avst.meetingcontrol.outside.interfacetoout.conf;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.ph.GetPolygraphAnalysisParam;
import com.avst.meetingcontrol.feignclient.ec.vo.ph.GetPolygraphAnalysisVO;
import com.avst.meetingcontrol.outside.interfacetoout.cache.AsrForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.MCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.PhForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrTxtParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.MCCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.PhDataParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.req.TxtBackParam;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

/**
 * 会议中关于测谎数据的保存问题
 */
public class MC_PhThread extends Thread{

    public boolean bool=true;//用于中断控制

    private String phssid;//测谎仪ssid
    private String userssid;//会议中的参会人员ssid

    private String phtype;//测谎仪类型

    private EquipmentControl equipmentControl;

    private String mtssid;//会议ssid

    private long starttime;//读取测谎开始时间

    public MC_PhThread(String phssid, String userssid, EquipmentControl equipmentControl_, String mtssid_,String phtype_,long starttime_){
        this.phssid=phssid;
        this.userssid=userssid;
        equipmentControl=equipmentControl_;
        mtssid=mtssid_;
        phtype=phtype_;
        starttime=starttime_;
    }

    @Override
    public void run() {

        while (bool){

            //请求测谎仪服务获取数据
            LogUtil.intoLog(this.getClass(),phssid+"------phssid  userssid:"+userssid);
            try {
                MCCacheParam mc=MCCache.getMCCacheParam(mtssid);
                if(null==mc){
                    LogUtil.intoLog(this.getClass(),phssid+"---会议缓存为找到直接退出  mtssid:"+mtssid);
                    return ;
                }
                if(mc.getMtstate()!=1){
                    LogUtil.intoLog(3,this.getClass(),phssid+"----会议状态不是进行中不需要去获取--phssid  mc.getMtstate():"+mc.getMtstate());
                }else{

                    ReqParam<GetPolygraphAnalysisParam> param=new ReqParam<GetPolygraphAnalysisParam>();
                    GetPolygraphAnalysisParam getPolygraphAnalysisParam=new GetPolygraphAnalysisParam();
                    getPolygraphAnalysisParam.setPhType(phtype);
                    getPolygraphAnalysisParam.setPolygraphssid(phssid);
                    param.setParam(getPolygraphAnalysisParam);

                    long reqtime= DateUtil.getSeconds();//请求时间()
                    RResult<GetPolygraphAnalysisVO> rr= equipmentControl.getPolygraphAnalysis(param);
                    if(null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
                        try {
                            GetPolygraphAnalysisVO vo=rr.getData();
                            if(null!=vo&&null!=vo.getT()){
                                PhDataParam_toout phDataParam_toout=new PhDataParam_toout();
                                phDataParam_toout.setReqTime((reqtime-starttime)/1000);//这是一个秒数，
                                phDataParam_toout.setT(vo.getT());
                                PhForMCCache.addPhDataParam_toout2Cache(phDataParam_toout,mtssid,phssid,userssid);
                            }else{
                                LogUtil.intoLog(this.getClass(),"MC_PhThread equipmentControl.getPolygraphAnalysis 异常 rr.getData() is null："+rr.getData());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else{
                        LogUtil.intoLog(this.getClass(),"MC_PhThread equipmentControl.getPolygraphAnalysis 异常 rr.getMessage()："+rr.getMessage()+"---phssid:"+phssid);
                    }

                    if(!bool){
                        break;
                    }
                }

                try {
                    Thread.sleep(1000);//1s请求一次测谎仪
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!bool){
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        LogUtil.intoLog(this.getClass(),"MC_PhThread 出来了--phssid:"+phssid+"--userssid:"+userssid+"--"+new Date());
    }
}
