package com.avst.meetingcontrol.outside.interfacetoout.cache;

import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrForMCCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrForMCCache_oneParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrTxtParam_toout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本场会议的语音识别缓存
 * 缓存本场会议所有的
 */
public class AsrForMCCache {

    private static Map<String, AsrForMCCacheParam> asrMap=new HashMap<String, AsrForMCCacheParam>();//本次会议的所有人员的asr识别结果，key会议ssid

    private static Map<String,String> asrToMTMap=null;//asrid语音识别的唯一识别码对应的会议ssid

    public static  AsrForMCCacheParam getMTAsrByMTSsid(String mtssid){

        if(null!=asrMap&&asrMap.containsKey(mtssid)){
            return asrMap.get(mtssid);
        }
        return null;
    };

    public static  List<AsrForMCCache_oneParam> getMTAsrAllUserAsrByMTSsid(String mtssid){

        AsrForMCCacheParam asrForMCCacheParam=getMTAsrByMTSsid(mtssid);
        if(null!=asrForMCCacheParam){
            return asrForMCCacheParam.getAsrForMCCache_oneParamList();
        }
        return null;
    };

    /**
     * 获取会议中会议的单个会议用户的语音识别信息，asrid语音识别的唯一标识
     * @param mtssid
     * @param asrid
     * @return
     */
    public static synchronized AsrForMCCache_oneParam getMTAsrOneUserAsrByAsrid(String mtssid,String asrid){

        List<AsrForMCCache_oneParam> list=getMTAsrAllUserAsrByMTSsid(mtssid);
        if(null!=list&&list.size() > 0){
            for(AsrForMCCache_oneParam asr:list){
                System.out.println(asr.getAsrid()+":asr.getAsrid()---"+asrid);

                if(asr.getAsrid().equals(asrid)){
                    return asr;
                }
            }
        }
        return null;
    };

    /**
     * 获取会议中会议的单个会议用户的语音识别信息，
     * @param mtssid
     * @param userssid
     * @return
     */
    public static synchronized AsrForMCCache_oneParam getMTAsrOneUserAsrByUserid(String mtssid,String userssid){

        List<AsrForMCCache_oneParam> list=getMTAsrAllUserAsrByMTSsid(mtssid);
        if(null!=list&&list.size() > 0){
            for(AsrForMCCache_oneParam asr:list){
                System.out.println(asr.getUserssid()+":asr.getuserssid()---"+userssid);

                if(asr.getUserssid().equals(userssid)){
                    return asr;
                }
            }
        }
        return null;
    };

    //最新的一次语音识别的asrid，一次会议可能有多个角色，最后一次说话肯定只有一个，这里就是用来标记会议的最后一次发言的人
    private static Map<String,String> newestAsridMap;
    /**
     * 获取该会议中最新的一条语音识别
     * @param mtssid
     * @return
     */
    public static synchronized AsrTxtParam_toout getNewestAsrTxtBymtssid(String mtssid){

         //先找到是哪一个人的，或者说是哪一个asrid是有最新识别信息
        String newestAsrid=newestAsridMap.get(mtssid);
        if(null==newestAsrid){
            System.out.println("没有找到最新的语音识别的asrid---重大问题");
            return null;
        }
        AsrForMCCache_oneParam asrForMCCache_oneParam=getMTAsrOneUserAsrByAsrid(mtssid,newestAsrid);
        if(null!=asrForMCCache_oneParam){
            List<AsrTxtParam_toout> asrtxtList=asrForMCCache_oneParam.getAsrTxtList();
            if(null!=asrtxtList&&asrtxtList.size() > 0){
                return asrtxtList.get(asrtxtList.size()-1);//返回最后一条
            }
        }
        return null;
    };


    public static synchronized AsrTxtParam_toout getNewestAsrTxtByAsrid(String mtssid,String asrid){

        AsrForMCCache_oneParam asrForMCCache_oneParam=getMTAsrOneUserAsrByAsrid(mtssid,asrid);
        if(null!=asrForMCCache_oneParam){
            List<AsrTxtParam_toout> asrtxtList=asrForMCCache_oneParam.getAsrTxtList();
            if(null!=asrtxtList&&asrtxtList.size() > 0){
                return asrtxtList.get(asrtxtList.size()-1);//返回最后一条
            }
        }
        return null;
    };

    /**
     * 缓存该会议的语音识别结果
     * @param mtssid
     * @param asrForMCCacheParam
     * @return
     */
    public static  synchronized boolean setAsrForMCCache(String mtssid,AsrForMCCacheParam asrForMCCacheParam){

        if(null==asrMap){
            asrMap=new HashMap<String,AsrForMCCacheParam>();
        }
        asrMap.put(mtssid,asrForMCCacheParam);
        return false;
    }

    /**
     * 缓存该会议的所有人的语音识别结果
     * @param mtssid
     * @param asrlist
     * @return
     */
    public static synchronized  boolean setMTAsrAllUserAsr(String mtssid,List<AsrForMCCache_oneParam> asrlist){

        AsrForMCCacheParam asrForMCCacheParam=getMTAsrByMTSsid(mtssid);
        if(null==asrForMCCacheParam){
            asrForMCCacheParam=new AsrForMCCacheParam();
        }
        asrForMCCacheParam.setMtssid(mtssid);
        asrForMCCacheParam.setAsrForMCCache_oneParamList(asrlist);
        setAsrForMCCache(mtssid,asrForMCCacheParam);
        return true;
    }


