package com.avst.meetingcontrol.web.service.tts;

import com.avst.meetingcontrol.common.conf.ASRType;
import com.avst.meetingcontrol.common.conf.TTSType;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.tts.AddOrUpdateToOutTtsEtinfoParam;
import com.avst.meetingcontrol.feignclient.ec.req.tts.GetToOutTtsEtinfoListParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TtsService extends BaseService {

    @Autowired
    private EquipmentControl equipmentControl;

   
    public void getToOutTtsEtinfoList(RResult result, GetToOutTtsEtinfoListParam param){
        param.setTtsType(TTSType.AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult ph_rr = equipmentControl.getToOutTtsEtinfoList(reqParam);
        if (null!=ph_rr&&ph_rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(ph_rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"tts_getToOutTtsEtinfoList__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"tts_getToOutTtsEtinfoList__请求失败");
        }
    }

   
    public void getToOutTtsEtinfoById(RResult result, GetToOutTtsEtinfoListParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"getToOutTtsEtinfoById__ssid"+ssid);
            return;
        }
        param.setTtsType(TTSType.AVST);
        param.setSsid(ssid);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult rr = equipmentControl.getToOutTtsEtinfoById(reqParam);
        if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"tts_getToOutTtsEtinfoById__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"tts_getToOutTtsEtinfoById__请求失败");
        }
        return;
    }

    
    public void addToOutTtsEtinfo(RResult result, AddOrUpdateToOutTtsEtinfoParam param){
        param.setTtsType(TTSType.AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult addrr =  equipmentControl.addToOutTtsEtinfo(reqParam);
        if (null!=addrr&&addrr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(addrr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"tts_addToOutTtsEtinfo__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"tts_addToOutTtsEtinfo__请求失败");
        }
        return;
    }

   
    public void updateToOutTtsEtinfo(RResult result,AddOrUpdateToOutTtsEtinfoParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"updateToOutTtsEtinfo__ssid"+ssid);
            return;
        }

        param.setTtsType(TTSType.AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult updrr =  equipmentControl.updateToOutTtsEtinfo(reqParam);
        if (null!=updrr&&updrr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(updrr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"tts_updateToOutTtsEtinfo__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"tts_updateToOutTtsEtinfor__请求失败");
        }
        return;
    }
}
