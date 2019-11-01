package com.avst.meetingcontrol.web.service.asr;

import com.avst.meetingcontrol.common.conf.ASRType;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_modeltd;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_modeltdMapper;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.asr.AddOrUpdateToOutAsrParam;
import com.avst.meetingcontrol.feignclient.ec.req.asr.GetToOutAsrListParam;
import com.avst.meetingcontrol.web.req.UpdateBoolParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsrService extends BaseService {
    @Autowired
    private EquipmentControl equipmentControl;

    @Autowired
    private Avstmt_modeltdMapper avstmt_modeltdMapper;

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
            String msg=rr.getMessage()==null?result.getMessage():rr.getMessage();
            result.setMessage(msg);
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
            String msg=rr.getMessage()==null?result.getMessage():rr.getMessage();
            result.setMessage(msg);
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
            String msg=addrr.getMessage()==null?result.getMessage():addrr.getMessage();
            result.setMessage(msg);
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
            String msg=updrr.getMessage()==null?result.getMessage():updrr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"语音识别updateToOutAsr__请求失败");
        }
        return;
    }

    //修改会议模板通道，是否需要测谎状态
    public void updateUsepolygraphBool(RResult result, UpdateBoolParam updateUseasrBoolParam) {

        UpdateBoolParam param = updateUseasrBoolParam;

        if (StringUtils.isBlank(param.getSsid())) {
            result.setMessage("语音识别的ssid不能为空");
            return;
        }
        if (null == param.getShieldbool()) {
            result.setMessage("语音识别是否开启的状态不能为空");
            return;
        }

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", param.getSsid());

        Avstmt_modeltd modeltd = new Avstmt_modeltd();
        modeltd.setUsepolygraph(param.getShieldbool());

        Integer update = avstmt_modeltdMapper.update(modeltd, ew);

        result.setData(update);
        result.changeToTrue();
    }

    //修改会议模板通道的语音识别开关状态
    public void updateUseasrBool(RResult result, UpdateBoolParam param) {

        if (StringUtils.isBlank(param.getSsid())) {
            result.setMessage("测谎状态的ssid不能为空");
            return;
        }
        if (null == param.getShieldbool()) {
            result.setMessage("测谎是否需要的状态不能为空");
            return;
        }

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", param.getSsid());

        Avstmt_modeltd modeltd = new Avstmt_modeltd();
        modeltd.setUseasr(param.getShieldbool());

        Integer update = avstmt_modeltdMapper.update(modeltd, ew);

        result.setData(update);
        result.changeToTrue();
    }

}
