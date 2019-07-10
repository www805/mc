package com.avst.meetingcontrol.web.service.flushbonading;

import com.avst.meetingcontrol.common.conf.FDType;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.fd.AddOrUpdateToOutFlushbonadingParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutFlushbonadingEttdListParam;
import com.avst.meetingcontrol.feignclient.ec.req.fd.GetToOutFlushbonadingListParam;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlushbonadingService extends BaseService {

    @Autowired
    private EquipmentControl equipmentControl;

    private Gson gson=new Gson();


    public void getToOutFlushbonadingList(RResult result,GetToOutFlushbonadingListParam param){
        param.setFdType(FDType.FD_AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult fh_rr = equipmentControl.getToOutFlushbonadingList(reqParam);
       if (null!=fh_rr&&fh_rr.getActioncode().equals(Code.SUCCESS.toString())){
           result.setData(fh_rr.getData());
           changeResultToSuccess(result);
           LogUtil.intoLog(this.getClass(),"审讯设备getToOutFlushbonadingList__请求成功");
       }else {
           LogUtil.intoLog(this.getClass(),"审讯设备getToOutFlushbonadingList__请求失败");
       }
       return;
    }


    public void getToOutFlushbonadingById(RResult result,GetToOutFlushbonadingListParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"getToOutFlushbonadingById__ssid"+ssid);
            return;
        }
        param.setFdType(FDType.FD_AVST);
        param.setSsid(ssid);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult fh_rr = equipmentControl.getToOutFlushbonadingById(reqParam);
        if (null!=fh_rr&&fh_rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(fh_rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"审讯设备getToOutFlushbonadingById__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"审讯设备getToOutFlushbonadingById__请求失败");
        }
        return;
    }


    public void addToOutFlushbonading(RResult result,AddOrUpdateToOutFlushbonadingParam param){
        param.setFdType(FDType.FD_AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult addfh_rr =  equipmentControl.addToOutFlushbonading(reqParam);
        if (null!=addfh_rr&&addfh_rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(addfh_rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"审讯设备addToOutFlushbonading__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"审讯设备addToOutFlushbonading__请求失败");
        }
        return;
    }


    public void updateToOutFlushbonading(RResult result, AddOrUpdateToOutFlushbonadingParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"updateToOutFlushbonading__ssid"+ssid);
            return;
        }

        param.setFdType(FDType.FD_AVST);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult updfh_rr =  equipmentControl.updateToOutFlushbonading(reqParam);
        if (null!=updfh_rr&&updfh_rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(updfh_rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"审讯设备updateToOutFlushbonading__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"审讯设备updateToOutFlushbonading__请求失败");
        }
        return;
    }
}
