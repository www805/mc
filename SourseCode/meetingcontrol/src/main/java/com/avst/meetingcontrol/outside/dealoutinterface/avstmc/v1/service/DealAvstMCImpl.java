package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.v1.service;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_asrtd;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_model;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_modeltd;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_tduser;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.*;
import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_mtinfo;
import com.avst.meetingcontrol.common.datasourse.publicsourse.mapper.Base_mtinfoMapper;
import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.OpenUtil;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RRParam;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.EquipmentControl;
import com.avst.meetingcontrol.feignclient.req.*;
import com.avst.meetingcontrol.feignclient.vo.GetAsrServerBySsidVO;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.InitMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.OverMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.StartMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndAsrParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndUserParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.InitMCVO;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.param.TDAndUserParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.MCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.MCCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.TdAndUserAndOtherCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.conf.MC_AsrThread;
import com.avst.meetingcontrol.outside.interfacetoout.req.TdAndUserAndOtherParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理avst会议系统的接口请求
 */
@Service
public class DealAvstMCImpl {

    @Autowired
    private Base_mtinfoMapper base_mtinfoMapper;

    @Autowired
    private Avstmt_asrtdMapper avstmt_asrtdMapper;

    @Autowired
    private Avstmt_modelMapper avstmt_modelMapper;

    @Autowired
    private Avstmt_modeltdMapper avstmt_modeltdMapper;

    @Autowired
    private Avstmt_tdpolygraphMapper avstmt_tdpolygraphMapper;

    @Autowired
    private Avstmt_tduserMapper avstmt_tduserMapper;

    @Autowired
    private EquipmentControl equipmentControl;




    public RRParam<InitMCVO> initMC(InitMCParam param, RRParam<InitMCVO> rrParam){

        InitMCVO initMCVO=new InitMCVO();

//初始化就是一堆检测是否可以新增会议

//是否需要检测通道的唯一性，一个通道是否可以被多次同时使用

        //并新建会议
        //新建会议人员设备通道
        //测试暂时不管检测的事
        int mttype=param.getMeetingtype();
        int modelbool=param.getModelbool();
        String  mtmodelssid=param.getMtmodelssid();
        List<TdAndUserParam> tulist=param.getTdAndUserList();
        Base_mtinfo base_mtinfo=new Base_mtinfo();
        if(modelbool==1) {//参照会议模板新增会议
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.eq("ssid", mtmodelssid);
            List<Avstmt_model> list = avstmt_modelMapper.selectList(wrapper);
            if(null!=list&&list.size() == 1){
                Avstmt_model avstmt_model=list.get(0);
                //有会议模板就按照模板的来，没有就用默认
                base_mtinfo.setMeetingtype(avstmt_model.getMeetingtype());
                base_mtinfo.setOpened(avstmt_model.getOpened());
                base_mtinfo.setUserecord(avstmt_model.getUserecord());

                //查找会议通道模板
                Avstmt_modeltd avstmt_modeltd=new Avstmt_modeltd();
                EntityWrapper<Avstmt_modeltd> entityWrapper = new EntityWrapper<Avstmt_modeltd>();
                entityWrapper.eq("mtmodelssid",mtmodelssid);
                List<Avstmt_modeltd> modeltdList=avstmt_modeltdMapper.selectList(entityWrapper);
                if(null!=tulist&&tulist.size()> 0 && null!=modeltdList&&modeltdList.size()> 0){

                    //先设置主麦
                    for(TdAndUserParam tu:tulist){
                        if(tu.getGrade()==1){
                            int i=0;
                            for(Avstmt_modeltd mtu:modeltdList){
                                if(mtu.getGrade()==1){

                                    String tdssid=mtu.getTdssid();

                                    tu.setTdssid(tdssid);//把模板的通道赋予会议用户通道
                                    tu.setAsrssid(mtu.getAsrssid());
                                    tu.setPolygraphssid(mtu.getPolygraphssid());
                                    tu.setUsepolygraph(mtu.getUsepolygraph());
                                    tu.setUseasr(mtu.getUseasr());
                                    modeltdList.remove(i);
                                    break;
                                }
                                i++;
                            }
                            break;
                        }
                    }

                    //再把剩下的麦的通道给其他用户
                    for(TdAndUserParam tu:tulist){
                        if(tu.getGrade()==1){//主麦已经设置了，不需要重新设置
                            continue;
                        }
                        tu.setTdssid(modeltdList.get(0).getTdssid());
                        tu.setAsrssid(modeltdList.get(0).getAsrssid());
                        tu.setPolygraphssid(modeltdList.get(0).getPolygraphssid());
                        tu.setUsepolygraph(modeltdList.get(0).getUsepolygraph());
                        tu.setUseasr(modeltdList.get(0).getUseasr());
                        modeltdList.remove(0);
                    }
                }
            }else{
                System.out.println("avstmt_modelMapper.selectObjs(wrapper) is null or size  > 1");
            }
        }

        String ssid= OpenUtil.getUUID_32();//会议ssid

        base_mtinfo.setSsid(ssid);
        int insertbool=base_mtinfoMapper.insert(base_mtinfo);
        System.out.println(insertbool+":insertbool,----base_mtinfoMapper.insert ssid:"+ssid );
        if(insertbool>-1){
            initMCVO.setMtssid(ssid);
            List<TDAndUserParam> tdAndUserParams=new ArrayList<TDAndUserParam>();

            //新增会议缓存
            MCCacheParam mcCacheParam=new MCCacheParam();
            mcCacheParam.setMeetingtype(mttype);
            mcCacheParam.setMtssid(ssid);

            if(null!=tulist&&tulist.size()> 0){
                List<TdAndUserAndOtherCacheParam> tdList=new ArrayList<TdAndUserAndOtherCacheParam>();
                for(TdAndUserParam tu:tulist){

                    //新建会议人员设备通道(绑定设备通道)
                    Avstmt_tduser avstmt_tduser=new Avstmt_tduser();
                    String tussid=OpenUtil.getUUID_32();
                    avstmt_tduser.setSsid(tussid);
                    avstmt_tduser.setEquipmenttdssid(tu.getTdssid());
                    avstmt_tduser.setMtssid(ssid);
                    avstmt_tduser.setUsername(tu.getUsername());
                    avstmt_tduser.setUsertype(tu.getGrade());
                    avstmt_tduser.setUserssid(tu.getUserssid());
                    avstmt_tduser.setCreatetime(new Date());
                    int insertbool_tu=avstmt_tduserMapper.insert(avstmt_tduser);
                    System.out.println(insertbool_tu+":insertbool_tu,----base_mtinfoMapper.insert tussid:"+tussid);

                    if(insertbool_tu > -1){

                        //返回带回会议通道用户对应关系
                        TDAndUserParam tdAndUserParam=new TDAndUserParam();
                        tdAndUserParam.setMttduserssid(tussid);
                        tdAndUserParam.setTdssid(tu.getTdssid());
                        tdAndUserParam.setMtuserssid(tu.getUserssid());
                        tdAndUserParam.setUsepolygraph(tu.getUsepolygraph());
                        tdAndUserParam.setUseasr(tu.getUseasr());
                        tdAndUserParam.setPolygraphssid(tu.getPolygraphssid());
                        tdAndUserParam.setAsrssid(tu.getAsrssid());
                        tdAndUserParams.add(tdAndUserParam);

                        //新增会议缓存中每一个人员
                        Gson gson = new Gson();
                        TdAndUserAndOtherCacheParam tdcache=gson.fromJson(gson.toJson(tu),TdAndUserAndOtherCacheParam.class);
                        tdcache.setMttduserssid(tussid);
                        tdList.add(tdcache);
                    }
                }
                mcCacheParam.setTdList(tdList);

            }
            MCCache.setMCCacheParam(mcCacheParam);//插入会议缓存

            //插入会议活动

            initMCVO.setTdlist(tdAndUserParams);
            rrParam.changeTrue(initMCVO);
        }
        return rrParam;
    }

