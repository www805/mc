package com.avst.meetingcontrol.outside.interfacetoout.cache;

import com.avst.meetingcontrol.common.conf.MCType;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.*;
import com.avst.meetingcontrol.outside.interfacetoout.conf.MC_Ph_AddThread;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本场会议的测谎仪缓存
 * 缓存本场会议中使用的测谎仪数据
 * 关联的KEY用mtssid
 */
public class PhForMCCache {

    private static Map<String, PhForMCCacheParam> phMap=new HashMap<String, PhForMCCacheParam>();//本次会议的所有人员的ph识别结果，key会议ssid


    /**
     * 获取该会议的所有用户的测谎数据信息
     * @param mtssid
     * @return
     */
    public static synchronized PhForMCCacheParam getMTPHByMTSsid(String mtssid){

        if(null!=phMap&&phMap.containsKey(mtssid)){
            return phMap.get(mtssid);
        }
        return null;
    };

    /**
     * 获取该会议的某一个用户的测谎数据信息
     * @param mtssid
     * @return
     */
    public static synchronized PhForMCCache_oneParam getMTOneUserPhDataByUserSsid(String mtssid,String userssid){

        PhForMCCacheParam phForMCCacheParam=getMTPHByMTSsid(mtssid);
        if(null!=phForMCCacheParam){
            List<PhForMCCache_oneParam> list=phForMCCacheParam.getPhForMCCache_oneParamList();
            if(null!=list&&list.size() > 0){
                for(PhForMCCache_oneParam param:list){
                    if(userssid.equals(param.getUserssid())){
                        return param;
                    }
                }
            }
        }
        return null;
    };

    /**
     * 获取该会议的某一个用户的测谎数据信息
     * @param mtssid
     * @return
     */
    public static synchronized PhForMCCache_oneParam getMTOneUserPhDataByPhSsid(String mtssid,String phssid){

        PhForMCCacheParam phForMCCacheParam=getMTPHByMTSsid(mtssid);
        if(null!=phForMCCacheParam){
            List<PhForMCCache_oneParam> list=phForMCCacheParam.getPhForMCCache_oneParamList();
            if(null!=list&&list.size() > 0){
                for(PhForMCCache_oneParam param:list){
                    if(phssid.equals(param.getPhssid())){
                        return param;
                    }
                }
            }
        }
        return null;
    };

    /**
     * 获取该会议的某一个用户的最后一条测谎数据信息
     * @param mtssid
     * @return
     */
    public static synchronized PhDataParam_toout getMTOneUserPhDataByPhSsid_lastoneData(String mtssid,String phssid){

        PhForMCCacheParam phForMCCacheParam=getMTPHByMTSsid(mtssid);
        if(null!=phForMCCacheParam){
            List<PhForMCCache_oneParam> list=phForMCCacheParam.getPhForMCCache_oneParamList();
            if(null!=list&&list.size() > 0){
                for(PhForMCCache_oneParam param:list){
                    if(phssid.equals(param.getPhssid())){
                        List<PhDataParam_toout> datalist=param.getPhDataList();
                        if(null!=datalist&&datalist.size() > 0){
                            return datalist.get(datalist.size()-1);
                        }
                    }
                }
            }
        }
        return null;
    };


    /**
     * 获取该会议的某一个用户的最后一条测谎数据信息
     * @param mtssid
     * @return
     */
    public static synchronized PhDataParam_toout getMTOneUserPhDataByUserSsid_lastoneData(String mtssid, String userssid){

        PhForMCCacheParam phForMCCacheParam=getMTPHByMTSsid(mtssid);
        if(null!=phForMCCacheParam){
            List<PhForMCCache_oneParam> list=phForMCCacheParam.getPhForMCCache_oneParamList();
            if(null!=list&&list.size() > 0){
                for(PhForMCCache_oneParam param:list){
                    if(userssid.equals(param.getUserssid())){
                        List<PhDataParam_toout> datalist=param.getPhDataList();
                        if(null!=datalist&&datalist.size() > 0){
                            return datalist.get(datalist.size()-1);
                        }
                    }
                }
            }
        }
        return null;
    };

    private static int maxHCPhDataNum=100;// 测谎数据最大缓存数


