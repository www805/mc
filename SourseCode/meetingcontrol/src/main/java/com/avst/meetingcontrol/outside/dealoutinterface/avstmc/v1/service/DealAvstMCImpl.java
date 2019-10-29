package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.v1.service;

import com.avst.meetingcontrol.common.conf.ASRType;
import com.avst.meetingcontrol.common.conf.FDType;
import com.avst.meetingcontrol.common.conf.MCType;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.*;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param.Avstmt_tduserAll;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.*;
import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_mtinfo;
import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_mttodatasave;
import com.avst.meetingcontrol.common.datasourse.publicsourse.mapper.Base_mtinfoMapper;
import com.avst.meetingcontrol.common.datasourse.publicsourse.mapper.Base_mttodatasaveMapper;
import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.OpenUtil;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RRParam;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.asr.OverAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.asr.StartAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutFlushbonadingEttdListParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.WorkOverParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.WorkOver_AccidentParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.WorkStartParam;
import com.avst.meetingcontrol.feignclient.ec.req.ph.CheckPolygraphStateParam;
import com.avst.meetingcontrol.feignclient.ec.req.ph.StartPolygraphParam;
import com.avst.meetingcontrol.feignclient.ec.vo.fd.WorkStartVO;
import com.avst.meetingcontrol.feignclient.ec.vo.fd.param.Flushbonading_ettd;
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
import com.avst.meetingcontrol.outside.interfacetoout.cache.PhForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.MCCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.PhForMCCache_oneParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.TdAndUserAndOtherCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.conf.MC_AsrThread;
import com.avst.meetingcontrol.outside.interfacetoout.conf.MC_PhThread;
import com.avst.meetingcontrol.outside.interfacetoout.vo.param.UserETParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

