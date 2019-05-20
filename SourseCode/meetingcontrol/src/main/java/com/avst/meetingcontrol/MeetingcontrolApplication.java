package com.avst.meetingcontrol;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@MapperScan({"com.avst.meetingcontrol.common.datasourse.extrasourse.avstmt.mapper","com.avst.meetingcontrol.common.datasourse.publicsourse.mapper"})
public class MeetingcontrolApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingcontrolApplication.class, args);
    }

}
