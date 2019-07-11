package com.avst.meetingcontrol.web.service;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_tduserMapper;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.Avstmt_tduserParam;
import com.avst.meetingcontrol.feignclient.ec.req.GetToOutFlushbonadingEttdByListParam;
import com.avst.meetingcontrol.web.req.GetAvstmt_tduserListParam;
import com.avst.meetingcontrol.web.vo.AvstmtTduserVO;
import com.avst.meetingcontrol.web.vo.GetAvstmt_tduserVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class Avstmt_tduserService extends BaseService {

    @Autowired
    private Avstmt_tduserMapper avstmt_tduserMapper;

    @Autowired
    private EquipmentControl equipmentControl;

    //会议人员设备通道信息
    public void getAvstmt_tduserList(RResult result, GetAvstmt_tduserListParam param){

        GetAvstmt_tduserVO vo = new GetAvstmt_tduserVO();

        if (StringUtils.isBlank(param.getMtssid())) {
            result.setMessage("会议的ssid不能为空");
            return;
        }

        //通过会议ssid找到对应的所有通道
        EntityWrapper ew = new EntityWrapper();
        ew.eq("mtssid", param.getMtssid());

        if (StringUtils.isNotBlank(param.getUsername())) {
            ew.like("username", param.getUsername());
        }

        if (StringUtils.isNotBlank(param.getUsertype())) {
            ew.eq("usertype", param.getUsertype());
        }

        if(StringUtils.isNotEmpty(param.getCreatetime_startdate()) && StringUtils.isNotEmpty(param.getCreatetime_enddate())){
            ew.between("DATE(createtime)", param.getCreatetime_startdate(), param.getCreatetime_enddate());
        }

        Integer count = avstmt_tduserMapper.selectCount(ew);//找到总条数
        param.setRecordCount(count);

        Page<AvstmtTduserVO> page = new Page<>(param.getCurrPage(), param.getPageSize());
        List<AvstmtTduserVO> pagelist = avstmt_tduserMapper.selectListPageAll(page, ew);

        //外部调用开始
        List<Avstmt_tduserParam> ssidList = new ArrayList();
        for (AvstmtTduserVO avstmtTduserVO : pagelist) {
            Avstmt_tduserParam avstmt_tduserParam = new Avstmt_tduserParam();
            avstmt_tduserParam.setEquipmenttdssid(avstmtTduserVO.getEquipmenttdssid());
            ssidList.add(avstmt_tduserParam);
        }

        //创建参数
        GetToOutFlushbonadingEttdByListParam flushbonadingEttdByListParam = new GetToOutFlushbonadingEttdByListParam();
        flushbonadingEttdByListParam.setPagelist(ssidList);
        flushbonadingEttdByListParam.setFdType("FD_AVST");
        ReqParam reqParam = new ReqParam();
        reqParam.setParam(flushbonadingEttdByListParam);

        try {
            RResult rResult = equipmentControl.getFlushbonadingEttdByMcSsid(reqParam);

            List<LinkedHashMap<String,String>> avstlist = (List<LinkedHashMap<String, String>>) rResult.getData();

            for (int i = 0; i < avstlist.size(); i++) {
                LinkedHashMap<String,String> listMap = avstlist.get(i);

                for (AvstmtTduserVO tduserVO : pagelist) {
                    //判断如果是相同ssid再把url放进去
                    if(tduserVO.getEquipmenttdssid().equalsIgnoreCase(listMap.get("equipmenttdssid"))){
                        String url = listMap.get("livingurl");
                        tduserVO.setLivingurl(url);
                        break;
                    }
                }
            }

        } catch (Exception e) {
//                e.printStackTrace();
            LogUtil.intoLog(4,this.getClass(),"equipmentControl.getFlushbonadingEttdByMcSsid 。run 远程调用获取直播地址");
        }


        vo.setPagelist(pagelist);
        vo.setPageparam(param);

        result.setData(vo);
        changeResultToSuccess(result);
    }



}
