package com.avst.meetingcontrol.web.action;

import com.avst.meetingcontrol.common.util.properties.PropertiesListenerConfig;
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

    @GetMapping("/toAvstmt_tduserList")
    public ModelAndView toAvstmt_tduserList(Model model){
        model.addAttribute("title","会议人员设备通道列表");
        return new ModelAndView("sweb/publichtml/Avstmt_tduserList", "Avstmt_tduserList", model);
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

    //设备跳转
    @RequestMapping(value = "/toaddOrUpdateFlushbonading")
    public ModelAndView toaddOrUpdateFlushbonading(Model model,String ssid) {
        model.addAttribute("title", "审讯设备新增/修改");
        model.addAttribute("ssid",ssid);
        model.addAttribute("etypessid", PropertiesListenerConfig.getProperty("flushbonading_etypessid"));
        return new ModelAndView("sweb/avsthtml/echtml/addOrUpdateFlushbonading", "addOrUpdateFlushbonadingModel", model);
    }

    @RequestMapping(value = "/toaddOrUpdateFlushbonadingEttd")
    public ModelAndView toaddOrUpdateFlushbonadingEttd(Model model,String ssid,String masterssid) {
        model.addAttribute("title", "设备通道 新增/修改");
        model.addAttribute("ssid",ssid);
        model.addAttribute("masterssid",masterssid);
        model.addAttribute("etypessid", PropertiesListenerConfig.getProperty("flushbonading_etypessid"));
        return new ModelAndView("sweb/avsthtml/echtml/addOrUpdateFlushbonadingEttd", "addOrUpdateFlushbonadingEttdModel", model);
    }

    @RequestMapping(value = "/toaddOrUpdatePolygraph")
    public ModelAndView toaddOrUpdatePolygraph(Model model,String ssid) {
        model.addAttribute("title", "测谎仪新增/修改");
        model.addAttribute("ssid",ssid);
        model.addAttribute("etypessid", PropertiesListenerConfig.getProperty("polygraph_etypessid"));
        return new ModelAndView("sweb/avsthtml/echtml/addOrUpdatePolygraph", "addOrUpdatePolygraphModel", model);
    }

    @RequestMapping(value = "/toaddOrUpdateAsr")
    public ModelAndView toaddOrUpdateAsr(Model model,String ssid) {
        model.addAttribute("title", "语音服务器 新增/修改");
        model.addAttribute("ssid",ssid);
        model.addAttribute("etypessid", PropertiesListenerConfig.getProperty("asr_etypessid"));
        return new ModelAndView("sweb/avsthtml/echtml/addOrUpdateAsr", "addOrUpdateAsrModel", model);
    }




}
