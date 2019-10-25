package com.avst.meetingcontrol.outside.dealoutinterface.avstmc.v1.action;

import com.avst.meetingcontrol.common.util.SpringUtil;
import com.avst.meetingcontrol.common.util.baseaction.RRParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.InitMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.OverMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.req.StartMCParam;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.v1.service.DealAvstMCImpl;
import com.avst.meetingcontrol.outside.dealoutinterface.avstmc.vo.InitMCVO;

/**
 * 自实现的会议管理系统的接口
 */
public class AvstMCImpl {

    private static DealAvstMCImpl dealAvstMC=null;

    private static DealAvstMCImpl getDealAvstMCImpl(){
        if(null==dealAvstMC){
            dealAvstMC=SpringUtil.getBean(DealAvstMCImpl.class);
        }
        return dealAvstMC;
    };


    /**
     * 初始化会议
     * 返回会议ssid
     * @return
     */
    public static RRParam<InitMCVO> initMC(InitMCParam param){

        RRParam<InitMCVO> rrParam=new RRParam<InitMCVO>();
        return getDealAvstMCImpl().initMC(param,rrParam);
    }

    /**
     * 开始会议
     * @return
     */
    public static RRParam startMC(StartMCParam param){

        RRParam rrParam=new RRParam<Boolean>();
        return getDealAvstMCImpl().startMC(param,rrParam);
    }

    /**
     * 结束会议
     * @return
     */
    public static RRParam<Boolean> overMC(OverMCParam param){

        RRParam<Boolean> rrParam=new RRParam<Boolean>();
        return getDealAvstMCImpl().overMC(param,rrParam);
    }

    /**
     * 结束会议（对于意外时间的处理）
     * @return
     */
    public static RRParam<Boolean> overMC_Accident(OverMCParam param){

        RRParam<Boolean> rrParam=new RRParam<Boolean>();
        return getDealAvstMCImpl().overMC_Accident(param,rrParam);
    }
}