    public RRParam<Boolean> startMC(StartMCParam param, RRParam<Boolean> rrParam){

//开始匹配会议人员设备通道
        String mtssid=param.getMtssid();
        int modelbool=param.getModelbool();
        String mtmodelssid=param.getMtmodelssid();

        //对每一个会议通道进行处理，该asr的要新建语言识别记录，开启asr识别；测谎也是一样的
        List<TdAndAsrParam> tdUserList=param.getTdAserList();
        if(null!=tdUserList&&tdUserList.size() > 0){
            for(TdAndAsrParam td:tdUserList){
                //完善会议缓存中通道的参数
                TdAndUserAndOtherCacheParam tdcacheParam= MCCache.getMCCacheOneTDParamByMTUserTDSsid(mtssid,td.getMttduserssid());
                if(null!=tdcacheParam){
                    tdcacheParam.setAsrssid(td.getAsrssid());
                    tdcacheParam.setUseasr(td.getUseasr());
                    tdcacheParam.setPolygraphssid(td.getPolygraphssid());
                    tdcacheParam.setUsepolygraph(td.getUsepolygraph());
                    tdcacheParam.setAsrtype(td.getAsrtype());
                }else{
                    System.out.println("注意完善会议缓存中通道失败，MCCache.getMCCacheOneTDParamByAsrssid(mtssid,td.getAsrssid()) is null --mtssid："+mtssid+"----td.getAsrssid():"+td.getAsrssid());
                    System.out.println("跳出，不开启会议其他组件---");
                    continue;
                }

                //检测是否需要asr，要新建语言识别记录，并开启asr
                if(td.getUseasr()!=1){//说明不需要asr
                    System.out.println("不需要开启asr--td.getMttduserssid()："+td.getMttduserssid());
                    continue;
                }

                String ssid =OpenUtil.getUUID_32();
                Avstmt_asrtd avstmt_asrtd=new Avstmt_asrtd();
                avstmt_asrtd.setSsid(ssid);
                avstmt_asrtd.setMttduserssid(td.getMttduserssid());
                avstmt_asrtd.setAsrserverssid(td.getAsrssid());
                avstmt_asrtd.setCreatetime((new Date()));
                int insert=avstmt_asrtdMapper.insert(avstmt_asrtd);
                System.out.println(insert+":insert---avstmt_tduserMapper.insert");
                if(insert > -1){

                    //有语音识别的开启语音识别
                    ReqParam<StartAsrParam> startparam=new ReqParam<StartAsrParam>();
                    StartAsrParam startAsrParam=new StartAsrParam();

                    //这里只需要给通道的ssid，设备微服务自己处理
                    startAsrParam.setTdssid(td.getTdssid());
                    startAsrParam.setAsrEquipmentssid(td.getAsrssid());
                    startAsrParam.setAsrtype(td.getAsrtype());
                    startparam.setParam(startAsrParam);
                    RResult result=equipmentControl.startAsr(startparam);
                    if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())){
                        String asrid=result.getData().toString();
                        String asrStartTime=result.getEndtime();//语音识别开始的时间
                        //修改语音识别记录中的asrid，这个是本次识别的唯一识别码
                        avstmt_asrtd.setAsrid(asrid);
                        long asrStartTime_long=Long.parseLong(asrStartTime);
                        avstmt_asrtd.setStarttime(asrStartTime_long);//开始时间保存为long类型
                        tdcacheParam.setAsrid(asrid);//缓存中放一份
                        tdcacheParam.setAsrStartTime(asrStartTime_long);
                        int i_updateById=avstmt_asrtdMapper.updateById(avstmt_asrtd);
                        System.out.println(i_updateById+":i_updateById 修改语音识别记录中的asrid");

                        //开启语音识别成功后，执行asr的线程，写入缓存
                        MC_AsrThread mcAsrThread=new MC_AsrThread(asrid,tdcacheParam.getUserssid(),equipmentControl,mtssid);
                        mcAsrThread.start();
                        tdcacheParam.setMcAsrThread(mcAsrThread);
                    }else{
                        System.out.println(result.getMessage()+",语音识别服务启动失败，td.getMttduserssid()："+td.getMttduserssid());
                    }
                }

                //检测是否需要测谎，要新建测谎记录，并开启测谎


                //是否需要录音
                //修改录音时间，asr识别时间，测谎仪时间



                //刷新会议缓存
                MCCache.setMCTDCacheParam(mtssid,tdcacheParam);
            }
            rrParam.changeTrue(true);
            //修改会议
            try {
                EntityWrapper<Base_mtinfo> entityWrapper=new EntityWrapper<Base_mtinfo>();
                entityWrapper.eq("ssid",mtssid);
                Base_mtinfo base_mtinfo =base_mtinfoMapper.selectList(entityWrapper).get(0);
                base_mtinfo.setMtstate(1);
                int updatebool=base_mtinfoMapper.update(base_mtinfo,entityWrapper);
                System.out.println("修改会议状态 开始 updatebool："+updatebool);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rrParam;
    }


