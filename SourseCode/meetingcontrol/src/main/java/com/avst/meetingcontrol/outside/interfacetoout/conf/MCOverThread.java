package com.avst.meetingcontrol.outside.interfacetoout.conf;


import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_realtimrecord;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_realtimrecordMapper;
import com.avst.meetingcontrol.common.util.OpenUtil;
import com.avst.meetingcontrol.common.util.SpringUtil;
import com.avst.meetingcontrol.outside.interfacetoout.cache.AsrForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.MCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrForMCCache_oneParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrTxtParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.MCCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.TdAndUserAndOtherCacheParam;

import java.util.List;

/**
 * 当用户关闭asr识别的时候asr识别可能还有部分数据没有回传，所以需要延迟关闭数据
 */
public class MCOverThread<T> extends Thread{

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

        //最后关闭会议缓存
        MCCache.delMCCacheParam(mtssid);//最后关闭会议缓存


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
    //                            realtimrecord.setRecordtime();//暂时不知道
                                realtimrecord.setSsid(OpenUtil.getUUID_32());
                                realtimrecord.setOrdernum(sortnum);
                                realtimrecord.setMtssid(mtssid);
                                realtimrecord.setMtuserssid(oneUserAsr.getUserssid());
                                avstmt_realtimrecordMapper.insert(realtimrecord);
                                sortnum++;
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



    }
}
