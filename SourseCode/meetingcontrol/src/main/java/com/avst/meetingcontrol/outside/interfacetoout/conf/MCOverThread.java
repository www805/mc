package com.avst.meetingcontrol.outside.interfacetoout.conf;


import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_realtimrecord;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_realtimrecordMapper;
import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.OpenUtil;
import com.avst.meetingcontrol.common.util.SpringUtil;
import com.avst.meetingcontrol.outside.interfacetoout.cache.AsrForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.MCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.PhForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 当用户关闭asr识别的时候asr识别可能还有部分数据没有回传，所以需要延迟关闭数据
 */
public class MCOverThread extends Thread{

    private int heartbeatTime=20;//关闭等待时间S

    private String mtssid;//会议ssid


    public MCOverThread(String mtssid) {
        this.mtssid = mtssid;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //查看是否有asr
        //是否需要写入数据，比如asr识别的数据
        List<AsrForMCCache_oneParam> asrList=AsrForMCCache.getMTAsrAllUserAsrByMTSsid(mtssid);
        if(null!=asrList&&asrList.size() > 0){

            try{
                Avstmt_realtimrecordMapper avstmt_realtimrecordMapper=SpringUtil.getBean(Avstmt_realtimrecordMapper.class);
                for(AsrForMCCache_oneParam oneUserAsr:asrList){//读取每个人的会议记录新增到数据库
                    List<AsrTxtParam_toout> oneUserTxtList=oneUserAsr.getAsrTxtList();

                    if(null!=oneUserTxtList&&oneUserTxtList.size() > 0){
                        int sortnum=1;
                        for(AsrTxtParam_toout txt:oneUserTxtList){
                            try{
                                Avstmt_realtimrecord realtimrecord=new Avstmt_realtimrecord();
                                realtimrecord.setTranslatext(txt.getTxt());
                                realtimrecord.setStarttime(Long.parseLong(txt.getStarttime()));
                                realtimrecord.setSsid(OpenUtil.getUUID_32());
                                realtimrecord.setOrdernum(sortnum);
                                realtimrecord.setMtssid(mtssid);
                                realtimrecord.setMtuserssid(oneUserAsr.getUserssid());
                                realtimrecord.setCreatetime(new Date());
                                realtimrecord.setString1(txt.getAsrtime());//发送时间
                                Integer inser=avstmt_realtimrecordMapper.insert(realtimrecord);
                                if(null!=inser&&inser > -1){
                                    sortnum++;

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //关闭asr识别结果
        AsrForMCCache.delAsrForMCMap(mtssid);

        //查看是否有测谎，等等
        PhForMCCacheParam phForMCCacheParam=PhForMCCache.getMTPHByMTSsid(mtssid);
        if(null!=phForMCCacheParam){

            List<PhForMCCache_oneParam> phAlllist=phForMCCacheParam.getPhForMCCache_oneParamList();
            if(null!=phAlllist&&phAlllist.size() > 0){
                for(PhForMCCache_oneParam one : phAlllist){

                    //先停止获取测谎数据的线程
                    TdAndUserAndOtherCacheParam tdAndUserAndOtherCacheParam=MCCache.getMCCacheOneTDParamByPhssid(mtssid,one.getPhssid());
                    if(null!=tdAndUserAndOtherCacheParam){//说明这个用户有测谎仪
                        LogUtil.intoLog(3,MCOverThread.class,mtssid+"：mtssid 说明这个用户有测谎仪 one.getPhssid()："
                                +one.getPhssid()+"----tdAndUserAndOtherCacheParam.getUsepolygraph()："+tdAndUserAndOtherCacheParam.getUsepolygraph());
                        MC_PhThread mc_phThread=tdAndUserAndOtherCacheParam.getMc_phThread();
                        if(null==mc_phThread){
                            LogUtil.intoLog(3,MCOverThread.class,mtssid+"：mtssid 异常了，本来应该有的线程没有了tdAndUserAndOtherCacheParam.getMc_phThread()");
                        }
                        mc_phThread.bool=false;//中断掉这个线程
                    }else{
                        LogUtil.intoLog(3,MCOverThread.class,mtssid+"：mtssid 说明这个用户没有测谎仪 one.getPhssid()："+one.getPhssid());
                    }

                    //开始做插入测谎数据的操作
                    String iid=one.getIid();
                    MC_Ph_AddThread mc_ph_addThread=new MC_Ph_AddThread(one.getPhDataList(),mtssid,one.getPhssid(),iid,one.getAddcount(),one.getAddfilepath());
                    mc_ph_addThread.start();
                }
            }

            PhForMCCache.rvPhMap(mtssid);
            LogUtil.intoLog(4,MCOverThread.class,"测谎数据缓存已经清除--PhForMCCache.rvPhMap mtssid:"+mtssid);
        }

        //最后关闭会议缓存
        MCCache.delMCCacheParam(mtssid);//最后关闭会议缓存

        //会议结束后，开始做asr语音文字 TXT导出


    }
}
