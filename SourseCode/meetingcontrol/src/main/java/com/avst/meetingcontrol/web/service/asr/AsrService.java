package com.avst.meetingcontrol.web.service.asr;

import com.avst.meetingcontrol.common.conf.ASRType;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.asr.AddOrUpdateToOutAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.asr.GetToOutAsrListParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsrService extends BaseService {
    @Autowired
    private EquipmentControl equipmentControl;


    public void getToOutAsrList(RResult result, GetToOutAsrListParam param){
        param.setAsrtype(ASRType.AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult rr = equipmentControl.getToOutAsrList(reqParam);
        if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"语音识别getToOutAsrList__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"语音识别getToOutAsrList__请求失败");
        }
        return;
    }


    public void getToOutAsrById(RResult result, GetToOutAsrListParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"getToOutAsrById__ssid"+ssid);
            return;
        }
        param.setAsrtype(ASRType.AVST);
        param.setSsid(ssid);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult rr = equipmentControl.getToOutAsrById(reqParam);
        if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"语音识别getToOutAsrById__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"语音识别getToOutAsrById__请求失败");
        }
        return;
    }


    public void addToOutAsr(RResult result,AddOrUpdateToOutAsrParam param){
        param.setAsrtype(ASRType.AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult addrr =  equipmentControl.addToOutAsr(reqParam);
        if (null!=addrr&&addrr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(addrr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"语音识别addToOutAsr__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"语音识别addToOutAsr__请求失败");
        }
        return;
    }


    public void updateToOutAsr(RResult result, AddOrUpdateToOutAsrParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"updateToOutAsr__ssid"+ssid);
            return;
        }

        param.setAsrtype(ASRType.AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult updrr =  equipmentControl.updateToOutAsr(reqParam);
        if (null!=updrr&&updrr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(updrr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"语音识别updateToOutAsr__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"语音识别updateToOutAsr__请求失败");
        }
        return;
    }



}
