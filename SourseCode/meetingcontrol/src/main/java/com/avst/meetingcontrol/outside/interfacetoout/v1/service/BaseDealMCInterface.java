package com.avst.meetingcontrol.outside.interfacetoout.v1.service;

import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.outside.interfacetoout.req.*;

/**
 * 对外开放的会议接口
 */
public interface BaseDealMCInterface {

    public RResult startMC(ReqParam<StartMCParam_out> param, RResult result);

    public RResult overMC(ReqParam<OverMCParam_out> param, RResult result);

    //public RResult getMCAsrTxtBack(ReqParam<GetMCAsrTxtBackParam_out> param, RResult result);

    public boolean setMCAsrTxtBack(ReqParam<SetMCAsrTxtBackParam_out> param);

    public RResult getMC(ReqParam<GetMCParam_out> param, RResult result);

    public RResult  getMCaLLUserAsrTxtList(ReqParam<GetMCaLLUserAsrTxtListParam_out> param, RResult result);

    public RResult  getMCState(ReqParam<GetMCStateParam_out> param, RResult result);

    public RResult  getPhssidByMTssid(ReqParam<GetPhssidByMTssidParam_out> param, RResult result);

    public RResult  getPHData(ReqParam<GetPHDataParam_out> param, RResult result);

    public RResult  getPHDataBack(ReqParam<GetPHDataBackParam_out> param,RResult result);

    public RResult getMc_model(ReqParam<GetMc_modelParam_out> param,RResult result);

    public RResult getTdByModelSsid(ReqParam<GetTdByModelSsidParam_out> param,RResult result);

    public RResult getMCCacheParamByMTssid(ReqParam<GetMCCacheParamByMTssidParam_out> param,RResult result);

    public RResult getTDCacheParamByMTssid(ReqParam<GetTDCacheParamByMTssidParam_out> param,RResult result);

    public RResult pauseOrContinueMC(PauseOrContinueMCParam_out param,RResult result);

    public RResult getTDByMTList(ReqParam<GetTDCacheParamByMTssidParam_out> param, RResult result);

    public RResult overAccidentMT(OverAccidentMTParam_out param, RResult result);

    public RResult setMCTagTxt(SetMCTagTxtParam_out param, RResult result);


}
