package com.ovwvwvo.appinfos.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ovwvwvo.appinfos.model.AppInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovwvwvo on 16/9/13.
 */

public class AppInfoUtil {

    public static List<AppInfoModel> getAllAppInfos(Context context) {
        List<AppInfoModel> models = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(PackageManager.GET_INSTRUMENTATION);
        AppInfoModel appInfoModel;
        for (PackageInfo packageInfo : list) {
            appInfoModel = new AppInfoModel();
            appInfoModel.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
            appInfoModel.setPackageName(packageInfo.packageName);
            appInfoModel.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
            appInfoModel.setVersionName(packageInfo.versionName);
            models.add(appInfoModel);
        }
        return models;
    }


}
