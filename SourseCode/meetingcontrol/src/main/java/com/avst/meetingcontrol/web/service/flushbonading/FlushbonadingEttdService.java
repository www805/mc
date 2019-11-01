package com.avst.meetingcontrol.web.service.flushbonading;

import com.avst.meetingcontrol.common.conf.ASRType;
import com.avst.meetingcontrol.common.conf.FDType;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.fd.AddOrUpdateToOutFlushbonadingEttdParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutFlushbonadingEttdListParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlushbonadingEttdService extends BaseService {
    @Autowired
    private EquipmentControl equipmentControl;
    
    public void getToOutFlushbonadingEttdList(RResult result, GetToOutFlushbonadingEttdListParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"getToOutFlushbonadingEttdList__ssid"+ssid);
            return;
        }

        param.setFdType(FDType.FD_AVST);
        param.setSsid(ssid);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult rr = equipmentControl.getToOutFlushbonadingEttdList(reqParam);
        if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"设备通道getToOutFlushbonadingEttdList__请求成功");
        }else {
            String msg=rr.getMessage()==null?result.getMessage():rr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"设备通道getToOutFlushbonadingEttdList__请求失败");
        }
        return;
    }


   
    public void getToOutFlushbonadingEttdById(RResult result, GetToOutFlushbonadingEttdListParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"getToOutFlushbonadingEttdById__ssid"+ssid);
            return;
        }
        param.setFdType(FDType.FD_AVST);
        param.setSsid(ssid);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult rr = equipmentControl.getToOutFlushbonadingEttdById(reqParam);
        if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"设备通道getToOutFlushbonadingEttdById__请求成功");
        }else {
            String msg=rr.getMessage()==null?result.getMessage():rr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"设备通道getToOutFlushbonadingEttdById__请求失败");
        }
        return;
    }

   
    public void addToOutFlushbonadingEttd(RResult result, AddOrUpdateToOutFlushbonadingEttdParam param){
        param.setFdType(FDType.FD_AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult addrr =  equipmentControl.addToOutFlushbonadingEttd(reqParam);
        if (null!=addrr&&addrr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(addrr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"设备通道addToOutFlushbonadingEttd__请求成功");
        }else {
            String msg=addrr.getMessage()==null?result.getMessage():addrr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"设备通道addToOutFlushbonadingEttd__请求失败");
        }
        return;
     }

   
    public void updateToOutFlushbonadingEttd(RResult result, AddOrUpdateToOutFlushbonadingEttdParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"updateToOutFlushbonadingEttd__ssid"+ssid);
            return;
        }

        param.setFdType(FDType.FD_AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult updrr =  equipmentControl.updateToOutFlushbonadingEttd(reqParam);
        if (null!=updrr&&updrr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(updrr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"设备通道updateToOutFlushbonadingEttd__请求成功");
        }else {
            String msg=updrr.getMessage()==null?result.getMessage():updrr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"设备通道updateToOutFlushbonadingEttd__请求失败");
        }
        return;
    }

}
