package com.avst.meetingcontrol.feignclient.bl;

import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.outside.interfacetoout.cache.param.AsrTxtParam_toout;
import com.avst.meetingcontrol.outside.interfacetoout.vo.SetMCAsrTxtBackVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 笔录系统的接口
 */
@FeignClient("trm")
public interface REMControl {

    //trm
    @RequestMapping( value = "/v1/police/out/outtRercordAsrTxtBack")
    @ResponseBody
    public boolean setRercordAsrTxtBack(@RequestBody ReqParam<SetMCAsrTxtBackVO> param);

    //获取trm当前登录的用户信息
    @RequestMapping("/trm/v1/getUserPwd")
    public RResult getUserPwd(@RequestBody ReqParam param);

}
