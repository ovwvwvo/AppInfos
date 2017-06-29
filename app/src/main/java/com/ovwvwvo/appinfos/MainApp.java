package com.ovwvwvo.appinfos;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;
import com.ovwvwvo.jkit.AppWrapper;
import com.ovwvwvo.share.ShareApi;

import io.fabric.sdk.android.Fabric;

/**
 * Copyright ©2016 by rawer
 */

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppWrapper.getInstance().setAppContext(this);
        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());
        initShare();
        initInstabug();
    }

    private void initInstabug() {
        new Instabug.Builder(this, "7115970e680b5f0732639fb380d2ebcf")
            .setInvocationEvent(InstabugInvocationEvent.SHAKE)
            .build();
    }

    private void initShare() {
        ShareApi.init(this);
        ShareApi.setQQConfig(new ShareApi.ShareConfig(BuildConfig.qqKey, BuildConfig.qqValue));
        ShareApi.setWeixin(new ShareApi.ShareConfig(BuildConfig.wechatKey, BuildConfig.wechatValue));
        ShareApi.setSinaWeibo(new ShareApi.ShareConfig(BuildConfig.sinaKey, BuildConfig.sinaValue));
    }

}
