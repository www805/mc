package com.avst.meetingcontrol.common.conf;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.base.vo.ControlInfoParamVO;
import com.avst.meetingcontrol.feignclient.zk.ZkControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时器任务
 * 注意：这种定时器一定要用try包一下，以免内存泄露或者线程异常不能释放
 */
@Component
public class SchedulerZk {

    @Autowired
    private ZkControl zkControl;

    @Value("${spring.application.name}")
    private String servername;

    @Value("${control.servser.url}")
    private String url;

    @Value("${control.servser.loginusername}")
    private String loginusername;

    @Value("${control.servser.loginpassword}")
    private String loginpassword;

    /**
     * 10秒心跳一次
     */
//    @Scheduled(cron = "0 05 1/1 * * *")
    @Scheduled(fixedRate = 10000)
    public void testTasks() {

        ReqParam<ControlInfoParamVO> param = new ReqParam<>();

        ControlInfoParamVO controlInfoParamVO = new ControlInfoParamVO();
        controlInfoParamVO.setServername(servername);//服务器注册名
        controlInfoParamVO.setServertitle("会议系统");//服务器中文名
        controlInfoParamVO.setUrl(url);
        controlInfoParamVO.setLoginusername(loginusername);
        controlInfoParamVO.setLoginpassword(loginpassword);
        controlInfoParamVO.setTotal_item(1);
        controlInfoParamVO.setUse_item(1);
//        controlInfoParamVO.setCreatetime(DateUtil.getDateAndMinute());//设置当前时间
        controlInfoParamVO.setStatus(1);//状态

        param.setParam(controlInfoParamVO);

        try {
            zkControl.getHeartbeat(param);
        } catch (Exception e) {
            LogUtil.intoLog(4,this.getClass(),"Scheduler.testTasks is error, 上报心跳到总控失败");
        }
    }

}
