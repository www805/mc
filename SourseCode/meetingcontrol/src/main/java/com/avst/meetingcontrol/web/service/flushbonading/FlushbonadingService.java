package com.avst.meetingcontrol.web.service.flushbonading;

import com.avst.meetingcontrol.common.conf.FDType;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.fd.*;
import com.avst.meetingcontrol.web.req.GetMiddlewareFTPParam;
import com.avst.meetingcontrol.web.req.GetptdjconstParam;
import com.avst.meetingcontrol.web.req.SetMiddlewareFTPParam;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String ssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            LogUtil.intoLog(this.getClass(),"getToOutFlushbonadingById__ssid"+ssid);
            return;
        }
        param.setFdType(FDType.FD_AVST);
        param.setFlushbonadingetinfossid(ssid);
        ReqParam reqParam=new ReqParam();
        reqParam.setParam(param);
        RResult fh_rr = equipmentControl.getToOutFlushbonadingById(reqParam);
        if (null!=fh_rr&&fh_rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(fh_rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"审讯设备getToOutFlushbonadingById__请求成功");
        }else {
            String msg=fh_rr.getMessage()==null?result.getMessage():fh_rr.getMessage();
            result.setMessage(msg);
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
            String msg=addfh_rr.getMessage()==null?result.getMessage():addfh_rr.getMessage();
            result.setMessage(msg);
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
            String msg=updfh_rr.getMessage()==null?result.getMessage():updfh_rr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"审讯设备updateToOutFlushbonading__请求失败");
        }
        return;
    }



    public void getptdjconst(RResult result, GetptdjconstParam param){
         String flushbonadingetinfossid=param.getFlushbonadingetinfossid();//嵌入式设备ssid
         boolean mustUpdateBool=param.isMustUpdateBool();//是否强制修改数据库中保存的片头

        if (StringUtils.isNotEmpty(flushbonadingetinfossid)){
            ReqParam reqParam=new ReqParam<>();
            GetToOutptdjconstParam getToOutptdjconstParam =new GetToOutptdjconstParam();
            getToOutptdjconstParam.setFdType(FDType.FD_AVST);
            getToOutptdjconstParam.setFlushbonadingetinfossid(flushbonadingetinfossid);
            getToOutptdjconstParam.setMustUpdateBool(mustUpdateBool);
            reqParam.setParam(getToOutptdjconstParam);
            RResult rr =  equipmentControl.getptdjconst(reqParam);
            if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
                result.setData(rr.getData());
                changeResultToSuccess(result);
                LogUtil.intoLog(this.getClass(),"审讯设备getptdjconst__请求成功");
            }else {
                String msg=rr.getMessage()==null?result.getMessage():rr.getMessage();
                result.setMessage(msg);
                LogUtil.intoLog(this.getClass(),"审讯设备getptdjconst__请求失败");
            }
        }
        return;
    }
    public void getMiddleware_FTP(RResult result, GetMiddlewareFTPParam param){
         String fdssid=param.getFlushbonadingetinfossid();
        if (StringUtils.isNotEmpty(fdssid)){
            ReqParam reqParam=new ReqParam<>();
            GetToOutMiddleware_FTPParam getMiddleware_ftpParam=new GetToOutMiddleware_FTPParam();
            getMiddleware_ftpParam.setFlushbonadingetinfossid(fdssid);
            getMiddleware_ftpParam.setFdType(FDType.FD_AVST);
            reqParam.setParam(getMiddleware_ftpParam);
            RResult rr =  equipmentControl.getToOutMiddleware_FTP(reqParam);
            if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
                result.setData(rr.getData());
                changeResultToSuccess(result);
                LogUtil.intoLog(this.getClass(),"审讯设备getToOutMiddleware_FTP__请求成功");
            }else {
                String msg=rr.getMessage()==null?result.getMessage():rr.getMessage();
                result.setMessage(msg);
                LogUtil.intoLog(this.getClass(),"审讯设备getToOutMiddleware_FTP__请求失败");
            }
        }else {
            result.setMessage("设备未找到");
            LogUtil.intoLog(this.getClass(),"审讯设备ssid is null"+fdssid);
        }
        return;
    }

    public void setMiddleware_FTP(RResult result, SetMiddlewareFTPParam param){
        ReqParam reqParam=new ReqParam<>();
        param.setFdType(FDType.FD_AVST);
        SetToOutMiddleware_FTPParam setMiddleware_ftpParam=gson.fromJson(gson.toJson(param),SetToOutMiddleware_FTPParam.class);
        reqParam.setParam(setMiddleware_ftpParam);
        RResult rr =  equipmentControl.setToOutMiddleware_FTP(reqParam);
        if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
            result.setData(rr.getData());
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"审讯设备setToOutMiddleware_FTP__请求成功");
        }else {
            String msg=rr.getMessage()==null?result.getMessage():rr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"审讯设备setToOutMiddleware_FTP__请求失败");
        }
        return;
    }
}
