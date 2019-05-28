package com.avst.meetingcontrol.feignclient.bl;

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
@FeignClient(value="trm",url="192.168.17.178:8080/")
public interface REMControl {

    //trm
    @RequestMapping( value = "/v1/police/out/outtRercordAsrTxtBack")
    @ResponseBody
    public boolean setRercordAsrTxtBack(@RequestBody ReqParam<SetMCAsrTxtBackVO> param);


}
