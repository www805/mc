package com.avst.meetingcontrol.web.service;


import com.avst.meetingcontrol.common.conf.ASRType;
import com.avst.meetingcontrol.common.conf.FDType;
import com.avst.meetingcontrol.common.conf.PHType;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_modeltd;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param.Avstmt_modeltdAll;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_modeltdMapper;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.OpenUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.asr.GetToOutAsrListParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutFlushbonadingEttdListParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutFlushbonadingListParam;
import com.avst.meetingcontrol.feignclient.ec.req.ph.GetToOutPolygraphListParam;
import com.avst.meetingcontrol.feignclient.ec.vo.asr.param.Asr_et_ettype;
import com.avst.meetingcontrol.feignclient.ec.vo.fd.param.Flushbonading_ettd;
import com.avst.meetingcontrol.feignclient.ec.vo.fd.param.Flushbonadinginfo;
import com.avst.meetingcontrol.feignclient.ec.vo.ph.param.PolygraphInfo;
import com.avst.meetingcontrol.web.req.*;
import com.avst.meetingcontrol.web.vo.GetAvstmt_modeltdByssidVO;
import com.avst.meetingcontrol.web.vo.GetAvstmt_modeltdListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Avstmt_modeltdService extends BaseService {
    private Gson gson = new Gson();

    @Autowired
    private Avstmt_modeltdMapper avstmt_modeltdMapper;

    @Autowired
    private EquipmentControl equipmentControl;

    public void getAvstmt_modeltdList(RResult result, GetAvstmt_modeltdListParam param) {
        GetAvstmt_modeltdListVO vo=new GetAvstmt_modeltdListVO();

        GetAvstmt_modeltdListParam pageparam = param;
        String mtmodelssid=pageparam.getMtmodelssid();
        if (StringUtils.isBlank(mtmodelssid)){
            result.setMessage("参数为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        ew.orderBy("createtime",false);

        if (null!=pageparam.getUsepolygraph()){
            ew.eq(true,"usepolygraph",pageparam.getUsepolygraph());
        }
        if (null!=pageparam.getUseasr()){
            ew.eq(true,"useasr",pageparam.getUseasr());
        }
        if (null!=pageparam.getGrade()){
            ew.eq(true,"grade",pageparam.getGrade());
        }

        if(StringUtils.isNotEmpty(param.getStartdate()) && StringUtils.isNotEmpty(param.getEnddate())){
            ew.between("DATE(createtime)", param.getStartdate(), param.getEnddate());
        }
        ew.eq(true,"mtmodelssid",mtmodelssid);

        int count=avstmt_modeltdMapper.selectCount(ew);
        param.setRecordCount(count);


        Page<Avstmt_modeltd> page=new Page<>(param.getCurrPage(),param.getPageSize());
        List<Avstmt_modeltd> oldpagelist=avstmt_modeltdMapper.selectPage(page,ew);
        List<Avstmt_modeltdAll> pagelist=new ArrayList<>();
        if(null!=oldpagelist&&oldpagelist.size()>0){
            pagelist =gson.fromJson(gson.toJson(oldpagelist), new TypeToken<List<Avstmt_modeltdAll>>(){}.getType());
        }
        if (null!=pagelist&&pagelist.size()>0){
            //
            for (Avstmt_modeltdAll avstmt_modeltdAll : pagelist) {
                String dqfdssid=avstmt_modeltdAll.getFdssid();
                String dqphssid=avstmt_modeltdAll.getPolygraphssid();
                String dqasrssid=avstmt_modeltdAll.getAsrssid();
                String dqtdssid=avstmt_modeltdAll.getTdssid();

                if (StringUtils.isNotBlank(dqfdssid)){
                    GetToOutFlushbonadingListParam param1=new GetToOutFlushbonadingListParam();
                    param1.setFdType(FDType.FD_AVST);
                    param1.setSsid(dqfdssid);
                    ReqParam reqParam1=new ReqParam();
                    reqParam1.setParam(param1);
                    RResult rr1 =  equipmentControl.getToOutFlushbonadingById(reqParam1);
                    if (null!=rr1&&rr1.getActioncode().equals(Code.SUCCESS.toString())&&null!=rr1.getData()){
                        Flushbonadinginfo flushbonadinginfo=gson.fromJson(gson.toJson(rr1.getData()), Flushbonadinginfo.class);
                        avstmt_modeltdAll.setFdexplain(flushbonadinginfo.getExplain());
                        LogUtil.intoLog(this.getClass(),"审讯设备getToOutFlushbonadingById__请求成功");
                    }else {
                        LogUtil.intoLog(this.getClass(),"审讯设备getToOutFlushbonadingById__请求失败");
                    }
                }
                if (StringUtils.isNotBlank(dqphssid)){
                    GetToOutPolygraphListParam param2=new GetToOutPolygraphListParam();
                    param2.setPhType(PHType.CMCROSS);
                    param2.setSsid(dqphssid);
                    ReqParam reqParam2=new ReqParam();
                    reqParam2.setParam(param2);
                    RResult rr2 =  equipmentControl.getToOutPolygraphById(reqParam2);
                    if (null!=rr2&&rr2.getActioncode().equals(Code.SUCCESS.toString())&&null!=rr2.getData()){
                        PolygraphInfo polygraphInfo=gson.fromJson(gson.toJson(rr2.getData()), PolygraphInfo.class);
                        avstmt_modeltdAll.setPhexplain(polygraphInfo.getExplain());
                        LogUtil.intoLog(this.getClass(),"测谎仪getToOutPolygraphById__请求成功");
                    }else {
                        LogUtil.intoLog(this.getClass(),"测谎仪getToOutPolygraphById__请求失败");
                    }
                }
                if (StringUtils.isNotBlank(dqasrssid)){
                    GetToOutAsrListParam param3=new GetToOutAsrListParam();
                    param3.setAsrtype(ASRType.AVST);
                    param3.setSsid(dqasrssid);
                    ReqParam reqParam3=new ReqParam();
                    reqParam3.setParam(param3);
                    RResult rr3 =  equipmentControl.getToOutAsrById(reqParam3);
                    if (null!=rr3&&rr3.getActioncode().equals(Code.SUCCESS.toString())&&null!=rr3.getData()){
                        Asr_et_ettype asr_et_ettype=gson.fromJson(gson.toJson(rr3.getData()), Asr_et_ettype.class);
                        avstmt_modeltdAll.setAsrexplain(asr_et_ettype.getExplain());
                        LogUtil.intoLog(this.getClass(),"语音识别getToOutAsrById__请求成功");
                    }else {
                        LogUtil.intoLog(this.getClass(),"语音识别getToOutAsrById__请求失败");
                    }
                }
                if (StringUtils.isNotBlank(dqtdssid)){
                    GetToOutFlushbonadingEttdListParam param4=new GetToOutFlushbonadingEttdListParam();
                    param4.setFdType(FDType.FD_AVST);
                    param4.setSsid(dqtdssid);
                    ReqParam reqParam4=new ReqParam();
                    reqParam4.setParam(param4);
                    RResult rr4 =  equipmentControl.getToOutFlushbonadingEttdById(reqParam4);
                    if (null!=rr4&&rr4.getActioncode().equals(Code.SUCCESS.toString())&&null!=rr4.getData()){
                        Flushbonading_ettd flushbonading_ettd=gson.fromJson(gson.toJson(rr4.getData()), Flushbonading_ettd.class);
                        avstmt_modeltdAll.setTdexplain(flushbonading_ettd.getPullflowurl());
                        LogUtil.intoLog(this.getClass(),"设备通道getToOutFlushbonadingEttdById__请求成功");
                    }else {
                        LogUtil.intoLog(this.getClass(),"设备通道getToOutFlushbonadingEttdById__请求失败");
                    }
                }
            }
        }
        vo.setPagelist(pagelist);
        vo.setPageparam(pageparam);
        result.setData(vo);
        changeResultToSuccess(result);
        return;
    }

    public void getAvstmt_modeltdByssid(RResult result, GetAvstmt_modeltdByssidParam param){
        GetAvstmt_modeltdByssidVO vo= new GetAvstmt_modeltdByssidVO();
        String ssid=param.getSsid();
        LogUtil.intoLog(this.getClass(),"getAvstmt_modeltdByssid__ssid__"+ssid);
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            return;
        }
        Avstmt_modeltd avstmt_modeltd=new Avstmt_modeltd();
        avstmt_modeltd.setSsid(ssid);
        avstmt_modeltd=avstmt_modeltdMapper.selectOne(avstmt_modeltd);
        if (null!=avstmt_modeltd){
            vo.setAvstmt_modeltd(avstmt_modeltd);
            result.setData(vo);
            changeResultToSuccess(result);
            return;
        }
        return;
    }

    public void updateAvstmt_modeltd(RResult result, UpdateAvstmt_modeltdParam param){
        String ssid=param.getSsid();
        LogUtil.intoLog(this.getClass(),"updateAvstmt_modeltd__ssid__"+ssid);
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            return;
        }
        EntityWrapper ew=new EntityWrapper();
        ew.eq("ssid",ssid);
        int avstmt_modeltdMapper_updatebool = avstmt_modeltdMapper.update(param,ew);
        LogUtil.intoLog(this.getClass(),"avstmt_modeltdMapper_updatebool__"+avstmt_modeltdMapper_updatebool);
        if (avstmt_modeltdMapper_updatebool>0){
            result.setData(1);
            changeResultToSuccess(result);
        }
        return;
    }

    public void addAvstmt_modeltd(RResult result, AddAvstmt_modeltdParam param){
        param.setCreatetime(new Date());
        param.setSsid(OpenUtil.getUUID_32());
        int avstmt_modeltdMapper_insertbool = avstmt_modeltdMapper.insert(param);
        LogUtil.intoLog(this.getClass(),"avstmt_modeltdMapper_insertbool__"+avstmt_modeltdMapper_insertbool);
        if (avstmt_modeltdMapper_insertbool>0){
            result.setData(1);
            changeResultToSuccess(result);
        }
        return;
    }
    public void delAvstmt_modeltd(RResult result, DelAvstmt_modeltdParam param){
        String ssid=param.getSsid();
        LogUtil.intoLog(this.getClass(),"delAvstmt_modeltd__ssid__"+ssid);
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            return;
        }
        EntityWrapper ew=new EntityWrapper();
        ew.eq("ssid",ssid);

        int avstmt_modeltdMapper_deletebool = avstmt_modeltdMapper.delete(ew);
        LogUtil.intoLog(this.getClass(),"avstmt_modeltdMapper_deletebool__"+avstmt_modeltdMapper_deletebool);
        if (avstmt_modeltdMapper_deletebool>0){
            result.setData(1);
            changeResultToSuccess(result);
        }
        return;
    }




}
