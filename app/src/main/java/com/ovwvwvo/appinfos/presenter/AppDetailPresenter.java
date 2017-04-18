package com.ovwvwvo.appinfos.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.jlibrary.utils.ToastUtil;

/**
 * Copyright ©2017 by rawer
 */

public class AppDetailPresenter {

    public void launchApp(Context context, String packageName) {
        try {
            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(LaunchIntent);
        } catch (Exception e) {
            ToastUtil.showShort(context, R.string.app_no_exit);
        }
    }

    public void uninstall(Context context, String packageName) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DELETE);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.showShort(context, R.string.app_no_exit);
        }
    }

    public void openSetting(Context context, String packageName) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.showShort(context, R.string.app_no_exit);
        }
    }

    public void openAppStore(Context context, String packageName) {
        try {
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            String str = "market://details?id=".concat(packageName);
            localIntent.setData(Uri.parse(str));
            context.startActivity(localIntent);
        } catch (Exception e) {
            ToastUtil.showShort(context, R.string.app_no_exit);
        }
    }
}