    /**
     * 缓存一个人的语音识别结果
     * @param mtssid
     * @param oneasr
     * @return
     */
    public static synchronized  boolean setMTAsrOneUserAsr(String mtssid,AsrForMCCache_oneParam oneasr){

        System.out.println(oneasr.getAsrid()+"-----");

        List<AsrForMCCache_oneParam> list=getMTAsrAllUserAsrByMTSsid(mtssid);
        if(null==list){
            list=new ArrayList<AsrForMCCache_oneParam>();
        }else{
            if(list.size() > 0){
                int i=0;
                for(AsrForMCCache_oneParam one:list){
                    System.out.println(one.getUserssid()+"---one.getUserssid()---oneasr.getUserssid()-"+oneasr.getUserssid());
                    if(null!=one.getUserssid()&&one.getUserssid().equals(oneasr.getUserssid())){
                        list.remove(i);
                        break;
                    }
                    i++;
                }
            }
        }
        list.add(oneasr);
        setMTAsrAllUserAsr(mtssid,list);

        //修改当前会议最新的一次的语音识别asrid
        if(null==newestAsridMap){
            newestAsridMap=new HashMap<String,String>();
        }
        newestAsridMap.put(mtssid,oneasr.getAsrid());

        return true;
    }

public static boolean runbool=true;





    /**
     * 添加一条识别结果到缓存（可能这一句已经有识别，直接替换）
     * @param mtssid
     * @param asrid
     * @param asrtxt
     * @param userssid
     * @return
     */
    public static synchronized boolean addAsrTxt(String mtssid,String asrid,AsrTxtParam_toout asrtxt,String userssid){


        AsrForMCCache_oneParam asrForMCCache_oneParam=getMTAsrOneUserAsrByUserid(mtssid,userssid);
        AsrForMCCache_oneParam asrForMCCache_oneParam2=new AsrForMCCache_oneParam();//这个对象就是用来验证的，后期如果测试没有问题去掉
        if(null==asrForMCCache_oneParam){
            asrForMCCache_oneParam=null;
            asrForMCCache_oneParam2.setAsrid(asrid);
            asrForMCCache_oneParam2.setUserssid(userssid);
        }else{
            asrForMCCache_oneParam2=asrForMCCache_oneParam;
        }

         List<AsrTxtParam_toout> txtList=asrForMCCache_oneParam2.getAsrTxtList();
        if(null==txtList){
            txtList=new ArrayList<AsrTxtParam_toout>();
        }else{
            if(txtList.size() > 0){

                //只需要比较最后一句,asr识别是会一直往后加的，只有最后一句话才会有可能还在识别中
                AsrTxtParam_toout txt=txtList.get(txtList.size()-1);
                System.out.println(txt.getStarttime()+"--"+asrtxt.getStarttime()+"---"+txtList.size());
                if(txt.getStarttime().hashCode() == asrtxt.getStarttime().hashCode()){//比较这句话开始识别的时间，一样的话就还是这句话的识别直接覆盖
                    txtList.remove(txtList.size()-1);
                    System.out.println(txtList.size()-1+"-txtList.remove--"+txtList.size());

                }

//                for(int i=txtList.size()-1;i>=0;i--){
//                    //只需要比较最后一句,asr识别是会一直往后加的，只有最后一句话才会有可能还在识别中
//                    AsrTxtParam_toout txt=txtList.get(i);
//                    System.out.println(txt.getStarttime()+"--"+asrtxt.getStarttime()+"---"+txtList.size());
//                    if(txt.getStarttime().hashCode() == asrtxt.getStarttime().hashCode()){//比较这句话开始识别的时间，一样的话就还是这句话的识别直接覆盖
//                        txtList.remove(i);
//                        System.out.println(i+"-txtList.remove--"+txtList.size());
//                        break;
//                    }
//                }
            }
        }

        txtList.add(asrtxt);
        asrForMCCache_oneParam2.setAsrTxtList(txtList);
        setMTAsrOneUserAsr(mtssid,asrForMCCache_oneParam2);

        return true;
    }

    /**
     * 删除map中的本次会议的所有语言识别结果
     * @param mtssid
     * @return
     */
    public static synchronized boolean delAsrForMCMap(String mtssid){

        if(null!=asrMap&&asrMap.containsKey(mtssid)){

            List<AsrForMCCache_oneParam> asrForMCCache_oneParams=getMTAsrAllUserAsrByMTSsid(mtssid);
            if(null!=asrForMCCache_oneParams&&asrForMCCache_oneParams.size() > 0){
                for(AsrForMCCache_oneParam asr:asrForMCCache_oneParams){
                    String asrid=asr.getAsrid();
                    if(null!=asrToMTMap&&asrToMTMap.containsKey(asrid)){
                        asrToMTMap.remove(asrid);
                    }
                }
            }

            asrMap.remove(mtssid);

            //最新的一次语音识别的asrid
            if(null!=newestAsridMap&&newestAsridMap.containsKey(mtssid)){
                newestAsridMap.remove(mtssid);
            }

            return true;
        }
        return false;
    }

    public static synchronized String getMTssidByAsrid(String asrid){
        if(null!=asrToMTMap&&asrToMTMap.containsKey(asrid)){
            return asrToMTMap.get(asrid);
        }
        return null;
    }

    public static synchronized boolean setMTssidByAsrid(String asrid,String mtssid){
        if(null==asrToMTMap){
            asrToMTMap=new HashMap<String,String>();
        }
        asrToMTMap.put(asrid,mtssid);
        return true;
    }


}
