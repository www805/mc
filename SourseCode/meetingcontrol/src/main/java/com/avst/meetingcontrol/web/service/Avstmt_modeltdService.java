package com.avst.meetingcontrol.web.service;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_modeltd;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param.Avstmt_modeltdAll;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_modeltdMapper;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.OpenUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.web.req.AddAvstmt_modeltdParam;
import com.avst.meetingcontrol.web.req.GetAvstmt_modeltdByssidParam;
import com.avst.meetingcontrol.web.req.GetAvstmt_modeltdListParam;
import com.avst.meetingcontrol.web.req.UpdateAvstmt_modeltdParam;
import com.avst.meetingcontrol.web.vo.GetAvstmt_modeltdByssidVO;
import com.avst.meetingcontrol.web.vo.GetAvstmt_modeltdListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Avstmt_modeltdService extends BaseService {
    private Gson gson = new Gson();

    @Autowired
    private Avstmt_modeltdMapper avstmt_modeltdMapper;

    public void getAvstmt_modeltdList(RResult result, GetAvstmt_modeltdListParam param){
        GetAvstmt_modeltdListVO vo=new GetAvstmt_modeltdListVO();

        GetAvstmt_modeltdListParam pageparam = param;
        String mtmodelssid=pageparam.getMtmodelssid();
        if (StringUtils.isBlank(mtmodelssid)){
            result.setMessage("参数为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        ew.orderBy("createtime",false);

        if (null!=pageparam.getUsepolygraph()){
            ew.eq(true,"usepolygraph",pageparam.getUsepolygraph());
        }
        if (null!=pageparam.getUseasr()){
            ew.eq(true,"useasr",pageparam.getUseasr());
        }
        if (null!=pageparam.getGrade()){
            ew.eq(true,"grade",pageparam.getGrade());
        }

        if(StringUtils.isNotEmpty(param.getStartdate()) && StringUtils.isNotEmpty(param.getEnddate())){
            ew.between("DATE(createtime)", param.getStartdate(), param.getEnddate());
        }
        ew.eq(true,"mtmodelssid",mtmodelssid);

        int count=avstmt_modeltdMapper.selectCount(ew);
        param.setRecordCount(count);


        Page<Avstmt_modeltd> page=new Page<>(param.getCurrPage(),param.getPageSize());
        List<Avstmt_modeltd> oldpagelist=avstmt_modeltdMapper.selectPage(page,ew);
        List<Avstmt_modeltdAll> pagelist=new ArrayList<>();
        if(null!=oldpagelist&&oldpagelist.size()>0){
            pagelist =gson.fromJson(gson.toJson(oldpagelist), new TypeToken<List<Avstmt_modeltdAll>>(){}.getType());
        }
        if (null!=pagelist&&pagelist.size()>0){
            //
        }
        vo.setPagelist(pagelist);
        vo.setPageparam(pageparam);
        result.setData(vo);
        changeResultToSuccess(result);
        return;
    }

    public void getAvstmt_modeltdByssid(RResult result, GetAvstmt_modeltdByssidParam param){
        GetAvstmt_modeltdByssidVO vo= new GetAvstmt_modeltdByssidVO();
        String ssid=param.getSsid();
        LogUtil.intoLog(this.getClass(),"getAvstmt_modeltdByssid__ssid__"+ssid);
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            return;
        }
        Avstmt_modeltd avstmt_modeltd=new Avstmt_modeltd();
        avstmt_modeltd.setSsid(ssid);
        avstmt_modeltd=avstmt_modeltdMapper.selectOne(avstmt_modeltd);
        if (null!=avstmt_modeltd){
            vo.setAvstmt_modeltd(avstmt_modeltd);
            result.setData(vo);
            changeResultToSuccess(result);
            return;
        }
        return;
    }

    public void updateAvstmt_modeltd(RResult result, UpdateAvstmt_modeltdParam param){
        String ssid=param.getSsid();
        LogUtil.intoLog(this.getClass(),"updateAvstmt_modeltd__ssid__"+ssid);
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            return;
        }
        EntityWrapper ew=new EntityWrapper();
        ew.eq("ssid",ssid);
        int avstmt_modeltdMapper_updatebool = avstmt_modeltdMapper.update(param,ew);
        LogUtil.intoLog(this.getClass(),"avstmt_modeltdMapper_updatebool__"+avstmt_modeltdMapper_updatebool);
        if (avstmt_modeltdMapper_updatebool>0){
            result.setData(1);
            changeResultToSuccess(result);
        }
        return;
    }

    public void addAvstmt_modeltd(RResult result, AddAvstmt_modeltdParam param){
        param.setCreatetime(new Date());
        param.setSsid(OpenUtil.getUUID_32());
        int avstmt_modeltdMapper_insertbool = avstmt_modeltdMapper.insert(param);
        LogUtil.intoLog(this.getClass(),"avstmt_modeltdMapper_insertbool__"+avstmt_modeltdMapper_insertbool);
        if (avstmt_modeltdMapper_insertbool>0){
            result.setData(1);
            changeResultToSuccess(result);
        }
        return;
    }


}
