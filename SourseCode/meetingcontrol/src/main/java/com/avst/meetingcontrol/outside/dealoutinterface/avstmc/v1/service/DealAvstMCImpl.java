package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.v1.service;

import com.avst.meetingcontrol.common.conf.MCType;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_asrtd;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_model;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_modeltd;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_tduser;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.*;
import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_mtinfo;
import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_mttodatasave;
import com.avst.meetingcontrol.common.datasourse.publicsourse.mapper.Base_mtinfoMapper;
import com.avst.meetingcontrol.common.datasourse.publicsourse.mapper.Base_mttodatasaveMapper;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.OpenUtil;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RRParam;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.asr.OverAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.asr.StartAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.WorkOverParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.WorkStartParam;
import com.avst.meetingcontrol.feignclient.ec.req.ph.CheckPolygraphStateParam;
import com.avst.meetingcontrol.feignclient.ec.vo.fd.WorkStartVO;
import com.avst.meetingcontrol.feignclient.ec.vo.ph.CheckPolygraphStateVO;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.InitMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.OverMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.StartMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndAsrParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndUserParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.InitMCVO;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.StartMCVO;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.param.TDAndUserParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.AsrForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.MCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.MCCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.TdAndUserAndOtherCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.vo.param.UserETParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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

    @Autowired
    private Base_mttodatasaveMapper base_mttodatasaveMapper;



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
                                    tu.setUserecord(base_mtinfo.getUserecord());
                                    tu.setFdeuipmentssid(mtu.getFdssid());
                                    tu.setGrade(mtu.getGrade());
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
                        if(tu.getGrade()==1){//主麦已经设置了，不需要重新设置,主麦只能有一个，不然会有问题
                            continue;
                        }
                        tu.setTdssid(modeltdList.get(0).getTdssid());
                        tu.setAsrssid(modeltdList.get(0).getAsrssid());
                        tu.setPolygraphssid(modeltdList.get(0).getPolygraphssid());
                        tu.setUsepolygraph(modeltdList.get(0).getUsepolygraph());
                        tu.setUseasr(modeltdList.get(0).getUseasr());
                        tu.setFdeuipmentssid(modeltdList.get(0).getFdssid());
                        tu.setGrade(modeltdList.get(0).getGrade());
                        tu.setUserecord(base_mtinfo.getUserecord());
                        modeltdList.remove(0);
                    }
                }
            }else{
                LogUtil.intoLog(this.getClass(),"avstmt_modelMapper.selectObjs(wrapper) is null or size  > 1");
            }
        }

        String ssid= OpenUtil.getUUID_32();//会议ssid

        base_mtinfo.setSsid(ssid);
        int insertbool=base_mtinfoMapper.insert(base_mtinfo);
        LogUtil.intoLog(this.getClass(),insertbool+":insertbool,----base_mtinfoMapper.insert ssid:"+ssid );
        if(insertbool>-1){
            initMCVO.setMtssid(ssid);
            List<TDAndUserParam> tdAndUserParams=new ArrayList<TDAndUserParam>();

            //新增会议缓存
            MCCacheParam mcCacheParam=new MCCacheParam();
            mcCacheParam.setMeetingtype(mttype);
            mcCacheParam.setMtssid(ssid);
            mcCacheParam.setMcType(MCType.AVST);//在avstmc的处理类中，类型就肯定是这个
            mcCacheParam.setYwSystemType(param.getYwSystemType());//业务系统的类型
            mcCacheParam.setMtstate(base_mtinfo.getMtstate());//状态初始化

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
                    LogUtil.intoLog(this.getClass(),insertbool_tu+":insertbool_tu,----base_mtinfoMapper.insert tussid:"+tussid);

                    if(insertbool_tu > -1){

                        //返回带回会议通道用户对应关系
                        TDAndUserParam tdAndUserParam=new TDAndUserParam();
                        tdAndUserParam.setMttduserssid(tussid);
                        tdAndUserParam.setTdssid(tu.getTdssid());
                        tdAndUserParam.setMtuserssid(tu.getUserssid());
                        tdAndUserParam.setUsepolygraph(tu.getUsepolygraph());
                        tdAndUserParam.setUseasr(tu.getUseasr());
                        tdAndUserParam.setUserecord(tu.getUserecord());
                        tdAndUserParam.setPolygraphssid(tu.getPolygraphssid());
                        tdAndUserParam.setAsrssid(tu.getAsrssid());
                        tdAndUserParam.setFdssid(tu.getFdeuipmentssid());
                        tdAndUserParams.add(tdAndUserParam);

                        //新增会议缓存中每一个人员
                        Gson gson = new Gson();
                        TdAndUserAndOtherCacheParam tdcache=gson.fromJson(gson.toJson(tu),TdAndUserAndOtherCacheParam.class);
                        tdcache.setMttduserssid(tussid);
                        tdcache.setFdssid(tu.getFdeuipmentssid());
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

    public RRParam startMC(StartMCParam param, RRParam rrParam){

//开始匹配会议人员设备通道
        String mtssid=param.getMtssid();
        int modelbool=param.getModelbool();
        String mtmodelssid=param.getMtmodelssid();
        int recordnum=0;//录音/像个数
        int asrnum=0;//语音识别个数
        int polygraphnum=0;//测谎仪个数
        List<UserETParam> useretlist=new ArrayList<UserETParam>();

        //对每一个会议通道进行处理，该asr的要新建语言识别记录，开启asr识别；测谎也是一样的
        List<TdAndAsrParam> tdUserList=param.getTdAserList();
        if(null!=tdUserList&&tdUserList.size() > 0){
            int asrerrorcount=0;//asr语音识别错误路数
            String livingurl=null;
            String iid=null;
            long firstasrstarttime=(new Date()).getTime();//第一个
            for(TdAndAsrParam td:tdUserList){
                //完善会议缓存中通道的参数
                TdAndUserAndOtherCacheParam tdcacheParam= MCCache.getMCCacheOneTDParamByMTUserTDSsid(mtssid,td.getMttduserssid());
                if(null!=tdcacheParam){
                    tdcacheParam.setAsrssid(td.getAsrssid());
                    tdcacheParam.setUseasr(td.getUseasr());
                    tdcacheParam.setAsrtype(td.getAsrtype());

                    tdcacheParam.setPolygraphssid(td.getPolygraphssid());
                    tdcacheParam.setUsepolygraph(td.getUsepolygraph());
                    tdcacheParam.setPolygraphtype(td.getPolygraphtype());

                    tdcacheParam.setFdssid(td.getFdssid());
                    tdcacheParam.setFdtype(td.getFdtype());
                    tdcacheParam.setFdrecord(td.getUserecord());
                }else{
                    LogUtil.intoLog(this.getClass(),"注意完善会议缓存中通道失败，MCCache.getMCCacheOneTDParamByAsrssid(mtssid,td.getAsrssid()) is null --mtssid："+mtssid+"----td.getAsrssid():"+td.getAsrssid());
                    LogUtil.intoLog(this.getClass(),"跳出，不开启会议其他组件---");
                    continue;
                }


                //检测是否需要asr，要新建语言识别记录，并开启asr
                Avstmt_asrtd avstmt_asrtd=null;
                if(td.getUseasr()!=1){//说明不需要asr
                    LogUtil.intoLog(this.getClass(),"不需要开启asr--td.getMttduserssid()："+td.getMttduserssid());

                }else{
                    String mtasrtdssid =OpenUtil.getUUID_32();
                    avstmt_asrtd=new Avstmt_asrtd();
                    try {
                        avstmt_asrtd.setSsid(mtasrtdssid);
                        avstmt_asrtd.setMttduserssid(td.getMttduserssid());
                        avstmt_asrtd.setAsrserverssid(td.getAsrssid());
                        avstmt_asrtd.setCreatetime((new Date()));
                        int insert=avstmt_asrtdMapper.insert(avstmt_asrtd);
                        LogUtil.intoLog(this.getClass(),insert+":insert---avstmt_tduserMapper.insert");
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
                                LogUtil.intoLog(this.getClass(),i_updateById+":i_updateById 修改语音识别记录中的asrid");

                                tdcacheParam.setAsrRun(true);

                                //添加asrid语音识别的唯一识别码对应的会议ssid
                                AsrForMCCache.setMTssidByAsrid(asrid,mtssid);

                                asrnum++;

                            }else{
                                asrerrorcount++;
                                LogUtil.intoLog(this.getClass(),result.getMessage()+",语音识别服务启动失败，td.getMttduserssid()："+td.getMttduserssid());
                                tdcacheParam.setAsrRun(false);
                            }

                        }else{
                            asrerrorcount++;
                            LogUtil.intoLog(this.getClass(),"数据库新增失败 avstmt_asrtdMapper.insert"+td.getMttduserssid());
                            tdcacheParam.setAsrRun(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



                //检测是否需要测谎，要新建测谎记录，并开启测谎
                try {
                    if(td.getUsepolygraph()==1){
                        ReqParam<CheckPolygraphStateParam> checkparam=new ReqParam<CheckPolygraphStateParam>();
                        CheckPolygraphStateParam checkPolygraphStateParam =new CheckPolygraphStateParam();
                        checkPolygraphStateParam.setPhType(td.getPolygraphtype());
                        checkPolygraphStateParam.setPolygraphssid(td.getPolygraphssid());
                        checkparam.setParam(checkPolygraphStateParam);
                        RResult checkphresult=equipmentControl.checkPolygraphState(checkparam);
                        if(null!=checkphresult&&checkphresult.getActioncode().equals(Code.SUCCESS.toString())&&null!=checkphresult.getData()){
                            try {
                                Gson gson=new Gson();
                                CheckPolygraphStateVO checkPolygraphStateVO=gson.fromJson(gson.toJson(checkphresult.getData()),CheckPolygraphStateVO.class);
                                if(0==checkPolygraphStateVO.getWorkstate()){//说明是可以用的
                                    LogUtil.intoLog(this.getClass(),"测谎仪检测 成功--Polygraphssid:"+td.getPolygraphssid());
                                    polygraphnum++;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{
                            LogUtil.intoLog(this.getClass(),"测谎仪检测是否在线失败 mtssid："+mtssid+"--Polygraphssid:"+td.getPolygraphssid());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //是否需要录音
                try {
                    if(td.getUserecord()==1){

                        ReqParam<WorkStartParam> workStartParam=new ReqParam<WorkStartParam>();
                        WorkStartParam startParam=new WorkStartParam();
                        startParam.setFdid(mtssid);//就是会议ssid
                        startParam.setFdType(td.getFdtype());
                        startParam.setFlushbonadingetinfossid(td.getFdssid());
                        workStartParam.setParam(startParam);
                        RResult result=equipmentControl.workStart(workStartParam);
                        if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())&&null!=result.getData()){
                            try {
                                Gson gson=new Gson();
                                WorkStartVO workStartVO=gson.fromJson(gson.toJson(result.getData()),WorkStartVO.class);
                                iid=workStartVO.getIid();//设备录像的唯一识别码
                                livingurl=workStartVO.getFdlivingurl();//设备直播地址

                                long startrecordtime=workStartVO.getStartrecordtime();//录像开始时间

                                if(null!=avstmt_asrtd&&null!=avstmt_asrtd.getId()&&0!=startrecordtime){
                                    avstmt_asrtd.setStartrecordtime(startrecordtime);
                                    int i_updateById=avstmt_asrtdMapper.updateById(avstmt_asrtd);
                                    LogUtil.intoLog(this.getClass(),i_updateById+":i_updateById 修改语音识别记录中的startrecordtime");
                                }else{
                                    LogUtil.intoLog(this.getClass(),startrecordtime+":startrecordtime  会议用户语音识别对象没有找到，不进行修改操作 avstmt_asrtd："+avstmt_asrtd);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            recordnum++;

                        }else{
                            LogUtil.intoLog(this.getClass(),"设备录音失败 mtssid："+mtssid+"--fdssid:"+td.getFdssid());
                        }
                    }else{
                        LogUtil.intoLog(this.getClass(),"不需要开启fd录像--td.getMttduserssid()："+td.getMttduserssid());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //直播地址的填写
                //
                UserETParam userETParam=new UserETParam();
                userETParam.setFdssid(td.getFdssid());
                userETParam.setLivingurl(livingurl);
                userETParam.setIid(iid);
                userETParam.setUserssid(tdcacheParam.getUserssid());
                if(td.getUserecord()==1){
                    userETParam.setPolygraphssid(td.getPolygraphssid());
                }
                useretlist.add(userETParam);

                //修改录音时间，asr识别时间，测谎仪时间

                //刷新会议缓存
                MCCache.setMCTDCacheParam(mtssid,tdcacheParam);

            }

            //添加会议数据存储对应
            Base_mttodatasave base_mttodatasave=new Base_mttodatasave();
            base_mttodatasave.setCreatetime(new Date());
            base_mttodatasave.setMtssid(mtssid);
            base_mttodatasave.setIid(iid);
            base_mttodatasave.setSsid(OpenUtil.getUUID_32());
            int mttodatasaveinsert_bool=base_mttodatasaveMapper.insert(base_mttodatasave);
            LogUtil.intoLog(this.getClass(),"mttodatasaveinsert_bool__"+mttodatasaveinsert_bool);

            try {
                EntityWrapper<Base_mtinfo> entityWrapper=new EntityWrapper<Base_mtinfo>();
                entityWrapper.eq("ssid",mtssid);
                Base_mtinfo base_mtinfo =base_mtinfoMapper.selectList(entityWrapper).get(0);
                //是否考虑所有的服务未开启，或者必须开启的服务未开启时关闭本次会议
                //关闭会议缓存，会议数据库当前记录，avstmt_asrtdMapper会议通道识别记录
                if(asrerrorcount==tdUserList.size()){//说明语音识别开启完全失败
                    //以后加上所有需要开启的判断，综合考虑是否需要关闭本次会议

                    MCCache.delMCCacheParam(mtssid);
                    base_mtinfo.setMtstate(4);
                    int updatebool=base_mtinfoMapper.update(base_mtinfo,entityWrapper);
                    LogUtil.intoLog(this.getClass(),"会议开启失败 修改会议状态 开始 updatebool："+updatebool);

                }else{

                    StartMCVO startMCVO=new StartMCVO();
                    startMCVO.setAsrnum(asrnum);
                    startMCVO.setMtssid(mtssid);
                    startMCVO.setPolygraphnum(polygraphnum);
                    startMCVO.setRecordnum(recordnum);
                    startMCVO.setUseretlist(useretlist);
                    rrParam.changeTrue(startMCVO);
                    //修改会议
                    try {

                        base_mtinfo.setMtstate(1);
                        int updatebool=base_mtinfoMapper.update(base_mtinfo,entityWrapper);
                        LogUtil.intoLog(this.getClass(),"会议开启成功 修改会议状态 开始 updatebool："+updatebool);


                        MCCacheParam  mcCacheParam=MCCache.getMCCacheParam(mtssid);

                        //刷新会议缓存状态
                        MCCache.setMCCacheParam(mtssid,base_mtinfo.getMtstate());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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

                //关闭asr
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
                    LogUtil.intoLog(this.getClass(),result.getMessage()+",语音识别服务关闭失败，param.getAsrid()："+param.getMtssid()+"--"+cachetd.getAsrid());
                }

                //关闭设备录像
                ReqParam<WorkOverParam> workOverParamReqParam=new ReqParam<WorkOverParam>();
                WorkOverParam workOverParam=new WorkOverParam();
                workOverParam.setFdid(mtssid);//就是会议ssid
                workOverParam.setFdType(cachetd.getFdtype());
                workOverParam.setFlushbonadingetinfossid(cachetd.getFdssid());
                workOverParamReqParam.setParam(workOverParam);
                RResult worr=equipmentControl.workOver(workOverParamReqParam);
                if(null!=worr&&null!=worr.getActioncode()&&worr.getActioncode().equals(Code.SUCCESS.toString())){
                    String iid=worr.getData().toString();//录音的唯一标识码
                    //是否需要对录音进行处理
                    LogUtil.intoLog(this.getClass(),"关闭录像成功 mtssid："+mtssid+"----iid:"+iid);

                }else{
                    LogUtil.intoLog(this.getClass(),"关闭录像失败 mtssid："+mtssid+"----cachetd.getFdssid():"+cachetd.getFdssid());
                }
            }
        }else{
            LogUtil.intoLog(this.getClass(),"没有找到会议缓存或者会议通道缓存为空，不需要通知第三方");
        }

        //修改会议
        try {
            EntityWrapper<Base_mtinfo> entityWrapper=new EntityWrapper<Base_mtinfo>();
            entityWrapper.eq("ssid",mtssid);
            Base_mtinfo base_mtinfo =base_mtinfoMapper.selectList(entityWrapper).get(0);
            base_mtinfo.setMtstate(2);
            int updatebool=base_mtinfoMapper.update(base_mtinfo,entityWrapper);
            LogUtil.intoLog(this.getClass(),"修改会议状态--结束-- updatebool："+updatebool);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rrParam;
    }


}