    public RRParam<Boolean> overMC(OverMCParam param, RRParam<Boolean> rrParam){

//修改会议状态
        //关闭第三方的应用
        String mtssid=param.getMtssid();
        MCCacheParam mcCacheParam=MCCache.getMCCacheParam(mtssid);
        if(null!=mcCacheParam&&null!=mcCacheParam.getTdList()){
            List<TdAndUserAndOtherCacheParam> tdlist=mcCacheParam.getTdList();
            for(TdAndUserAndOtherCacheParam cachetd:tdlist){

                ReqParam<OverAsrParam> overparam=new ReqParam<OverAsrParam>();
                OverAsrParam overAsrParam=new OverAsrParam();
                overAsrParam.setAsrid(cachetd.getAsrid());
                overAsrParam.setAsrEquipmentssid(cachetd.getAsrssid());
                overAsrParam.setAsrtype(cachetd.getAsrtype());
                overparam.setParam(overAsrParam);
                RResult result=equipmentControl.overAsr(overparam);
                if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())){
                    rrParam.changeTrue(true);
                }else{
                    System.out.println(result.getMessage()+",语音识别服务关闭失败，param.getAsrid()："+param.getMtssid()+"--"+cachetd.getAsrid());
                }
            }
        }else{
            System.out.println("没有找到会议缓存或者会议通道缓存为空，不需要通知第三方");
        }

        //修改会议
        try {
            EntityWrapper<Base_mtinfo> entityWrapper=new EntityWrapper<Base_mtinfo>();
            entityWrapper.eq("ssid",mtssid);
            Base_mtinfo base_mtinfo =base_mtinfoMapper.selectList(entityWrapper).get(0);
            base_mtinfo.setMtstate(2);
            int updatebool=base_mtinfoMapper.update(base_mtinfo,entityWrapper);
            System.out.println("修改会议状态--结束-- updatebool："+updatebool);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rrParam;
    }


}
