package com.avst.meetingcontrol.common.cache;

import com.avst.meetingcontrol.common.cache.param.AppCacheParam;

public class AppCache {

    private static AppCacheParam appCacheParam;

    public static synchronized AppCacheParam getAppCacheParam() {

        if(null == appCacheParam){
            appCacheParam = new AppCacheParam();
        }
        return appCacheParam;
    }

    public static synchronized void setAppCacheParam(AppCacheParam appCacheParam) {
        AppCache.appCacheParam = appCacheParam;
    }

    public static synchronized void delAppCacheParam(){
        appCacheParam = null;
    }
}
