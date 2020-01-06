package com.avst.meetingcontrol.common.conf;

import com.avst.meetingcontrol.common.util.DateUtil;
import com.avst.meetingcontrol.common.util.LogUtil;
import com.avst.meetingcontrol.common.util.NetTool;
import com.avst.meetingcontrol.common.util.baseaction.RResult;
import com.avst.meetingcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.meetingcontrol.feignclient.zk.ZkControl;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 当springboot启动的时候，会自动执行该类
 */
@Component
@Order(value = 1)
public class ZkTimeConfig implements ApplicationRunner {

    @Autowired
    private ZkControl zkControl;

    //获取服务器时间进行比对
    @Override
    public void run(ApplicationArguments args) {

        try {
            //从总控获取时间
            RResult controlTime = zkControl.getControlTime();

            if(null != controlTime){

                //日期的差距
                String createTime = (String) controlTime.getData();  //获取总控服务器时间
                if (StringUtils.isNotEmpty(createTime)) {

                    String newTime = DateUtil.getDateAndMinute();  //当前服务器时间
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //把获取到的时间转为时间戳
                    Date date = dateFormatter.parse(createTime);

                    //把转成总控时间和当前服务器时间戳进行计算
                    Date newday = dateFormatter.parse(newTime);
                    Date oldDay = dateFormatter.parse(createTime);

                    Integer servserDate = Integer.parseInt(PropertiesListenerConfig.getProperty("control.servser.date"));
                    String formulas = PropertiesListenerConfig.getProperty("control.servser.formulas");

                    //计算公式转换成整数
                    JexlEngine jexlEngine = new JexlBuilder().create();
                    JexlExpression expression = jexlEngine.createExpression(formulas);
                    Integer evaluate = (Integer) expression.evaluate(null);

                    long intervalDay = (newday.getTime() - oldDay.getTime())/(evaluate);

                    //如果时间差过1小时以上，就修改系统时间
                    if (Math.abs(intervalDay) >= servserDate) {

                        //修改系统时间
                        String osName = System.getProperty("os.name");
                        String cmd = "";
                        try {
                            SimpleDateFormat rq = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat xs = new SimpleDateFormat("HH:mm:ss");

                            if (osName.matches("^(?i)Windows.*$")) {// Window 系统

                                // 格式 HH:mm:ss  22:35:00
                                cmd = xs.format(date);
                                cmd = "  cmd /c time " + cmd;
//                                Runtime.getRuntime().exec(cmd);
                                NetTool.executeCMD(cmd);
                                // 格式：yyyy-MM-dd  2009-03-26
                                cmd = rq.format(date);
                                cmd = " cmd /c date " + cmd;
//                                Runtime.getRuntime().exec(cmd);
                                NetTool.executeCMD(cmd);
                            } else {// Linux 系统
                                // 格式：yyyyMMdd  20090326
                                rq = new SimpleDateFormat("yyyyMMdd");
                                cmd = rq.format(date);
                                cmd = "  date -s " + cmd;
//                                Runtime.getRuntime().exec(cmd);
                                NetTool.executeCMD(cmd);
                                // 格式 HH:mm:ss  22:35:00
                                cmd = xs.format(date);
                                cmd = "  date -s " + cmd;
//                                Runtime.getRuntime().exec(cmd);
                                NetTool.executeCMD(cmd);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

//                System.out.println("服务器获取到的时间：" + date);
            }
        } catch (Exception e) {
            LogUtil.intoLog(4,this.getClass(),"getControlTime ZkTimeConfig。run 总控同步时间，请求异常");
        }

    }


}
