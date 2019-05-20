package com.avst.meetingcontrol.outside.interfacetoout.conf;

import com.avst.meetingcontrol.common.util.JacksonUtil;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.feignclient.EquipmentControl;
import com.avst.meetingcontrol.outside.interfacetoout.cache.AsrForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.MCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrTxtParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.TdAndUserAndOtherCacheParam;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Date;
import java.util.List;

/**
 * 会议中关于语音识别线程
 */
public class MC_AsrThread extends Thread{

    public boolean bool=true;//用于中断控制

    private String asrid;//语音识别唯一标识
    private String userssid;//会议中的参会人员ssid

    private EquipmentControl equipmentControl;

    private String mtssid;//会议ssid

    public MC_AsrThread(String asrid,String userssid,EquipmentControl equipmentControl_,String mtssid_){
        this.asrid=asrid;
        this.userssid=userssid;
        equipmentControl=equipmentControl_;
        mtssid=mtssid_;
    }

    @Override
    public void run() {

        while (bool){


            //请求asr服务获取数据
            System.out.println(asrid+"------asrid  userssid:"+userssid);
            try {
                RResult<com.avst.meetingcontrol.feignclient.vo.AsrTxtParam_toout> rr= equipmentControl.txtBack(asrid);
                if(null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
                    AsrTxtParam_toout asrtxt= null;
                    try {
                        Gson gson=new Gson();
                        asrtxt = gson.fromJson(gson.toJson(rr.getData()), AsrTxtParam_toout.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(null!=asrtxt){
                        if(AsrForMCCache.runbool){
                            System.out.println(userssid+":userssid 运行中---");
                            AsrForMCCache.runbool=false;
                            AsrForMCCache.addAsrTxt(mtssid,asrid,asrtxt,userssid);
                            AsrForMCCache.runbool=true;
                        }else{
                            System.out.println(userssid+":userssid 稍等---");
                        }
    //                        System.out.println("----"+JacksonUtil.objebtToString(AsrForMCCache.getNewestAsrTxtByasrid(mtssid)));
                    }else{
                        System.out.println("MC_AsrThread equipmentControl.txtBack 异常 rr.getData() is null："+rr.getData());
                    }
                }else{
                    System.out.println("MC_AsrThread equipmentControl.txtBack 异常 rr.getMessage()："+rr.getMessage()+"---asrid:"+asrid);
                }

                if(!bool){
                    break;
                }
                try {
                    Thread.sleep(1000);
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
        System.out.println("MC_AsrThread 出来了--asrid:"+asrid+"--userssid:"+userssid+"--"+new Date());
    }
}
