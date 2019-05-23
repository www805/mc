package com.avst.meetingcontrol.outside.interfacetoout.cache;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_realtimrecord;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_realtimrecordMapper;
import com.avst.meetingcontrol.common.util.SpringUtil;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.GetMCCacheParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议实时数据
 */
public class GetMCCache {
    private static List<GetMCCacheParam> getMCList=null;//全部对话

    public static synchronized List<GetMCCacheParam> getMCs() {
            if (null==getMCList){
                initgetMC();
            }
            if (null!=getMCList&&getMCList.size()>0){
                return  getMCList;
            }
            return  null;
    }

    public static synchronized List<GetMCCacheParam> getMCByMtssid(String mtssid) {
        if (null==getMCList){
            initgetMC();
        }
        if(null!=getMCList&&getMCList.size() > 0){
            List<GetMCCacheParam> list=new ArrayList<GetMCCacheParam>();
            for(GetMCCacheParam param:getMCList){
                String mtssid_=param.getMtssid();
                if(null!=mtssid_&&null!=mtssid&&mtssid_.equals(mtssid)){
                    list.add(param);
                }
            }
            return list;
        }
        return null;
    }

    public static synchronized boolean initgetMC(){
        Avstmt_realtimrecordMapper avstmt_realtimrecordMapper = SpringUtil.getBean(Avstmt_realtimrecordMapper.class);
        EntityWrapper ew=new EntityWrapper();
        ew.orderBy("ordernum",true);
        List<Avstmt_realtimrecord>  list = avstmt_realtimrecordMapper.selectList(ew);
        if (null!=list&&list.size()>0){
            getMCList=new ArrayList<GetMCCacheParam>();
            for (Avstmt_realtimrecord a : list) {
                GetMCCacheParam param=new GetMCCacheParam();
                param.setMtssid(a.getMtssid());
                param.setStarttime(a.getStarttime().toString());
                param.setAsrsort(a.getOrdernum());
                param.setTxt(a.getTranslatext());
                param.setUserssid(a.getMtuserssid());
                param.setAsrtime(a.getString1());//时间
                getMCList.add(param);
            }
            return  true;
        }
        return  false;
    }

}
