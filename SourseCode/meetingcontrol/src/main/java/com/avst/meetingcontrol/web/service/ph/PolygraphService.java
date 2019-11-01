package com.avst.meetingcontrol.web.service.ph;

import com.avst.meetingcontrol.common.conf.PHType;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.ph.AddOrUpdateToOutPolygraphParam;
import com.avst.meetingcontrol.feignclient.ec.req.ph.GetToOutPolygraphListParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PolygraphService extends BaseService {
    @Autowired
    private EquipmentControl equipmentControl;



    public void getToOutPolygraphList(RResult result, GetToOutPolygraphListParam param){
        param.setPhType(PHType.CMCROSS);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult ph_rr = equipmentControl.getToOutPolygraphList(reqParam);
        if (null!=ph_rr&&ph_rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(ph_rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"测谎仪getToOutPolygraphList__请求成功");
        }else {
            String msg=ph_rr.getMessage()==null?result.getMessage():ph_rr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"测谎仪getToOutPolygraphList__请求失败");
        }
        return;
    }


    public void getToOutPolygraphById(RResult result, GetToOutPolygraphListParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"getToOutPolygraphById__ssid"+ssid);
            return;
        }
        param.setPhType(PHType.CMCROSS);
        param.setSsid(ssid);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult ph_rr = equipmentControl.getToOutPolygraphById(reqParam);
        if (null!=ph_rr&&ph_rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(ph_rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"测谎仪getToOutPolygraphList__请求成功");
        }else {
            String msg=ph_rr.getMessage()==null?result.getMessage():ph_rr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"测谎仪getToOutPolygraphList__请求失败");
        }
        return;
    }


    public void addToOutPolygraph(RResult result, AddOrUpdateToOutPolygraphParam param){
        param.setPhType(PHType.CMCROSS);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult addph_rr =  equipmentControl.addToOutPolygraph(reqParam);
        if (null!=addph_rr&&addph_rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(addph_rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"测谎仪addToOutPolygraph__请求成功");
        }else {
            String msg=addph_rr.getMessage()==null?result.getMessage():addph_rr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"测谎仪addToOutFlushbonading__请求失败");
        }
        return;
    }


    public void updateToOutPolygraph(RResult result, AddOrUpdateToOutPolygraphParam param){
        String ssid=param.getSsid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"updateToOutPolygraph__ssid"+ssid);
            return;
        }

        param.setPhType(PHType.CMCROSS);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult updph_rr =  equipmentControl.updateToOutPolygraph(reqParam);
        if (null!=updph_rr&&updph_rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(updph_rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"测谎仪updateToOutPolygraph__请求成功");
        }else {
            String msg=updph_rr.getMessage()==null?result.getMessage():updph_rr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"测谎仪updateToOutPolygraph__请求失败");
        }
        return;
    }
}
