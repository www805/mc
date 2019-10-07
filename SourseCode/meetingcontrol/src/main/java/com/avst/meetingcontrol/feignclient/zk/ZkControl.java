package com.avst.meetingcontrol.feignclient.zk;


import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.base.vo.ControlInfoParamVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "zk")
public interface ZkControl {

    //获取注册的服务器
    @RequestMapping( value = "/zk/getControlInfoAll")
    @ResponseBody
    public RResult getControlInfoAll();

    //获取服务器时间
    @RequestMapping( value = "/zk/getControlTime")
    @ResponseBody
    public RResult getControlTime();

    @RequestMapping("/zk/getHeartbeat")
    public RResult getHeartbeat(@RequestBody ReqParam<ControlInfoParamVO> param);

}
