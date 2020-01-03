package com.avst.meetingcontrol.common.cache;

import com.avst.meetingcontrol.common.cache.param.AppCacheParam;
import com.avst.meetingcontrol.common.conf.NetTool;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.OpenUtil;
import com.avst.meetingcontrol.common.util.properties.PropertiesListenerConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class AppCache {

    private static AppCacheParam appCacheParam;

    public static synchronized AppCacheParam getAppCacheParam() {

        if(null == appCacheParam || null == appCacheParam.getData()){
            init();
        }
        return appCacheParam;
    }

    public static synchronized void setAppCacheParam(AppCacheParam appCacheParam) {
        AppCache.appCacheParam = appCacheParam;
    }

    public static synchronized void delAppCacheParam(){
        appCacheParam = null;
    }


    private static synchronized void init(){

        if(null == appCacheParam){
            appCacheParam = new AppCacheParam();
        }

        String application_name = PropertiesListenerConfig.getProperty("spring.application.name");
        String nav_file_name = PropertiesListenerConfig.getProperty("nav.file.name");

        String path = OpenUtil.getXMSoursePath() + File.separator + nav_file_name + ".yml";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);

            Yaml yaml = new Yaml();
            Map<String,Object> map = yaml.load(fis);

            Map<String,Object> avstYml = (Map<String, Object>) map.get(application_name);
            Map<String,Object> zkYml = (Map<String, Object>) map.get("zk");
            Map<String,Object> guidepage = (Map<String, Object>) zkYml.get("guidepage");
            Map<String,Object> client_button = (Map<String, Object>) guidepage.get("client_button");
            String guidepageUrl = (String) client_button.get("url");
            String myIP = NetTool.getMyIP();
            avstYml.put("guidepageUrl" , "http://" + myIP + guidepageUrl);

            avstYml.put("bottom", map.get("bottom"));
            if (null != map && map.size() > 0) {
                appCacheParam.setTitle((String) avstYml.get("title"));
            }
            appCacheParam.setData(avstYml);

        } catch (IOException e) {
            LogUtil.intoLog(4, AppCache.class, "没找到外部配置文件：" + path);
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




}
