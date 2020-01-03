package com.avst.meetingcontrol.web.service;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_tduserMapper;
import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_mtinfo;
import com.avst.meetingcontrol.common.datasourse.publicsourse.mapper.Base_mtinfoMapper;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.web.req.GetAvstmt_tduserListParam;
import com.avst.meetingcontrol.web.req.GetBase_mtinfoListParam;
import com.avst.meetingcontrol.web.vo.AvstmtTduserVO;
import com.avst.meetingcontrol.web.vo.GetAvstmt_tduserVO;
import com.avst.meetingcontrol.web.vo.GetBase_mtinfoListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class Base_mtinfoService extends BaseService {
    @Autowired
    private Base_mtinfoMapper base_mtinfoMapper;

    //会议列表
    public void getBase_mtinfoList(RResult result, GetBase_mtinfoListParam param){
        GetBase_mtinfoListVO vo=new GetBase_mtinfoListVO();

        GetBase_mtinfoListParam pageparam=param;

        EntityWrapper ew=new EntityWrapper();
        ew.orderBy("createtime",false);

        if (null!=pageparam.getMeetingtype()){
            ew.eq(true,"meetingtype",pageparam.getMeetingtype());
        }
        if (null!=pageparam.getOpened()){
            ew.eq(true,"opened",pageparam.getOpened());
        }
        if (null!=pageparam.getUserecord()){
            ew.eq(true,"userecord",pageparam.getUserecord());
        }
        if (null!=pageparam.getMtstate()){
            ew.eq(true,"`mtstate`",pageparam.getMtstate());
        }
        if (null!=pageparam.getUserecord()){
            ew.eq(true,"userecord",pageparam.getUserecord());
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(StringUtils.isNotEmpty(param.getRecordstarttime_startdate()) && StringUtils.isNotEmpty(param.getRecordstarttime_enddate())){

            try {
                Date startdate = simpleDateFormat.parse(param.getRecordstarttime_startdate());
                long ts = startdate.getTime();
                String head = String.valueOf(ts);

                Date lastdate = simpleDateFormat.parse(param.getRecordstarttime_enddate());
                ts = lastdate.getTime();
                String end = String.valueOf(ts);

                ew.between("mtstarttime", head, end);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.isNotEmpty(param.getRecordendtime_startdate()) && StringUtils.isNotEmpty(param.getRecordendtime_enddate())){
            try {
                Date startdate = simpleDateFormat.parse(param.getRecordendtime_startdate());
                long ts = startdate.getTime();
                String head = String.valueOf(ts);

                Date lastdate = simpleDateFormat.parse(param.getRecordendtime_enddate());
                ts = lastdate.getTime();
                String end = String.valueOf(ts);

                ew.between("mtendtime", head, end);

            } catch (ParseException e) {
                e.printStackTrace();
            }
//            ew.between("DADE(mtendtime)", param.getRecordendtime_startdate(), param.getRecordendtime_enddate());
        }

        int count=base_mtinfoMapper.selectCount(ew);
        param.setRecordCount(count);

        Page<Base_mtinfo> page=new Page<>(param.getCurrPage(),param.getPageSize());
        List<Base_mtinfo> pagelist=base_mtinfoMapper.selectPage(page,ew);


        vo.setPagelist(pagelist);
        vo.setPageparam(pageparam);


        result.setData(vo);
        changeResultToSuccess(result);

        return;
    }




}
