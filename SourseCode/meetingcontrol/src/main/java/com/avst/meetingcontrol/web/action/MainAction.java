package com.avst.meetingcontrol.web.action;

import com.avst.meetingcontrol.common.conf.Constant;
import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.baseaction.BaseAction;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.web.req.GetHomeParam;
import com.avst.meetingcontrol.web.req.LoginParam;
import com.avst.meetingcontrol.web.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/mc/main")
@Controller
public class MainAction extends BaseAction {

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/{pageid}")
    public ModelAndView gotomain(Model model, @PathVariable("pageid")String pageid) {

        return new ModelAndView(pageid);
    }


    @RequestMapping(value = "/main")
    public ModelAndView gotomain(Model model) {

        model.addAttribute("title", "智能提讯管理系统");
        return new ModelAndView("sweb/main", "main", model);
    }

    @RequestMapping(value = "/home")
    public ModelAndView gotohome(Model model) {

        //获取统计数据信息

        model.addAttribute("title", "智能提讯管理系统");
        return new ModelAndView("sweb/home", "main", model);
    }


    /**
     * 进入用户登录页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/gotologin")
    public ModelAndView gotologin(Model model, HttpServletRequest request) {
        RResult rResult=createNewResultOfFail();

        model.addAttribute("result", rResult);
        model.addAttribute("title", "欢迎进入会议管理系统");

        request.getSession().setAttribute(Constant.MANAGE_WEB,null);

        return new ModelAndView("sweb/login", "login", model);
    }

    @PostMapping(value = "/logining")
    @ResponseBody
    public RResult checklogin(Model model, HttpServletRequest request, LoginParam loginParam) {
        RResult result=createNewResultOfFail();
        mainService.logining(result,request,loginParam);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public RResult logout(Model model,HttpServletRequest request) {
        RResult rResult=createNewResultOfFail();
        this.changeResultToSuccess(rResult);
        rResult.setMessage("退出成功");
        request.getSession().setAttribute(Constant.MANAGE_WEB,null);
        return rResult;
    }

    @RequestMapping(value = "/404")
    public ModelAndView getError(Model model) {
        model.addAttribute("title", "错误页面404");
        return new ModelAndView("error/404", "error", model);
    }

    /**
     * 首页数据
     * @param param
     * @return
     */
    @RequestMapping("/getHome")
    @ResponseBody
    public RResult getHome(@RequestBody GetHomeParam param){
        RResult result=this.createNewResultOfFail();
        mainService.getHome(result,param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }



}
