package com.ovwvwvo.appinfos.model;

import android.graphics.drawable.Drawable;

/**
 * Created by guang on 16/9/13.
 */

public class AppInfoModel {
    private Drawable icon;
    private String appName;
    private String packageName;

    public Drawable getIcon() {
        return icon;
    }

    public void setAppIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
