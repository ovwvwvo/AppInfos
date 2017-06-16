package com.ovwvwvo.appinfos;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.ovwvwvo.jkit.AppWrapper;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import io.fabric.sdk.android.Fabric;

/**
 * Copyright ©2016 by rawer
 */

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppWrapper.getInstance().setAppContext(this);
        Fabric.with(this, new Crashlytics());
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
