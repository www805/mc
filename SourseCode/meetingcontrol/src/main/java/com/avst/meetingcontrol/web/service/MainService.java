package com.avst.meetingcontrol.web.service;

import com.avst.meetingcontrol.common.conf.Constant;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.web.req.LoginParam;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MainService {

    public RResult logining(RResult result, HttpServletRequest request, LoginParam loginParam){

        if(loginParam.getLoginaccount().equals("admin")&&loginParam.getPassword().equals("admin123")){
            result.changeToTrue();

            request.getSession().setAttribute(Constant.MANAGE_WEB,loginParam);

        }else{
            result.setMessage("登录失败");
        }
        return result;
    }

}
