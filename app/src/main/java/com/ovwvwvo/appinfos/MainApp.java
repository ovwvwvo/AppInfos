package com.ovwvwvo.appinfos;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initUmeng();
    }

    private void initUmeng() {
        UMShareAPI.get(this);
        Config.REDIRECT_URL = "http://www.baidu.com";
        Config.IsToastTip = true;
        PlatformConfig.setQQZone(BuildConfig.qqKey, BuildConfig.qqValue);
        PlatformConfig.setWeixin(BuildConfig.wechatKey, BuildConfig.wechatValue);
        PlatformConfig.setSinaWeibo(BuildConfig.sinaKey, BuildConfig.sinaValue);
    }
}
