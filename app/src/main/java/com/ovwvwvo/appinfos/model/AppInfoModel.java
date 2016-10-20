package com.ovwvwvo.appinfos.model;

import android.graphics.drawable.Drawable;

/**
 * Created by guang on 16/9/13.
 */

public class AppInfoModel {
    private Drawable icon;
    private String appName;
    private String packageName;
    private String versionName;

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

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Override
    public String toString() {
        return "{" +
                "appName : \"" + appName + '\"' +
                ", packageName : \"" + packageName + '\"' +
                '}';
    }
}
