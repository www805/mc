package com.avst.meetingcontrol.web.service;

import com.avst.meetingcontrol.common.cache.AppCache;
import com.avst.meetingcontrol.common.cache.param.AppCacheParam;
import com.avst.meetingcontrol.common.conf.Constant;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.entity.Avstmt_model;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_modelMapper;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_modeltdMapper;
import com.avst.meetingcontrol.common.datasourse.publicsourse.entity.Base_mtinfo;
import com.avst.meetingcontrol.common.datasourse.publicsourse.mapper.Base_mtinfoMapper;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.OpenUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.vo.fd.param.Flushbonadinginfo;
import com.avst.meetingcontrol.web.req.GetHomeParam;
import com.avst.meetingcontrol.web.req.LoginParam;
import com.avst.meetingcontrol.web.vo.GetHomeVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class MainService extends BaseService {
    @Autowired
    private EquipmentControl equipmentControl;

    @Autowired
    private Avstmt_modelMapper avstmt_modelMapper;

    @Autowired
    private Avstmt_modeltdMapper avstmt_modeltdMapper;

    @Autowired
    private Base_mtinfoMapper base_mtinfoMapper;

    public RResult logining(RResult result, HttpServletRequest request, LoginParam loginParam){

        AppCacheParam cacheParam = AppCache.getAppCacheParam();
        if (StringUtils.isBlank(cacheParam.getTitle()) || null == cacheParam.getData()) {
            RResult rr = new RResult();
            this.getNavList(rr);
        }

        /**取出账号密码**/
        Map<String, Object> loginData = cacheParam.getData();

        String loginaccount = (String) loginData.get("loginaccount");
        String password = (String) loginData.get("password");

        if(!loginParam.getLoginaccount().equals(loginaccount)){
            result.setMessage("用户不存在");
            return result;
        }

        if(!loginParam.getPassword().equals(password)){
            result.setMessage("用户名或密码错误");
            return result;
        }

        result.changeToTrue();
        request.getSession().setAttribute(Constant.MANAGE_WEB, loginParam);

//        if(loginParam.getLoginaccount().equals("admin")&&loginParam.getPassword().equals("admin")){
//            result.changeToTrue();
//            request.getSession().setAttribute(Constant.MANAGE_WEB,loginParam);
//        }else{
//            result.setMessage("登录失败");
//        }
        return result;
    }

    public void getHome(RResult result, GetHomeParam param){
        GetHomeVO getHomeVO=new GetHomeVO();
          Integer fdcount=0;
          Integer phcount=0;
          Integer asrcount=0;
          Integer ttscount=0;

         Integer modelcount=0;//会议模板个数
         Integer modeltdcount=0;//模板通道个数

         Integer mtinfo_count=0;
         Integer mtinfo_initcount=0;//会议初始化个数
         Integer mtinfo_ingcount=0;//会议进行中个数
         Integer mtinfo_endtcount=0;//会议已结束个数
         Integer mtinfo_pausecount=0;//会议暂停个数
         Integer mtinfo_abnormalcount=0;//会议异常个数



        ReqParam reqParam=new ReqParam();
        RResult rr=equipmentControl.gethome(reqParam);
        if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())&&null!=rr.getData()){
            Gson gson=new Gson();
            getHomeVO=gson.fromJson(gson.toJson(rr.getData()), GetHomeVO.class);
            LogUtil.intoLog(this.getClass(),"首页统计equipmentControl.gethome__请求成功");
        }else {
            LogUtil.intoLog(this.getClass(),"首页统计equipmentControl.gethome__请求失败");
        }

        EntityWrapper ew = new EntityWrapper();
        modelcount=avstmt_modelMapper.selectCount(ew);
        modeltdcount=avstmt_modeltdMapper.selectCount(ew);

        mtinfo_count=base_mtinfoMapper.selectCount(ew);//会议总个数

        EntityWrapper ew1 = new EntityWrapper();
        ew1.eq("mtstate",0);
        mtinfo_initcount=base_mtinfoMapper.selectCount(ew1);//会议初始化个数

        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("mtstate",1);
        mtinfo_ingcount=base_mtinfoMapper.selectCount(ew2);//会议进行中个数

        EntityWrapper ew3 = new EntityWrapper();
        ew3.eq("mtstate",2);
        mtinfo_endtcount=base_mtinfoMapper.selectCount(ew3);//会议已结束个数

        EntityWrapper ew4 = new EntityWrapper();
        ew4.eq("mtstate",3);
        mtinfo_pausecount=base_mtinfoMapper.selectCount(ew4);//会议暂停个数

        EntityWrapper ew5 = new EntityWrapper();
        ew5.eq("mtstate",4);
        mtinfo_abnormalcount=base_mtinfoMapper.selectCount(ew5);//会议异常个数



        getHomeVO.setModelcount(modelcount);
        getHomeVO.setModeltdcount(modeltdcount);
        getHomeVO.setMtinfo_count(mtinfo_count);

        getHomeVO.setMtinfo_initcount(mtinfo_initcount);
        getHomeVO.setMtinfo_ingcount(mtinfo_ingcount);
        getHomeVO.setMtinfo_endtcount(mtinfo_endtcount);
        getHomeVO.setMtinfo_pausecount(mtinfo_pausecount);
        getHomeVO.setMtinfo_abnormalcount(mtinfo_abnormalcount);
        result.setData(getHomeVO);
        changeResultToSuccess(result);
        return;
    }

    public void getNavList(RResult result) {

        AppCacheParam cacheParam = AppCache.getAppCacheParam();
        if(null == cacheParam.getData()){
            String application_name = PropertiesListenerConfig.getProperty("spring.application.name");
            String nav_file_name = PropertiesListenerConfig.getProperty("nav.file.name");

            String path = OpenUtil.getXMSoursePath() + "\\" + nav_file_name + ".yml";
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(path);

                Yaml yaml = new Yaml();
                Map<String,Object> map = yaml.load(fis);

                Map<String,Object> avstYml = (Map<String, Object>) map.get(application_name);
                avstYml.put("bottom", map.get("bottom"));
                if (null != map && map.size() > 0) {
                    cacheParam.setTitle((String) avstYml.get("title"));
                }
                cacheParam.setData(avstYml);

            } catch (IOException e) {
                LogUtil.intoLog(4, this.getClass(), "没找到外部配置文件：" + path);
            }finally {
                if(null != fis){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        result.setData(cacheParam);
        result.changeToTrue();

    }
}
