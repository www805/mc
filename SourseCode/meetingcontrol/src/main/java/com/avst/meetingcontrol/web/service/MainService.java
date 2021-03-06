package com.avst.meetingcontrol.web.service;

import com.avst.meetingcontrol.common.cache.AppCache;
import com.avst.meetingcontrol.common.cache.param.AppCacheParam;
import com.avst.meetingcontrol.common.conf.BASEType;
import com.avst.meetingcontrol.common.conf.Constant;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_modelMapper;
import com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper.Avstmt_modeltdMapper;
import com.avst.meetingcontrol.common.datasourse.publicsourse.mapper.Base_mtinfoMapper;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseService;
import com.avst.meetingcontrol.common.util.baseaction.Code;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.baseaction.ReqParam;
import com.avst.meetingcontrol.feignclient.ec.EquipmentControl;
import com.avst.meetingcontrol.feignclient.ec.req.GetToOutBaseEcParam;
import com.avst.meetingcontrol.feignclient.ec.req.GetToOutBaseListParam;
import com.avst.meetingcontrol.web.req.GetBaseListParam;
import com.avst.meetingcontrol.web.req.GetHomeParam;
import com.avst.meetingcontrol.web.req.LoginParam;
import com.avst.meetingcontrol.web.vo.GetBaseListVO;
import com.avst.meetingcontrol.web.vo.GetHomeVO;
import com.avst.meetingcontrol.web.vo.GetLoginCookieVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

    public RResult logining(RResult result, HttpServletRequest request, HttpServletResponse response, LoginParam loginParam){

        if (StringUtils.isBlank(loginParam.getLoginaccount())||StringUtils.isBlank(loginParam.getPassword())){
            result.setMessage("账号密码不能为空");
            return result;
        }


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
            result.setMessage("未找到该用户");
            return result;
        }

        if(!loginParam.getPassword().equals(password)){
            result.setMessage("密码错误");
            return result;
        }

        boolean rememberpassword=loginParam.isRememberpassword();
        if (rememberpassword){
            Cookie mcloginaccount=new Cookie("MCLOGINACCOUNT",loginaccount);
            mcloginaccount.setMaxAge(60*60*24*7);
            mcloginaccount.setPath("/");
            Cookie mcrememberme=new Cookie("MCREMEMBERME","YES");
            mcrememberme.setMaxAge(60*60*24*7);
            mcrememberme.setPath("/");
            response.addCookie(mcloginaccount);
            response.addCookie(mcrememberme);
        }else {
            Cookie mcloginaccount=new Cookie("MCLOGINACCOUNT",null);
            mcloginaccount.setMaxAge(0);
            mcloginaccount.setPath("/");
            Cookie mcrememberme=new Cookie("MCREMEMBERME",null);
            mcrememberme.setMaxAge(0);
            mcrememberme.setPath("/");
            response.addCookie(mcloginaccount);
            response.addCookie(mcrememberme);
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
        result.setData(cacheParam);
        result.changeToTrue();

    }

    public void getBaseList(RResult result, GetBaseListParam param){
        GetToOutBaseListParam getToOutBaseListParam=new GetToOutBaseListParam();
        getToOutBaseListParam.setBaseType(BASEType.Base);
        RResult rr =  equipmentControl.getToOutBaseList(getToOutBaseListParam);
        if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
            if(null!=rr.getData()){
                Gson gson=new Gson();
                List<GetBaseListVO> vos=gson.fromJson(gson.toJson(rr.getData()), new TypeToken<List<GetBaseListVO>>(){}.getType());
                if (null!=vos&&vos.size()>0){
                    result.setData(vos);
                }
            }
            changeResultToSuccess(result);
            LogUtil.intoLog(this.getClass(),"审讯设备getToOutBaseList__请求成功");
        }else {
            String msg=rr.getMessage()==null?result.getMessage():rr.getMessage();
            result.setMessage(msg);
            LogUtil.intoLog(this.getClass(),"审讯设备getToOutBaseList__请求失败");
        }

    }

    public void getLoginCookie(RResult result,HttpServletRequest request){
        GetLoginCookieVO vo=new GetLoginCookieVO();
        String loginaccount = "";
        String password = "";

        //获取当前站点的所有Cookie
        String rememberme=null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {//对cookies中的数据进行遍历，找到用户名、密码的数据
                if ("MCLOGINACCOUNT".equals(cookies[i].getName())) {
                    loginaccount = cookies[i].getValue();
                } else if ("MCREMEMBERME".equals(cookies[i].getName())) {
                    rememberme = cookies[i].getValue();
                }
            }
        }

        if (StringUtils.isNotEmpty(rememberme)&&rememberme.equals("YES")&&StringUtils.isNotEmpty(loginaccount)){
            AppCacheParam cacheParam = AppCache.getAppCacheParam();
            if (StringUtils.isBlank(cacheParam.getTitle()) || null == cacheParam.getData()) {
                RResult rr = new RResult();
                this.getNavList(rr);
            }

            Map<String, Object> loginData = cacheParam.getData();
            password = (String) loginData.get("password");
        }


        vo.setLoginaccount(loginaccount);
        vo.setPassword(password);
        result.setData(vo);
        result.changeToTrue();
       return;
    }

    public void getBaseEc(RResult result, GetToOutBaseEcParam param) {


        try {
            param.setBaseType(BASEType.Base);
            RResult rr = equipmentControl.getToOutBaseEc(param);

            if (null!=rr&&rr.getActioncode().equals(Code.SUCCESS.toString())){
                result.setData(rr.getData());
                changeResultToSuccess(result);
            }

        } catch (Exception e) {
            LogUtil.intoLog(4, this.getClass(), "获取ec所有基础设备getBaseEc__请求失败");
        }


    }
}
