package com.ovwvwvo.appinfos.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ovwvwvo.appinfos.model.AppInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guang on 16/9/13.
 */

public class AppInfoUtil {

    public static List<AppInfoModel> getAllAppInfos(Context context) {
        List<AppInfoModel> models = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
//        List<PackageInfo> list = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        List<PackageInfo> list = pm.getInstalledPackages(PackageManager.GET_SHARED_LIBRARY_FILES);
        //List<PackageInfo> list=pm.getInstalledPackages(PackageManager.GET_META_DATA);
        //List<PackageInfo> list=pm.getInstalledPackages(0);
        //List<PackageInfo> list=pm.getInstalledPackages(-10);
        //List<PackageInfo> list=pm.getInstalledPackages(10000);
        AppInfoModel appInfoModel;
        for (PackageInfo packageInfo : list) {
            appInfoModel = new AppInfoModel();
            appInfoModel.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
            appInfoModel.setPackageName(packageInfo.packageName);
            appInfoModel.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
            models.add(appInfoModel);
        }
        return models;
    }


}
