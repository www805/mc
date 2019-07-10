package com.avst.meetingcontrol.web.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 关于会议的页面跳转
 */
@RequestMapping("/page/mtpage")
@Controller
public class MtPageAction {


    @GetMapping("/toAvstmt_modelList")
    public ModelAndView tomt_modelList(Model model){
        model.addAttribute("title","会议模板列表");
        return new ModelAndView("sweb/avsthtml/Avstmt_modelList", "Avstmt_modelList", model);
    }

    @GetMapping("/toAvstmt_modelAddOrUpdate")
    public ModelAndView toAvstmt_modelAddOrUpdate(Model model,String ssid){
        model.addAttribute("ssid",ssid);
        model.addAttribute("title","会议模板编辑");
        return new ModelAndView("sweb/avsthtml/Avstmt_modelAddOrUpdate", "Avstmt_modelAddOrUpdate", model);
    }



    @GetMapping("/toBase_mtinfoList")
    public ModelAndView tomtinfoList(Model model){
        model.addAttribute("title","会议列表");
        return new ModelAndView("sweb/publichtml/Base_mtinfoList", "Base_mtinfoList", model);
    }

    @GetMapping("/toAvstmt_modeltdList")
    public ModelAndView toAvstmt_modeltdList(Model model,String mtmodelssid){
        model.addAttribute("title","模板通道列表");
        model.addAttribute("mtmodelssid",mtmodelssid);
        return new ModelAndView("sweb/avsthtml/Avstmt_modeltdList", "Avstmt_modeltdList", model);
    }

    @GetMapping("/toAvstmt_modeltdAddOrUpdate")
    public ModelAndView toAvstmt_modeltdAddOrUpdate(Model model,String ssid,String mtmodelssid){
        model.addAttribute("ssid",ssid);
        model.addAttribute("mtmodelssid",mtmodelssid);
        model.addAttribute("title","模板通道编辑");
        return new ModelAndView("sweb/avsthtml/Avstmt_modeltdAddOrUpdate", "Avstmt_modeltdAddOrUpdate", model);
    }



}