    /**
     * 往缓存中添加数据
     */
    public static synchronized void addPhDataParam_toout2Cache(PhDataParam_toout phDataParam_toout,String mtssid,String phssid,String userssid){

        if(null==phDataParam_toout){
            return ;
        }
        PhForMCCache_oneParam phForMCCache_oneParam=null;
        if(StringUtils.isNotEmpty(phssid)){
            phForMCCache_oneParam=getMTOneUserPhDataByPhSsid(mtssid,phssid);
        }else{
            phForMCCache_oneParam=getMTOneUserPhDataByUserSsid(mtssid,userssid);
        }
        if(null==phForMCCache_oneParam){
            phForMCCache_oneParam=new PhForMCCache_oneParam();
            phForMCCache_oneParam.setUserssid(userssid);
            phForMCCache_oneParam.setPhssid(phssid);
        }
        List<PhDataParam_toout> phDataParam_tooutList=phForMCCache_oneParam.getPhDataList();
        if(null==phDataParam_tooutList){
            phDataParam_tooutList=new ArrayList<PhDataParam_toout>();
        }else{
//            System.out.println(phDataParam_tooutList.size()+":phDataParam_tooutList.size()---maxHCPhDataNum:"+maxHCPhDataNum);
            if(phDataParam_tooutList.size() >=maxHCPhDataNum){//达到临界值了
               final List<PhDataParam_toout> phDataParam_tooutList_add=phDataParam_tooutList;

               //开始做插入测谎数据的操作
                String iid=phForMCCache_oneParam.getIid();
                MC_Ph_AddThread mc_ph_addThread=new MC_Ph_AddThread(phDataParam_tooutList_add,mtssid,phssid,iid,phForMCCache_oneParam.getAddcount(),phForMCCache_oneParam.getAddfilepath());
                mc_ph_addThread.start();

                phDataParam_tooutList=new ArrayList<PhDataParam_toout>();//清空
            }

        }
        phDataParam_tooutList.add(phDataParam_toout);

        setPhDataParam_tooutList(phDataParam_tooutList,mtssid,phssid,userssid);

    }
    /**
    * 修改缓存
     */
    public static synchronized void setPhDataParam_tooutList(List<PhDataParam_toout> phDataParam_tooutList,String mtssid,String phssid,String userssid){

        if(null==phDataParam_tooutList||phDataParam_tooutList.size()==0){
            return ;
        }
        PhForMCCache_oneParam phone=null;
        if(StringUtils.isNotEmpty(phssid)){
            phone=getMTOneUserPhDataByPhSsid(mtssid,phssid);
        }else{
            phone=getMTOneUserPhDataByPhSsid(mtssid,userssid);
        }

        if(null==phone){
            phone=new PhForMCCache_oneParam();
            phone.setPhDataList(phDataParam_tooutList);
            phone.setPhssid(phssid);
            phone.setUserssid(userssid);
        }else{
            if(StringUtils.isNotEmpty(phssid)){
                if(phssid.equals(phone.getPhssid())){
                    phone.setPhDataList(phDataParam_tooutList);
                }
            }else{
                if(userssid.equals(phone.getUserssid())){
                    phone.setPhDataList(phDataParam_tooutList);
                }
            }

        }
        setPhForMCCache_oneParam(phone,mtssid);
    }

    /**
     * 新增次数在原先的基础上加1
     * @param caddcount 当前的新增次数
     */
    public static synchronized void updateAddcount(int caddcount,String mtssid,String phssid,String filepath){

        caddcount++;
        PhForMCCache_oneParam phForMCCache_oneParam=getMTOneUserPhDataByPhSsid(mtssid,phssid);
        if(null!=phForMCCache_oneParam){
            phForMCCache_oneParam.setAddcount(caddcount);
            if(StringUtils.isNotEmpty(filepath)){
                phForMCCache_oneParam.setAddfilepath(filepath);
            }
            setPhForMCCache_oneParam(phForMCCache_oneParam,mtssid);
        }else{
            System.out.println("可能该会议已经结束了,mtssid:"+mtssid);
        }
    }


    public static  synchronized  void setPhForMCCache_oneParam(PhForMCCache_oneParam phone,String mtssid ){

        PhForMCCacheParam phForMCCacheParam=getMTPHByMTSsid(mtssid);

        if(null==phForMCCacheParam){
            phForMCCacheParam=new PhForMCCacheParam();
            phForMCCacheParam.setMctype(MCType.AVST.toString());//默认avst会议
            phForMCCacheParam.setMtssid(mtssid);
            phForMCCacheParam.setMttype(2);
            List<PhForMCCache_oneParam> phForMCCache_oneParams=new ArrayList<PhForMCCache_oneParam>();
            phForMCCache_oneParams.add(phone);
            phForMCCacheParam.setPhForMCCache_oneParamList(phForMCCache_oneParams);
        }else{
            List<PhForMCCache_oneParam> phForMCCache_oneParams = phForMCCacheParam.getPhForMCCache_oneParamList();
            if(null==phForMCCache_oneParams){
                phForMCCache_oneParams=new ArrayList<PhForMCCache_oneParam>();
            }
            boolean bool_add=true;//是否需要新增
            for(PhForMCCache_oneParam ph:phForMCCache_oneParams){
                if(null!=ph.getUserssid()){
                    if(ph.getUserssid().equals(phone.getUserssid())){
                        ph=phone;
                        bool_add=false;
                    }
                }else{
                    if(ph.getPhssid().equals(phone.getPhssid())){
                        ph=phone;
                        bool_add=false;
                    }
                }
            }
            if(bool_add){
                phForMCCache_oneParams.add(phone);
            }
            phForMCCacheParam.setPhForMCCache_oneParamList(phForMCCache_oneParams);
        }
        phMap.put(mtssid,phForMCCacheParam);
    }

    public static synchronized boolean rvPhMap(String mtssid){

        if(null!=phMap&&phMap.containsKey(mtssid)){
            phMap.remove(mtssid);
        }
        return true;
    }

}