private Gson gson=new Gson();

    public RRParam<InitMCVO> initMC(InitMCParam param, RRParam<InitMCVO> rrParam){

        InitMCVO initMCVO=new InitMCVO();

//初始化就是一堆检测是否可以新增会议

//是否需要检测通道的唯一性，一个通道是否可以被多次同时使用

        //新建会议
        //新建会议人员设备通道
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

                initMCVO.setAsrtype(avstmt_model.getAsrtype());//这个参数决定了asr语音识别使用的类型，单对单还是单对多

                //查找会议通道模板
                Avstmt_modeltd avstmt_modeltd=new Avstmt_modeltd();
                EntityWrapper<Avstmt_modeltd> entityWrapper = new EntityWrapper<Avstmt_modeltd>();
                entityWrapper.eq("mtmodelssid",mtmodelssid);
                List<Avstmt_modeltd> modeltdList=avstmt_modeltdMapper.selectList(entityWrapper);
                if(null!=tulist&&tulist.size()> 0 && null!=modeltdList&&modeltdList.size()> 0){

                    //麦的设置是从1开始一直往后，1麦默认是主麦
                    for(int i=0;i<tulist.size();i++){
                        TdAndUserParam tu=tulist.get(i);

                            for(Avstmt_modeltd mtu:modeltdList){
                                if(tu.getGrade()==mtu.getGrade()){
                                    String tdssid=mtu.getTdssid();

                                    tu.setTdssid(tdssid);//把模板的通道赋予会议用户通道
                                    tu.setAsrssid(mtu.getAsrssid());
                                    tu.setPolygraphssid(mtu.getPolygraphssid());
                                    tu.setUsepolygraph(mtu.getUsepolygraph());
                                    tu.setUseasr(mtu.getUseasr());
                                    tu.setUserecord(base_mtinfo.getUserecord());
                                    tu.setFdeuipmentssid(mtu.getFdssid());
                                    tu.setGrade(mtu.getGrade());
                                    break;
                                }
                            }

                        //如果实际的路数的grade在模型中没有对应的grade，就是找不到对应的tdssid（通道ssid）就去掉这一路
                        if(StringUtils.isEmpty(tu.getTdssid())){
                            tulist.remove(i);
                            i--;
                        };

                    }
                }
            }else{
                LogUtil.intoLog(this.getClass(),"avstmt_modelMapper.selectObjs(wrapper) is null or size  > 1");
            }
        }

        String ssid= OpenUtil.getUUID_32();//会议ssid
        base_mtinfo.setCreatetime(new Date());
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
                        tdAndUserParam.setTdnum(tu.getGrade());
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
//                        tdcache.setAsrtype(ASRType.AVST);
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
        int asrtype=param.getAsrtype();
        int recordnum=0;//录音/像个数
        int asrnum=0;//语音识别个数
        int polygraphnum=0;//测谎仪个数
        List<UserETParam> useretlist=new ArrayList<UserETParam>();

        //对每一个会议通道进行处理，该asr的要新建语言识别记录，开启asr识别；测谎也是一样的
        List<TdAndAsrParam> tdUserList=param.getTdAserList();
        if(null!=tdUserList&&tdUserList.size() > 0){

            long mtstarttime=(new Date()).getTime();;//会议开始时间 ms

            int asrerrorcount=0;//asr语音识别错误路数
            String livingurl=null;
            String previewurl=null;
            String iid=null;
            long firstasrstarttime=(new Date()).getTime();//第一个
            String asrStartTime_asrtype2=null;//语音识别开始的时间
            //1对单单语音识别，2单对多语音识别
            String tdssids="";
            String asrid_asrtype2=null;//当有值的时候就不需要再去请求设备
            if(asrtype==2){//需要把所有的tdssid一次都传过去
                for(TdAndAsrParam td:tdUserList){
                    if(td.getUseasr()==1){
                        tdssids+=td.getTdssid()+",";
                    }
                }
                tdssids=OpenUtil.strtrim(tdssids,",");
            }

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

                //是否需要录音
                //会议开启的时候的录像时间才会是会议录像开始时间，暂停，继续之后的录像开始时间不是会议录像开始时间（重点记号）
                long startrecordtime=(new Date()).getTime();
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
                                previewurl=workStartVO.getFdpreviewurl();//设备直播预览地址

                                startrecordtime=workStartVO.getStartrecordtime();//录像开始时间
                                tdcacheParam.setFdrecordstarttime(startrecordtime);
                                //现在同步点都用录像时间
//                                if(null!=avstmt_asrtd&&null!=avstmt_asrtd.getId()&&0!=startrecordtime){
//                                    avstmt_asrtd.setStartrecordtime(startrecordtime);
//                                    int i_updateById=avstmt_asrtdMapper.updateById(avstmt_asrtd);
//                                    LogUtil.intoLog(this.getClass(),i_updateById+":i_updateById 修改语音识别记录中的startrecordtime");
//                                }else{
//                                    LogUtil.intoLog(this.getClass(),startrecordtime+":startrecordtime  会议用户语音识别对象没有找到，不进行修改操作 avstmt_asrtd："+avstmt_asrtd);
//
//                                }
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
                            RResult result=null;
                            String asrid=null;

                            //这里只需要给通道的ssid，设备微服务自己处理
                            if(asrtype==2){
                                if(null==asrid_asrtype2){
                                    result=asrStart(tdssids,td.getAsrssid(),td.getAsrtype());
                                    if(null!=result){
                                        asrid_asrtype2=result.getData().toString();
                                        asrid=asrid_asrtype2+"_"+td.getTdnum();
                                        asrStartTime_asrtype2=result.getEndtime();
                                    }
                                }else{
                                    asrid=asrid_asrtype2+"_"+td.getTdnum();
                                }
                            }else{
                                result=asrStart(td.getTdssid(),td.getAsrssid(),td.getAsrtype());
                                if(null!=result){
                                    asrid=result.getData().toString();
                                    asrStartTime_asrtype2=result.getEndtime();
                                }
                            }

                            if(null!=asrid){
                                //修改语音识别记录中的asrid，这个是本次识别的唯一识别码
                                avstmt_asrtd.setAsrid(asrid);
                                long asrStartTime_long=Long.parseLong(asrStartTime_asrtype2);
                                avstmt_asrtd.setStarttime(asrStartTime_long);//开始时间保存为long类型
                                tdcacheParam.setAsrid(asrid);//缓存中放一份
                                tdcacheParam.setAsrStartTime(asrStartTime_long);
                                avstmt_asrtd.setMtstartrecordtime(startrecordtime);
                                int i_updateById=avstmt_asrtdMapper.updateById(avstmt_asrtd);
                                LogUtil.intoLog(this.getClass(),i_updateById+":i_updateById 修改语音识别记录中的asrid");

                                tdcacheParam.setAsrRun(true);

                                //添加asrid语音识别的唯一识别码对应的会议ssid
                                AsrForMCCache.setMTssidByAsrid(asrid,mtssid);

                                asrnum++;

                            }else{
                                asrerrorcount++;
                                LogUtil.intoLog(4,this.getClass(),result==null?"":result.getMessage()+",语音识别服务启动失败，td.getMttduserssid()："+td.getMttduserssid());
                                tdcacheParam.setAsrRun(false);
                            }

                        }else{
                            asrerrorcount++;
                            LogUtil.intoLog(4,this.getClass(),"数据库新增失败 avstmt_asrtdMapper.insert"+td.getMttduserssid());
                            tdcacheParam.setAsrRun(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                //检测是否需要测谎，要新建测谎记录，并开启测谎
                try {
                    if(td.getUsepolygraph()==1){
                        ReqParam<StartPolygraphParam> checkparam=new ReqParam<StartPolygraphParam>();
                        StartPolygraphParam checkPolygraphStateParam =new StartPolygraphParam();
                        checkPolygraphStateParam.setPhType(td.getPolygraphtype());
                        checkPolygraphStateParam.setPolygraphssid(td.getPolygraphssid());
                        checkparam.setParam(checkPolygraphStateParam);
                        RResult checkphresult=equipmentControl.startPolygraph(checkparam);
                        if(null!=checkphresult&&checkphresult.getActioncode().equals(Code.SUCCESS.toString())&&null!=checkphresult.getData()){
                            try {
                                Gson gson=new Gson();
                                CheckPolygraphStateVO checkPolygraphStateVO=gson.fromJson(gson.toJson(checkphresult.getData()),CheckPolygraphStateVO.class);
                                System.out.println(checkPolygraphStateVO.getWorkstate());
                                if(null!=checkPolygraphStateVO&&1==checkPolygraphStateVO.getWorkstate()){//说明是可以用的
                                    LogUtil.intoLog(this.getClass(),"测谎仪开启 成功--Polygraphssid:"+td.getPolygraphssid());
                                    polygraphnum++;

                                    //long 测谎仪开始记录时间 ms
                                    long phstarttime=DateUtil.strToLong_MS(checkphresult.getEndtime());
                                    tdcacheParam.setPhStartTime(phstarttime);

                                    String phiid=phstarttime+"_"+td.getPolygraphssid();//iid暂时用开始记录测谎时间拼接phssid

                                    //写入数据库
                                    Avstmt_tdpolygraph avstmt_tdpolygraph=new Avstmt_tdpolygraph();
                                    avstmt_tdpolygraph.setMttduserssid(td.getMttduserssid());
                                    avstmt_tdpolygraph.setPolygraphssid(td.getPolygraphssid());
                                    avstmt_tdpolygraph.setMtstartrecordtime(startrecordtime);
                                    avstmt_tdpolygraph.setStarttime(phstarttime);
                                    avstmt_tdpolygraph.setIid(phiid);
                                    avstmt_tdpolygraph.setSsid(OpenUtil.getUUID_32());
                                    avstmt_tdpolygraph.setCreatetime(new Date());
                                    int insert_ph=avstmt_tdpolygraphMapper.insert(avstmt_tdpolygraph);
                                    if(insert_ph <= 0){//注意测试，这里，是0还是1
                                        LogUtil.intoLog(this.getClass(),"测谎仪关联用户通道失败，没有新增到数据库， mtssid："+mtssid+"--Polygraphssid:"+td.getPolygraphssid());
                                    }

                                    PhForMCCache_oneParam phone=new PhForMCCache_oneParam();
                                    phone.setPhssid(td.getPolygraphssid());
                                    phone.setUserssid(tdcacheParam.getUserssid());
                                    phone.setIid(phiid);
                                    PhForMCCache.setPhForMCCache_oneParam(phone,mtssid);

                                    //开启定时请求测谎仪数据 //业务平台测谎仪数据从缓存中调取
                                    MC_PhThread mc_phThread=new MC_PhThread(
                                            td.getPolygraphssid(),tdcacheParam.getUserssid(),equipmentControl,mtssid,td.getPolygraphtype(),phstarttime);
                                    mc_phThread.start();
                                    tdcacheParam.setMc_phThread(mc_phThread);

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


                //直播地址的填写
                //
                UserETParam userETParam=new UserETParam();
                userETParam.setFdssid(td.getFdssid());
                userETParam.setLivingurl(livingurl);
                userETParam.setPreviewurl(previewurl);
                userETParam.setIid(iid);
                userETParam.setUserssid(tdcacheParam.getUserssid());
                if(td.getUserecord()==1){
                    userETParam.setPolygraphssid(td.getPolygraphssid());
                }
                useretlist.add(userETParam);

                //刷新会议缓存
                MCCache.setMCTDCacheParam(mtssid,tdcacheParam);

            }

            //添加会议数据存储对应
            Base_mttodatasave base_mttodatasave=new Base_mttodatasave();
            base_mttodatasave.setMtssid(mtssid);
            base_mttodatasave.setIid(iid);
            base_mttodatasave.setCreatetime(new Date());
            base_mttodatasave.setSsid(OpenUtil.getUUID_32());
            int mttodatasaveinsert_bool=base_mttodatasaveMapper.insert(base_mttodatasave);
            LogUtil.intoLog(this.getClass(),"mttodatasaveinsert_bool__"+mttodatasaveinsert_bool);

            try {
                EntityWrapper<Base_mtinfo> entityWrapper=new EntityWrapper<Base_mtinfo>();
                entityWrapper.eq("ssid",mtssid);
                Base_mtinfo base_mtinfo =base_mtinfoMapper.selectList(entityWrapper).get(0);
                //是否考虑所有的服务未开启，或者必须开启的服务未开启时关闭本次会议
                //关闭会议缓存，会议数据库当前记录，avstmt_asrtdMapper会议通道识别记录
//                if(asrerrorcount==tdUserList.size()){//说明语音识别开启完全失败
//                    //以后加上所有需要开启的判断，综合考虑是否需要关闭本次会议
//                    //会议开启失败需要关闭的一些缓存和处理
//                    AsrForMCCache.delAsrForMCMap(mtssid);
//                    PhForMCCache.rvPhMap(mtssid);
//                    MCCache.delMCCacheParam(mtssid);
//                    base_mtinfo.setMtstate(4);
//                    int updatebool=base_mtinfoMapper.update(base_mtinfo,entityWrapper);
//                    LogUtil.intoLog(this.getClass(),"会议开启失败 修改会议状态 开始 updatebool："+updatebool);
//
//                }else{

                //修改会议
                try {
                    if(null!=base_mtinfo){
                        base_mtinfo.setMtstate(1);
                        base_mtinfo.setMtstarttime(mtstarttime);
                        int updatebool=base_mtinfoMapper.update(base_mtinfo,entityWrapper);
                        LogUtil.intoLog(this.getClass(),"会议开启成功 修改会议状态 开始 updatebool："+updatebool);


                        MCCacheParam  mcCacheParam=MCCache.getMCCacheParam(mtssid);
                        mcCacheParam.setMtstate(base_mtinfo.getMtstate());
                        mcCacheParam.setPolygraphnum(polygraphnum);
                        mcCacheParam.setAsrnum(asrnum);
                        mcCacheParam.setRecordnum(recordnum);
                        mcCacheParam.setMtstarttime(mtstarttime);//赋予开启会议的时间 ms

                        //更新会议缓存状态和组件服务数量
                        MCCache.setMCCacheParam(mcCacheParam);


                        //暂时去掉组件开始失败
                        StartMCVO startMCVO=new StartMCVO();
                        startMCVO.setAsrnum(asrnum);
                        startMCVO.setMtssid(mtssid);
                        startMCVO.setPolygraphnum(polygraphnum);
                        startMCVO.setRecordnum(recordnum);
                        startMCVO.setUseretlist(useretlist);
                        rrParam.changeTrue(startMCVO);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return rrParam;
    }

    private RResult asrStart(String tdssid,String asrssid,String asrtype){
        //有语音识别的开启语音识别
        ReqParam<StartAsrParam> startparam=new ReqParam<StartAsrParam>();
        StartAsrParam startAsrParam=new StartAsrParam();

        startAsrParam.setTdssid(tdssid);
        startAsrParam.setAsrEquipmentssid(asrssid);
        startAsrParam.setAsrtype(asrtype);
        startparam.setParam(startAsrParam);
        RResult result=equipmentControl.startAsr(startparam);
        if(null!=result&&result.getActioncode().equals(Code.SUCCESS.toString())&&null!=result.getData()){
            return result;
        }
        return null;
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
            base_mtinfo.setMtendtime((new Date()).getTime());
            int updatebool=base_mtinfoMapper.update(base_mtinfo,entityWrapper);
            LogUtil.intoLog(this.getClass(),"修改会议状态--结束-- updatebool："+updatebool);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rrParam;
    }

    public RRParam<Boolean> overMC_Accident(OverMCParam param, RRParam<Boolean> rrParam){

        //查询数据库，找到所有设备的ssid
        //查询缓存看这些设备ssid有没有正在用的， 没有的话就直接关闭掉,在avstmt_asrtd里面找mcrecordtime,有的话就带上
        //暂时没有语音识别和身心监护的数据保存


//修改会议状态
        //关闭第三方的应用
        String mtssid=param.getMtssid();

        //找出所有设备ssid
        EntityWrapper ew=new EntityWrapper();
        ew.eq("tu.mtssid",mtssid);
        List<Avstmt_tduserAll> tulist=avstmt_tduserMapper.getAvstmt_tduserAll(ew);
        if(null!=tulist&&tulist.size() > 0){

            Set<String> fdSsidList=new HashSet<String>();

            for(Avstmt_tduserAll tu:tulist){//Avstmt_tduserAll asr或者ph的mtrecordtime可以被找到
                String tdssid=tu.getEquipmenttdssid();
                if(StringUtils.isNotEmpty(tdssid)){
                    ReqParam<GetToOutFlushbonadingEttdListParam> tdparam=new ReqParam<GetToOutFlushbonadingEttdListParam>();
                    GetToOutFlushbonadingEttdListParam getToOutFlushbonadingEttdListParam=new GetToOutFlushbonadingEttdListParam();
                    getToOutFlushbonadingEttdListParam.setSsid(tdssid);
                    getToOutFlushbonadingEttdListParam.setFdType(FDType.FD_AVST);
                    tdparam.setParam(getToOutFlushbonadingEttdListParam);
                    RResult rr4=equipmentControl.getToOutFlushbonadingEttdById(tdparam);
                    if (null!=rr4&&rr4.getActioncode().equals(Code.SUCCESS.toString())&&null!=rr4.getData()){
                        Flushbonading_ettd flushbonading_ettd=gson.fromJson(gson.toJson(rr4.getData()), Flushbonading_ettd.class);
                        if(null!=flushbonading_ettd&&StringUtils.isNotEmpty(flushbonading_ettd.getFlushbonadingssid())){
                            fdSsidList.add(flushbonading_ettd.getFlushbonadingssid());
                        }
                    }else {
                        LogUtil.intoLog(this.getClass(),"设备通道getToOutFlushbonadingEttdById__请求失败");
                    }
                }
            }

            //关闭设备
            if(null!=fdSsidList&&fdSsidList.size() > 0){
                Set<String> ssidlist=MCCache.getFDSsidList();//查找所有正在使用的设备
                for(String fdssid:fdSsidList){//关闭设备

                    //关闭设备录像
                    ReqParam<WorkOver_AccidentParam> workOverParamReqParam=new ReqParam<WorkOver_AccidentParam>();
                    WorkOver_AccidentParam workOverParam=new WorkOver_AccidentParam();
                    workOverParam.setFdid(mtssid);//就是会议ssid
                    workOverParam.setFdType(FDType.FD_AVST);
                    workOverParam.setFlushbonadingetinfossid(fdssid);
                    workOverParam.setIid(mtssid+"_"+fdssid);//iid其实就是 会议ssid_设备ssid
//                    workOverParam.setMtRecordTime();//这个参数只会是在要保存语音识别和身心监护数据的时候才会有用，从tulist中得到,

                    //查询缓存看这些设备ssid有没有正在用的
                    if(null!=ssidlist&&ssidlist.size() > 0){

                        for(String fdssid_c:ssidlist){
                            if(fdssid_c.equals(fdssid)){//说明暂时不能关闭这个
                                workOverParam.setCloaseRecbool(false);
                                break;
                            }
                        }
                    }

                    workOverParamReqParam.setParam(workOverParam);
                    RResult worr=equipmentControl.workOver_Accident(workOverParamReqParam);
                    if(null!=worr&&null!=worr.getActioncode()&&worr.getActioncode().equals(Code.SUCCESS.toString())){
                        String iid=worr.getData().toString();//录音的唯一标识码
                        //是否需要对录音进行处理
                        LogUtil.intoLog(this.getClass(),"关闭录像成功 mtssid："+mtssid+"----iid:"+iid);

                    }else{
                        LogUtil.intoLog(this.getClass(),"关闭录像失败 mtssid："+mtssid+"----fdssid:"+fdssid);
                    }
                }
            }else{
                LogUtil.intoLog(this.getClass(),"没有找到会议缓存或者会议通道缓存为空，不需要通知第三方");
            }

        }else{//没有设备需要关闭
            rrParam.setMessage("没有设备需要关闭");
            LogUtil.intoLog(4,this.getClass(),"overMC_Accident 关闭异常会议的时候，没有设备需要关闭，mtssid="+mtssid);
            return rrParam;
        }

        //修改会议
        try {
            EntityWrapper<Base_mtinfo> entityWrapper=new EntityWrapper<Base_mtinfo>();
            entityWrapper.eq("ssid",mtssid);
            Base_mtinfo base_mtinfo =base_mtinfoMapper.selectList(entityWrapper).get(0);
            base_mtinfo.setMtstate(2);
            base_mtinfo.setMtendtime((new Date()).getTime());
            int updatebool=base_mtinfoMapper.update(base_mtinfo,entityWrapper);
            LogUtil.intoLog(this.getClass(),"修改会议状态--结束-- updatebool："+updatebool);

            rrParam.changeTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rrParam;
    }


}
