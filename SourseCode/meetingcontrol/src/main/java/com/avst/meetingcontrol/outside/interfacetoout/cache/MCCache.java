package com.avst.meetingcontrol.outside.interfacetoout.cache;

import com.avst.meetingcontrol.outside.interfacetoout.cache.param.MCCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.TdAndUserAndOtherCacheParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议缓存，主控会议过程中的信息
 */
public class MCCache {

    private static List<MCCacheParam> mcList;//会议缓存list

    public static  synchronized  List<MCCacheParam> getMCList(){

        return mcList;
    }

    public static  synchronized  MCCacheParam getMCCacheParam(String mtssid){
        if(null!=mcList&&mcList.size() > 0){
            for(MCCacheParam mc:mcList){
                if(mc.getMtssid().equals(mtssid)){
                    return mc;
                }
            }
        }
        return null;
    }

    /**
     * 获取会议缓存中某一位用户的通道数据信息
     * @param mtssid
     * @param mtusertdssid
     * @return
     */
    public static  synchronized  TdAndUserAndOtherCacheParam getMCCacheOneTDParamByMTUserTDSsid(String mtssid,String mtusertdssid){
        MCCacheParam mcCacheParam=getMCCacheParam(mtssid);
        if(null!=mcCacheParam&&null!=mcCacheParam.getTdList()&&mcCacheParam.getTdList().size() > 0){
            for(TdAndUserAndOtherCacheParam cacheParam:mcCacheParam.getTdList()){
                if(null!=cacheParam.getMttduserssid()&&cacheParam.getMttduserssid().equals(mtusertdssid)){
                    return cacheParam;
                }
            }
        }
        return null;
    }

    /**
     * 获取会议缓存中某一位用户的通道数据信息
     * @param mtssid
     * @param asrid
     * @return
     */
    public static  synchronized  TdAndUserAndOtherCacheParam getMCCacheOneTDParamByAsrid(String mtssid,String asrid){
        MCCacheParam mcCacheParam=getMCCacheParam(mtssid);
        if(null!=mcCacheParam&&null!=mcCacheParam.getTdList()&&mcCacheParam.getTdList().size() > 0){
            for(TdAndUserAndOtherCacheParam cacheParam:mcCacheParam.getTdList()){
                if(null!=cacheParam.getAsrid()&&cacheParam.getAsrid().equals(asrid)){
                    return cacheParam;
                }
            }
        }
        return null;
    }

    /**
     * 获取会议缓存中某一位用户的通道数据信息
     * @param mtssid
     * @param userssid
     * @return
     */
    public static  synchronized  TdAndUserAndOtherCacheParam getMCCacheOneTDParamByUserssid(String mtssid,String userssid){
        MCCacheParam mcCacheParam=getMCCacheParam(mtssid);
        if(null!=mcCacheParam&&null!=mcCacheParam.getTdList()&&mcCacheParam.getTdList().size() > 0){
            for(TdAndUserAndOtherCacheParam cacheParam:mcCacheParam.getTdList()){
                if(null!=cacheParam.getUserssid()&&cacheParam.getUserssid().equals(userssid)){
                    return cacheParam;
                }
            }
        }
        return null;
    }

    /**
     * 获取会议缓存中某一位用户的ssid
     * @param mtssid
     * @param asrid
     * @return
     */
    public static  synchronized  String getUserSsidByAsrid(String mtssid,String asrid){
        MCCacheParam mcCacheParam=getMCCacheParam(mtssid);
        if(null!=mcCacheParam&&null!=mcCacheParam.getTdList()&&mcCacheParam.getTdList().size() > 0){
            for(TdAndUserAndOtherCacheParam cacheParam:mcCacheParam.getTdList()){
                if(null!=cacheParam.getAsrid()&&cacheParam.getAsrid().equals(asrid)){
                    return cacheParam.getUserssid();
                }
            }
        }
        return null;
    }


    public static  synchronized  boolean setMCCacheParam(MCCacheParam mcCacheParam){

        if(null==mcList){
            mcList=new ArrayList<MCCacheParam>();
        }else{
            if(mcList.size()> 0){
                int i=0;
                for(MCCacheParam mc:mcList){
                    if(null!=mc.getMtssid()&&mc.getMtssid().equals(mcCacheParam.getMtssid())){
                        mcList.remove(i);
                        break;
                    }
                    i++;
                }
            }
        }
        mcList.add(mcCacheParam);
        return true;
    }

    /**
     * 更新会议缓存中某一位用户的通道数据信息
     * @param mtssid
     * @param param
     * @return
     */
    public static  synchronized  boolean setMCTDCacheParam(String mtssid, TdAndUserAndOtherCacheParam param){

        MCCacheParam mcCacheParam=getMCCacheParam(mtssid);
        if(null==mcCacheParam){
            mcCacheParam=new MCCacheParam();
        }
        List<TdAndUserAndOtherCacheParam> tdList=mcCacheParam.getTdList();
        if(null==tdList){
            tdList=new ArrayList<TdAndUserAndOtherCacheParam>();
        }else{
            if(tdList.size() > 0){

                int i=0;
                for(TdAndUserAndOtherCacheParam cache:tdList){
                    if(cache.getMttduserssid().equals(param.getMttduserssid())){
                        tdList.remove(i);
                        break ;
                    }
                    i++;
                }
            }
        }
        tdList.add(param);
        mcCacheParam.setTdList(tdList);
        setMCCacheParam(mcCacheParam);
        return true;
    }

    /**
     * 更新会议状态
     * @param mtssid
     * @return
     */
    public static  synchronized  boolean setMCCacheParam(String mtssid, int bool){
        if(null==mcList){
            mcList=new ArrayList<MCCacheParam>();
        }else{
            if(mcList.size()> 0){
                int i=0;
                for(MCCacheParam mc:mcList){
                    if(null!=mc.getMtssid()&&mc.getMtssid().equals(mtssid)){
                        mcList.get(i).setMtstate(bool);
                        break;
                    }
                    i++;
                }
            }
        }
        return true;
    }


    public static  synchronized  boolean delMCCacheParam(String mtssid){

        if(null!=mcList&&mcList.size() > 0){
            int i=0;
            for(MCCacheParam mc:mcList){
                if(mc.getMtssid().equals(mtssid)){
                    mcList.remove(i);
                    return true;
                }
                i++;
            }
        }
        return false;
    }

}
