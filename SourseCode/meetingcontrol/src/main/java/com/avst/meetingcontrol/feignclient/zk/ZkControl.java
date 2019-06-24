package com.avst.meetingcontrol.feignclient.zk;


import com.avst.meetingcontrol.common.util.baseaction.RResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "zk", url = "localhost:8079/")
public interface ZkControl {

    @RequestMapping( value = "/zk/getControlTime")
    @ResponseBody
    public RResult getControlTime();

}
