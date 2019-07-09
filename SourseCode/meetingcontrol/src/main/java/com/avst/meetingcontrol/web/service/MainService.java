package com.avst.meetingcontrol.web.service;

import com.avst.meetingcontrol.common.conf.Constant;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.web.req.LoginParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MainService {

    public RResult logining(RResult result, HttpServletRequest request, LoginParam loginParam){
         String loginaccount=loginParam.getLoginaccount().trim();
         String password=loginParam.getPassword().trim();
         if (StringUtils.isBlank(loginaccount)||StringUtils.isBlank(password)){
             result.setMessage("账号密码不能为空");
             return result;
         }


        if(loginParam.getLoginaccount().equals("admin")&&loginParam.getPassword().equals("admin")){
            result.changeToTrue();
            request.getSession().setAttribute(Constant.MANAGE_WEB,loginParam);
        }else{
            result.setMessage("登录失败");
        }
        return result;
    }

}
