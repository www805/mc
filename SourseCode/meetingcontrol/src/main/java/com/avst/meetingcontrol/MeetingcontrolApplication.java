package com.avst.meetingcontrol;

import com.avst.meetingcontrol.common.util.properties.PropertiesListener;
import com.avst.meetingcontrol.common.util.properties.PropertiesListenerConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@MapperScan({"com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper","com.avst.meetingcontrol.common.datasourse.publicsourse.mapper"})
public class MeetingcontrolApplication {

    /**
     *
     * 第四种方式：通过注册监听器(`Listeners`) + `PropertiesLoaderUtils`的方式
     *
     * 注册配置文件监听器
     */
    @RequestMapping("/listener")
    public Map<String, Object> listener() {
        Map<String, Object> map = new HashMap<>();
        map.putAll(PropertiesListenerConfig.getAllProperty());
        return map;
    }


    public static void main(String[] args) {

        //注册监听器
        SpringApplication application = new SpringApplication(MeetingcontrolApplication.class);
        application.addListeners(new PropertiesListener("application.properties","mc.properties"));
        application.run( args);
    }

}
