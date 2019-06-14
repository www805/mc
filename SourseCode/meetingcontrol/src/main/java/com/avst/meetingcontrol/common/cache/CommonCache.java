package com.avst.meetingcontrol.common.cache;

import com.avst.meetingcontrol.common.cache.param.AdminManage_session;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一些常用的公共的缓存
 */
public class CommonCache {




    /**
     * 用户在session中的缓存
     */
    private static AdminManage_session adminManage_session;

    public static AdminManage_session getAdminManage_session(){

        return adminManage_session;
    }

    public static void setAdminManage_session(AdminManage_session admin){

        adminManage_session=admin;

    }


}
