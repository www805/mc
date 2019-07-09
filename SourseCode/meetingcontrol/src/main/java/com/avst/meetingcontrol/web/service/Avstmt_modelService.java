package com.avst.meetingcontrol.web.service;

import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_model;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param.Avstmt_modelAll;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.param.Avstmt_modeltdAll;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_modelMapper;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_modeltdMapper;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.OpenUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.web.req.AddAvstmt_modelParam;
import com.avst.meetingcontrol.web.req.GetAvstmt_modelByssidParam;
import com.avst.meetingcontrol.web.req.GetAvstmt_modelListParam;
import com.avst.meetingcontrol.web.req.UpdateAvstmt_modelParam;
import com.avst.meetingcontrol.web.vo.GetAvstmt_modelByssidVO;
import com.avst.meetingcontrol.web.vo.GetAvstmt_modelListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class Avstmt_modelService extends BaseService {
    private Gson gson = new Gson();

    @Autowired
    private Avstmt_modelMapper avstmt_modelMapper;

    @Autowired
    private Avstmt_modeltdMapper avstmt_modeltdMapper;

    public void getAvstmt_modelList(RResult result, GetAvstmt_modelListParam param){
        GetAvstmt_modelListVO vo=new GetAvstmt_modelListVO();


        GetAvstmt_modelListParam pageparam = param;

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
        if (null!=pageparam.getExplain()){
            ew.like(true,"`explain`",pageparam.getExplain());
        }
        if (null!=pageparam.getUserecord()){
            ew.eq(true,"userecord",pageparam.getUserecord());
        }


        if(StringUtils.isNotEmpty(param.getStartdate()) && StringUtils.isNotEmpty(param.getEnddate())){
            ew.between("DATE(createtime)", param.getStartdate(), param.getEnddate());
        }

        int count=avstmt_modelMapper.selectCount(ew);
        param.setRecordCount(count);
        Page<Avstmt_model> page=new Page<>(param.getCurrPage(),param.getPageSize());
        List<Avstmt_model> oldpagelist=avstmt_modelMapper.selectPage(page,ew);
        List<Avstmt_modelAll> pagelist=new ArrayList<>();
        if(null!=oldpagelist&&oldpagelist.size()>0){
            pagelist =gson.fromJson(gson.toJson(oldpagelist), new TypeToken<List<Avstmt_modelAll>>(){}.getType());
        }
        if (null!=pagelist&&pagelist.size()>0){
            //获取全部通道
            for (Avstmt_modelAll avstmt_modelAll : pagelist) {
                String mtmodelssid=avstmt_modelAll.getSsid();
                EntityWrapper ew1=new EntityWrapper();
                ew1.orderBy("createtime",false);
                ew1.eq(true,"mtmodelssid",mtmodelssid);
                List<Avstmt_modeltdAll> avstmt_modeltdAlls=avstmt_modeltdMapper.selectList(ew1);
                if (null!=avstmt_modeltdAlls&&avstmt_modeltdAlls.size()>0){
                    avstmt_modelAll.setAvstmt_modeltdAlls(avstmt_modeltdAlls);
                }
            }
        }
        vo.setPagelist(pagelist);
        vo.setPageparam(pageparam);

        result.setData(vo);
        changeResultToSuccess(result);
        return;
    }

    public void updateAvstmt_model(RResult result, UpdateAvstmt_modelParam param){
        String ssid=param.getSsid();
        LogUtil.intoLog(this.getClass(),"updateAvstmt_model__ssid__"+ssid);
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            return;
        }
        EntityWrapper ew=new EntityWrapper();
        ew.eq("ssid",ssid);
       int avstmt_modelMapper_updatebool = avstmt_modelMapper.update(param,ew);
       LogUtil.intoLog(this.getClass(),"avstmt_modelMapper_updatebool__"+avstmt_modelMapper_updatebool);
       if (avstmt_modelMapper_updatebool>0){
           result.setData(1);
           changeResultToSuccess(result);
       }
        return;
    }
    public void addAvstmt_model(RResult result, AddAvstmt_modelParam param){
        param.setCreatetime(new Date());
        param.setSsid(OpenUtil.getUUID_32());
        int avstmt_modelMapper_insertbool = avstmt_modelMapper.insert(param);
        LogUtil.intoLog(this.getClass(),"avstmt_modelMapper_insertbool__"+avstmt_modelMapper_insertbool);
        if (avstmt_modelMapper_insertbool>0){
            result.setData(1);
            changeResultToSuccess(result);
        }
        return;
    }
    public void getAvstmt_modelByssid(RResult result, GetAvstmt_modelByssidParam param){
        GetAvstmt_modelByssidVO vo=new GetAvstmt_modelByssidVO();

        String ssid=param.getSsid();
        LogUtil.intoLog(this.getClass(),"getAvstmt_modelByssid__ssid__"+ssid);
        if (StringUtils.isBlank(ssid)){
            result.setMessage("参数为空");
            return;
        }

        Avstmt_model avstmt_model=new Avstmt_model();
        avstmt_model.setSsid(ssid);
        avstmt_model= avstmt_modelMapper.selectOne(avstmt_model);
        Avstmt_modelAll avstmt_modelAll = gson.fromJson(gson.toJson(avstmt_model),Avstmt_modelAll.class);
        if (null!=avstmt_modelAll){
            String mtmodelssid=avstmt_modelAll.getSsid();
            EntityWrapper ew1=new EntityWrapper();
            ew1.orderBy("createtime",false);
            ew1.eq(true,"mtmodelssid",mtmodelssid);
            List<Avstmt_modeltdAll> avstmt_modeltdAlls=avstmt_modeltdMapper.selectList(ew1);
            if (null!=avstmt_modeltdAlls&&avstmt_modeltdAlls.size()>0){
                avstmt_modelAll.setAvstmt_modeltdAlls(avstmt_modeltdAlls);
            }
            vo.setAvstmt_model(avstmt_modelAll);
            result.setData(vo);
            changeResultToSuccess(result);
            return;
        }
        return;
    }

}
