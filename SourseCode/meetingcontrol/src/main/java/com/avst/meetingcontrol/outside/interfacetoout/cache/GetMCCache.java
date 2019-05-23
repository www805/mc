package com.avst.meetingcontrol.outside.interfacetoout.cache;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_realtimrecord;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_realtimrecordMapper;
import com.avst.meetingcontrol.common.util.SpringUtil;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrTxtParam_toout;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议实时数据
 */
public class GetMCCache {
    private static Map<String, List<AsrTxtParam_toout>> getMCList=null;//本次会议的所有人员的asr识别结果，key会议ssid

    private static String lastmtssid=null;

    public static synchronized List<AsrTxtParam_toout> getMCByMtssid(String mtssid) {
        if (null==getMCList||getMCList.isEmpty()){
            initgetMC(mtssid);
        }

        if (null!=lastmtssid&&lastmtssid!=mtssid){
            initgetMC(mtssid);
        }

        if(null!=getMCList&&getMCList.containsKey(mtssid)){
            return getMCList.get(mtssid);
        }
        return null;
    }



    public static synchronized boolean initgetMC(String mtssid){
        lastmtssid=mtssid;

        Avstmt_realtimrecordMapper avstmt_realtimrecordMapper = SpringUtil.getBean(Avstmt_realtimrecordMapper.class);
        EntityWrapper ew=new EntityWrapper();
        ew.orderBy("ordernum",true);
        ew.eq("mtssid",mtssid);
        List<Avstmt_realtimrecord>  list = avstmt_realtimrecordMapper.selectList(ew);
        if (null!=list&&list.size()>0){
            getMCList=new HashMap<String,  List<AsrTxtParam_toout>>();
            List<AsrTxtParam_toout> l=new ArrayList<AsrTxtParam_toout>();
            for (Avstmt_realtimrecord a : list) {
                AsrTxtParam_toout param=new AsrTxtParam_toout();
                param.setStarttime(a.getStarttime().toString());
                param.setAsrsort(a.getOrdernum());
                param.setTxt(a.getTranslatext());
                param.setUserssid(a.getMtuserssid());
                param.setAsrtime(a.getString1());//时间
                    l.add(param);
            }
            if (null!=l&&l.size()>0){
                getMCList.put(mtssid,l);
            }
            return  true;
        }
        return  false;
    }

}
