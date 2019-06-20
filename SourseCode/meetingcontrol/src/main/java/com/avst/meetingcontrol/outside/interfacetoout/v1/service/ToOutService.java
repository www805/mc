package com.avst.meetingcontrol.outside.interfacetoout.v1.service;

import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.outside.interfacetoout.vo.ToOutVO;
import org.springframework.stereotype.Service;

@Service
public class ToOutService extends BaseService {

    public RResult checkClient(RResult rresult, ReqParam param){
        ToOutVO toOutVO=new ToOutVO();
        toOutVO.setTotal_item(1);
        toOutVO.setUse_item(1);
        rresult.setData(toOutVO);
        changeResultToSuccess(rresult);
        return  rresult;
    }
}
