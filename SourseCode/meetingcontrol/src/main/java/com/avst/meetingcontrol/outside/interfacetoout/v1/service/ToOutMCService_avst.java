package com.avst.meetingcontrol.outside.interfacetoout.v1.service;

import com.avst.meetingcontrol.common.conf.SSType;
import com.avst.meetingcontrol.common.conf.YWType;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_realtimrecord;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_tdpolygraph;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_tduser;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param.Avstmt_tduserAll;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_realtimrecordMapper;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_tdpolygraphMapper;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_tduserMapper;
import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_mttodatasave;
import com.avst.meetingcontrol.common.datasourse.publicsourse.mapper.Base_mttodatasaveMapper;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RRParam;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.bl.REMControl;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.ss.GetSavePathParam;
import com.avst.meetingcontrol.feignclient.ec.vo.ss.GetSavepathVO;
import com.avst.meetingcontrol.feignclient.ec.vo.ss.param.RecordSavepathParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.InitMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.OverMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.StartMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndAsrParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.param.TdAndUserParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.v1.action.AvstMCImpl;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.InitMCVO;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.param.TDAndUserParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.AsrForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.MCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.PhForMCCache;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrTxtParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.MCCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.PhDataParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.TdAndUserAndOtherCacheParam;
import com.avst.meetingcontrol.outside.interfacetoout.conf.MCOverThread;
import com.avst.meetingcontrol.outside.interfacetoout.req.*;
import com.avst.meetingcontrol.outside.interfacetoout.vo.GetMCVO;
import com.avst.meetingcontrol.outside.interfacetoout.vo.SetMCAsrTxtBackVO;
import com.avst.meetingcontrol.outside.interfacetoout.vo.StartMCVO;
import com.avst.meetingcontrol.outside.interfacetoout.vo.param.PHDataBackVoParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class ToOutMCService_avst implements BaseDealMCInterface {

    @Autowired
    private EquipmentControl equipmentControl;

    @Autowired
    private REMControl remControl;

    @Autowired
    private Avstmt_realtimrecordMapper avstmt_realtimrecordMapper;

    @Autowired
    private Avstmt_tduserMapper avstmt_tduserMapper;

    @Autowired
    private Base_mttodatasaveMapper base_mttodatasaveMapper;

    @Autowired
    private Avstmt_tdpolygraphMapper avstmt_tdpolygraphMapper;


    @Override
    public RResult startMC(ReqParam<StartMCParam_out> param, RResult result) {


        StartMCParam_out startMCParam_out=param.getParam();

        //实例化会议
        InitMCParam initMCParam=new InitMCParam();
        initMCParam.setMeetingtype(startMCParam_out.getMeetingtype());
        initMCParam.setModelbool(startMCParam_out.getModelbool());
        initMCParam.setMtmodelssid(startMCParam_out.getMtmodelssid());
        initMCParam.setYwSystemType(startMCParam_out.getYwSystemType());
        List<TdAndUserParam> tdAndUserList=new ArrayList<TdAndUserParam>();
        Gson gson = new Gson();
        List<TdAndUserAndOtherParam> tdlist=startMCParam_out.getTdList();
        if(null!=tdlist&&tdlist.size() > 0){

            for(TdAndUserAndOtherParam td:tdlist){
                TdAndUserParam tdAndUserParam=gson.fromJson(gson.toJson(td),TdAndUserParam.class);
                tdAndUserList.add(tdAndUserParam);
            }
            initMCParam.setTdAndUserList(tdAndUserList);
            RRParam<InitMCVO> rr= AvstMCImpl.initMC(initMCParam);
            if(null==rr||rr.getCode()!=1||null==rr.getT()){//实例化会议失败
                LogUtil.intoLog(this.getClass(),"实例化会议失败---rr.getMessage():"+rr.getMessage()+"----startMCParam_out.getModelbool():"+startMCParam_out.getModelbool());
                result.setMessage("实例化会议失败");
                return result;
            }
            //开启会议
            String mtssid=rr.getT().getMtssid();//本次会议的ssid
            List<TDAndUserParam> rrtdlist=rr.getT().getTdlist();
            if(null==rrtdlist || rrtdlist.size() == 0){
                LogUtil.intoLog(this.getClass(),rrtdlist.size()+":rrtdlist.size() AvstMCImpl.initMC 设置会议人员绑定设备通道失败---");
                result.setMessage("设置会议人员绑定设备通道失败---");
                return result;
            }
            StartMCParam startMCParam=new StartMCParam();
            startMCParam.setModelbool(startMCParam_out.getModelbool());
            startMCParam.setMtmodelssid(startMCParam_out.getMtmodelssid());
            startMCParam.setMtssid(mtssid);//实例化返回的会议ssid
            List<TdAndAsrParam> tdAndAsrList=new ArrayList<TdAndAsrParam>();
            gson = new Gson();
            for(TdAndUserAndOtherParam td:tdlist){
                TdAndAsrParam tdAndAsrParam=gson.fromJson(gson.toJson(td),TdAndAsrParam.class);
                //还有5个参数没有设置
                for(TDAndUserParam p:rrtdlist){
                    if(p.getMtuserssid().equals(td.getUserssid())){//把init会议生成的会议人员通道ssid填进去
                        tdAndAsrParam.setMttduserssid(p.getMttduserssid());
                        tdAndAsrParam.setTdssid(p.getTdssid());
                        tdAndAsrParam.setUsepolygraph(p.getUsepolygraph());
                        tdAndAsrParam.setUseasr(p.getUseasr());
                        tdAndAsrParam.setUserecord(p.getUserecord());
                        tdAndAsrParam.setPolygraphssid(p.getPolygraphssid());
                        tdAndAsrParam.setAsrssid(p.getAsrssid());
                        tdAndAsrParam.setFdssid(p.getFdssid());
                        break;
                    }
                }
                tdAndAsrList.add(tdAndAsrParam);
            }
            startMCParam.setTdAserList(tdAndAsrList);
            RRParam rr2= AvstMCImpl.startMC(startMCParam);
            if(null!=rr2&&rr2.getCode()==1&&null!=rr2.getT()){//开启会议成功
                gson=new Gson();
                StartMCVO startMCVO=null;
                try {
                    startMCVO=gson.fromJson(gson.toJson(rr2.getT()),StartMCVO.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(null==startMCVO){//以防万一解析出错了
                    startMCVO=new StartMCVO();
                    startMCVO.setMtssid(mtssid);
                }
                result.changeToTrue(startMCVO);//返回本次会议的ssid
            }
        }
        return result;
    }

    @Override
    public RResult overMC(ReqParam<OverMCParam_out> param, RResult result) {

        //关闭会议
        OverMCParam_out overMCParam_out=param.getParam();
        String mtssid=overMCParam_out.getMtssid();
        OverMCParam overparam=new OverMCParam();
        overparam.setMtssid(mtssid);
        RRParam<Boolean> rr= AvstMCImpl.overMC(overparam);;
        if(null!=rr&&rr.getCode()==1&&null!=rr.getT()&&rr.getT().equals(true)){//开启会议成功
            result.changeToTrue(true);//返回
        }

        //开启关闭的线程
        MCOverThread mcOverThread=new MCOverThread(mtssid);
        mcOverThread.start();
        return result;
    }

    @Override
    public boolean setMCAsrTxtBack(ReqParam<SetMCAsrTxtBackParam_out> param) {

        SetMCAsrTxtBackParam_out mcAsrTxtBackParam_out=param.getParam();
        String asrid=mcAsrTxtBackParam_out.getAsrid();
        String mtssid=AsrForMCCache.getMTssidByAsrid(asrid);
        if(null==mcAsrTxtBackParam_out.getAsrTxtParam_toout()){
            return false;
        }
        String userssid=MCCache.getUserSsidByAsrid(mtssid,asrid);

        AsrTxtParam_toout asrtxt= null;
        Gson gson=new Gson();
        asrtxt = gson.fromJson(gson.toJson(mcAsrTxtBackParam_out.getAsrTxtParam_toout()), AsrTxtParam_toout.class);

        if(null!=asrtxt){

            LogUtil.intoLog(this.getClass(),userssid+":userssid 运行中---");
            AsrForMCCache.runbool=false;
            asrtxt.setUserssid(userssid);
            AsrForMCCache.addAsrTxt(mtssid,asrid,asrtxt,userssid);
            AsrForMCCache.runbool=true;

            //向业务平台推送数据
            try {
                MCCacheParam mcCacheParam=MCCache.getMCCacheParam(mtssid);
                if(null!=mcCacheParam){
                    AsrTxtParam_toout asrTxtParam_toout=AsrForMCCache.getNewestAsrTxtBymtssid(mtssid);
                    //获取asrstarttime 时间戳
                    TdAndUserAndOtherCacheParam tdAndUserAndOtherCacheParam=MCCache.getMCCacheOneTDParamByUserssid(mtssid,asrTxtParam_toout.getUserssid());
                    if(null!=tdAndUserAndOtherCacheParam){
                        asrTxtParam_toout.setAsrstartime(tdAndUserAndOtherCacheParam.getAsrStartTime());
                    }else{
                        asrTxtParam_toout.setAsrstartime((new Date()).getTime());
                        LogUtil.intoLog(3,this.getClass(),"没有找到这个会议的这个用户对应的信息 mtssid："+mtssid+"---asrTxtParam_toout.getUserssid():"+asrTxtParam_toout.getUserssid());
                    }
                    SetMCAsrTxtBackVO setMCAsrTxtBackVO = gson.fromJson(gson.toJson(asrTxtParam_toout), SetMCAsrTxtBackVO.class);
                    setMCAsrTxtBackVO.setMtssid(mtssid);
                    ReqParam<SetMCAsrTxtBackVO> pparam=new ReqParam<SetMCAsrTxtBackVO>();
                    pparam.setParam(setMCAsrTxtBackVO);
                    if(mcCacheParam.getYwSystemType().equals(YWType.RECORD_TRM)){//avst版本的的笔录系统的类型
                        return remControl.setRercordAsrTxtBack(pparam);//调用对应的feign请求，返回TXT数据
                    }//以后还有其他的系统
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public RResult getMC(ReqParam<GetMCParam_out> param,RResult result) {
        GetMCVO getMCVO=new GetMCVO();

        GetMCParam_out getMCParam_out=param.getParam();
        String mtssid=getMCParam_out.getMtssid();
        if (StringUtils.isNotBlank(mtssid)){
            String iid=null;
            List<AsrTxtParam_toout> list=new ArrayList<AsrTxtParam_toout>();

            //根据mtssid获取会议所有的通道/用户
            EntityWrapper ew=new EntityWrapper();
            ew.eq("tu.mtssid",mtssid);
            List<Avstmt_tduserAll> tulist=avstmt_tduserMapper.getAvstmt_tduserAll(ew);
            if(null!=tulist&&tulist.size() > 0){
                for(Avstmt_tduserAll tu:tulist){
                    //根据会议ssid获取用户本次会议对话
                    ew=new EntityWrapper();
                    ew.orderBy("ordernum",true);
                    ew.eq("mtssid",mtssid);
                    ew.eq("mtuserssid",tu.getUserssid());
                    List<Avstmt_realtimrecord>  avstmt_realtimrecords = avstmt_realtimrecordMapper.selectList(ew);
                    if (null!=avstmt_realtimrecords&&avstmt_realtimrecords.size()>0){
                        for (Avstmt_realtimrecord a : avstmt_realtimrecords) {
                            AsrTxtParam_toout l=new AsrTxtParam_toout();
                            l.setStarttime(a.getStarttime().toString());
                            l.setAsrsort(a.getOrdernum());
                            l.setTxt(a.getTranslatext());
                            l.setUserssid(a.getMtuserssid());
                            l.setAsrtime(a.getString1());//时间
                            l.setAsrstartime(tu.getStarttime());
                            l.setSubtractime(tu.getStarttime()-tu.getStartrecordtime());
                            list.add(l);
                        }
                    }else{
                        LogUtil.intoLog(3,this.getClass(),"当前用户数据库没有记录一个语言识别数据----mtssid："+mtssid+"---mtuserssid:"+tu.getUserssid());
                    }
                }

            }else{
                LogUtil.intoLog(4,this.getClass(),"没有找到会议ssid对应的用户集合，avstmt_tduserMapper.getAvstmt_tduserAll is null----mtssid："+mtssid);
            }

            Base_mttodatasave base_mttodatasave=new Base_mttodatasave();
            base_mttodatasave.setMtssid(mtssid);
            base_mttodatasave=base_mttodatasaveMapper.selectOne(base_mttodatasave);

            if (null!=base_mttodatasave){
                iid=base_mttodatasave.getIid();
            }
            getMCVO.setIid(iid);
            getMCVO.setList(list);
            result.changeToTrue(getMCVO);
        }else{
            LogUtil.intoLog(this.getClass(),"参数为空");
        }
        return result;
    }

    @Override
    public RResult getMCaLLUserAsrTxtList(ReqParam<GetMCaLLUserAsrTxtListParam_out> param, RResult result) {
        GetMCaLLUserAsrTxtListParam_out getMCaLLUserAsrTxtListParam_out=param.getParam();
        String mtssid=getMCaLLUserAsrTxtListParam_out.getMtssid();
        if (StringUtils.isNotBlank(mtssid)){
            List<AsrTxtParam_toout> list=new ArrayList<AsrTxtParam_toout>();
            list=AsrForMCCache.getMCaLLUserAsrTxtList(mtssid);
            result.changeToTrue(list);
        }else{
            LogUtil.intoLog(this.getClass(),"参数为空");
        }
        return result;
    }

    @Override
    public RResult getMCState(ReqParam<GetMCStateParam_out> param, RResult result) {
        GetMCStateParam_out getMCStateParam_out=param.getParam();
        String mtssid=getMCStateParam_out.getMtssid();
        if (StringUtils.isNotBlank(mtssid)){
            MCCacheParam mcCacheParam =   MCCache.getMCCacheParam(mtssid);
            Integer mtstate =0;
            if (null!=mcCacheParam){
                 mtstate =  mcCacheParam.getMtstate();
                result.changeToTrue(mtstate);
                return result;
            }
        }
        return result;
    }

    @Override
    public RResult getPhssidByMTssid(ReqParam<GetPhssidByMTssidParam_out> param, RResult result) {
        GetPhssidByMTssidParam_out out=param.getParam();
        String mtssid=out.getMtssid();
        TdAndUserAndOtherCacheParam tdAndUserAndOtherCacheParam=MCCache.getMCCacheOneTDParamWithPh(mtssid);
        if(null!=tdAndUserAndOtherCacheParam&&null!=tdAndUserAndOtherCacheParam.getPolygraphssid()){
            String phssid=tdAndUserAndOtherCacheParam.getPolygraphssid();
            if (StringUtils.isNotBlank(phssid)){
                result.changeToTrue(phssid);
                return result;
            }
        }
        return result;
    }

    @Override
    public RResult getPHData(ReqParam<GetPHDataParam_out> param, RResult result) {
        GetPHDataParam_out param_out=param.getParam();
        String mtssid=param_out.getMtssid();
        TdAndUserAndOtherCacheParam tdAndUserAndOtherCacheParam=MCCache.getMCCacheOneTDParamWithPh(mtssid);
        if(null!=tdAndUserAndOtherCacheParam&&null!=tdAndUserAndOtherCacheParam.getPolygraphssid()){
            String phssid=tdAndUserAndOtherCacheParam.getPolygraphssid();
            PhDataParam_toout phDataParam_toout=PhForMCCache.getMTOneUserPhDataByPhSsid_lastoneData(mtssid,phssid);
            if(null!=phDataParam_toout){
                result.changeToTrue(phDataParam_toout);
            }
        }
        return result;
    }

    @Override
    public RResult getPHDataBack(ReqParam<GetPHDataBackParam_out> param, RResult result) {
        GetPHDataBackParam_out param_out=param.getParam();
        String mtssid=param_out.getMtssid();

      //根据会议，获取被询问人的人员设备通道ssid
      String mttduserssid=null;
      EntityWrapper tduserew=new EntityWrapper();
      tduserew.eq("mtssid",mtssid);
      List<Avstmt_tduser> avstmt_tdusers =  avstmt_tduserMapper.selectList(tduserew);
      if (null!=avstmt_tdusers&&avstmt_tdusers.size()>0){
          for (Avstmt_tduser avstmt_tduser : avstmt_tdusers) {
                if (avstmt_tduser.getUsertype()==2){
                    mttduserssid=avstmt_tduser.getSsid();
                    break;
                }
          }
      }

      if (null!=mttduserssid){
          EntityWrapper ew=new EntityWrapper();
          ew.eq("mttduserssid",mttduserssid);
          List<Avstmt_tdpolygraph> avstmt_tdpolygraphs = avstmt_tdpolygraphMapper.selectList(ew);
            if (null!=avstmt_tdpolygraphs&&avstmt_tdpolygraphs.size()==1){
                Avstmt_tdpolygraph avstmt_tdpolygraph=avstmt_tdpolygraphs.get(0);
                if (null!=avstmt_tdpolygraph){
                    String iid=avstmt_tdpolygraph.getIid();//获取对应iid
                    if (null!=iid){
                        Gson gson = new Gson();
                        RResult<GetSavepathVO> getsavepath_rr=new RResult<>();
                        GetSavePathParam getSavePathParam=new GetSavePathParam();
                        getSavePathParam.setSsType(SSType.AVST);
                        getSavePathParam.setIid(iid);
                        getsavepath_rr=equipmentControl.getSavePath(getSavePathParam);
                        if (null!=getsavepath_rr&&getsavepath_rr.getActioncode().equals(Code.SUCCESS.toString())){
                            LogUtil.intoLog(this.getClass(),"请求equipmentControl.getSavePath__成功");
                            System.out.println("_________________________________________________"+getsavepath_rr.getData());
                            GetSavepathVO getSavepathVO=gson.fromJson(gson.toJson(getsavepath_rr.getData()), GetSavepathVO.class);
                            if (null!=getSavepathVO){
                                List<RecordSavepathParam> recordList = getSavepathVO.getRecordList();
                                List<PHDataBackVoParam> phdatabackList=new ArrayList<>();
                                if (null!=recordList&&recordList.size()>0){
                                    for (RecordSavepathParam recordSavepathParam : recordList) {
                                        String savepath=recordSavepathParam.getSavepath();
                                        if (null!=savepath){
                                            try {
                                                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(savepath)),"UTF-8"));
                                                String lineTxt = null;
                                                while ((lineTxt = br.readLine()) != null) {
                                                    PHDataBackVoParam phDataBackVoParam=new PHDataBackVoParam();
                                                    String[] txts = lineTxt.split(";");
                                                    phDataBackVoParam.setNum(txts[0]);
                                                    phDataBackVoParam.setPhBataBackJson(txts[1]);
                                                    phdatabackList.add(phDataBackVoParam);
                                                }
                                                br.close();
                                            } catch (Exception e) {
                                                System.err.println("read errors :" + e);
                                            }
                                        }
                                    }
                                }
                                result.changeToTrue(phdatabackList);
                            }
                        }else {
                            LogUtil.intoLog(this.getClass(),"请求equipmentControl.getSavePath__出错");
                        }
                    }
                }else {
                    LogUtil.intoLog(this.getClass(),"getPHDataBack人员设备通道ssid为空__"+mttduserssid);
                }
            }

      }else {
          LogUtil.intoLog(this.getClass(),"avstmt_tdpolygraphMapper.selectOne人员设备通道ssid为空__"+mttduserssid);
      }
        return result;
    }

    @Override
    public RResult getFdrecordStarttimeByMTssid(ReqParam<GetFdrecordStarttimeByMTssidParam_out> param, RResult result) {
        GetFdrecordStarttimeByMTssidParam_out out=param.getParam();
        String mtssid=out.getMtssid();
        TdAndUserAndOtherCacheParam tdAndUserAndOtherCacheParam=MCCache.getMCCacheOneTDParamWithPh(mtssid);
        if(null!=tdAndUserAndOtherCacheParam&&null!=tdAndUserAndOtherCacheParam.getPolygraphssid()){
            long fdrecordstarttime=tdAndUserAndOtherCacheParam.getFdrecordstarttime();
            if (fdrecordstarttime>0){
                result.changeToTrue(fdrecordstarttime);
                return result;
            }
        }
        return result;
    }




}
