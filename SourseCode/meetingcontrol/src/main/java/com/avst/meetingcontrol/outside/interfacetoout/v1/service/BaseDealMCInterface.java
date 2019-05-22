package com.avst.meetingcontrol.outside.interfacetoout.v1.service;

import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.outside.interfacetoout.req.GetMCAsrTxtBackParam_out;
import com.avst.meetingcontrol.outside.interfacetoout.req.OverMCParam_out;
import com.avst.meetingcontrol.outside.interfacetoout.req.SetMCAsrTxtBackParam_out;
import com.avst.meetingcontrol.outside.interfacetoout.req.StartMCParam_out;

/**
 * 对外开放的会议接口
 */
public interface BaseDealMCInterface {

    public RResult startMC(ReqParam<StartMCParam_out> param, RResult result);

    public RResult overMC(ReqParam<OverMCParam_out> param, RResult result);

    //public RResult getMCAsrTxtBack(ReqParam<GetMCAsrTxtBackParam_out> param, RResult result);

    public boolean setMCAsrTxtBack(ReqParam<SetMCAsrTxtBackParam_out> param);

}
