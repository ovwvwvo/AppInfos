package com.ovwvwvo.appinfos.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ovwvwvo.appinfos.model.event.InstallEvent;
import com.ovwvwvo.appinfos.model.event.UnInstallEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Copyright ©2017 by rawer
 */

public class MonitorSysReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收安装广播
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            EventBus.getDefault().post(new InstallEvent());
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            //接收卸载广播
            EventBus.getDefault().post(new UnInstallEvent());
        }
    }
}