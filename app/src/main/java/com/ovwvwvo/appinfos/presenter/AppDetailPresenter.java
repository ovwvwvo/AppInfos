package com.ovwvwvo.appinfos.presenter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.util.DataCleanManager;
import com.ovwvwvo.appinfos.util.PackageUtil;
import com.ovwvwvo.jkit.weight.ToastMaster;

/**
 * Copyright ©2017 by rawer
 */

public class AppDetailPresenter {

    public void launchApp(Context context, String packageName) {
        try {
            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(LaunchIntent);
        } catch (Exception e) {
            ToastMaster.showToastMsg(R.string.app_no_exit);
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
            ToastMaster.showToastMsg(R.string.app_no_exit);
        }
    }

    public void openSetting(Context context, String packageName) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastMaster.showToastMsg(R.string.app_no_exit);
        }
    }

    public void openAppStore(Context context, String packageName) {
        try {
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            String str = "market://details?id=".concat(packageName);
            localIntent.setData(Uri.parse(str));
            context.startActivity(localIntent);
        } catch (Exception e) {
            ToastMaster.showToastMsg(R.string.app_no_exit);
        }
    }


    public void clearAppCache(Context context, String packageName) {
        Context c = getContextFromPackName(context, packageName);
        if (c != null)
            DataCleanManager.cleanApplicationData(c);
    }

    public void cleanInternalCache(Context context, String packageName) {
        Context c = getContextFromPackName(context, packageName);
        if (c != null)
            DataCleanManager.cleanInternalCache(c);
    }


    public void cleanExternalCache(Context context, String packageName) {
        Context c = getContextFromPackName(context, packageName);
        if (c != null)
            DataCleanManager.cleanExternalCache(c);
    }

    public void cleanSharedPreference(Context context, String packageName) {
        Context c = getContextFromPackName(context, packageName);
        if (c != null)
            DataCleanManager.cleanSharedPreference(c);
    }


    public void cleanFiles(Context context, String packageName) {
        Context c = getContextFromPackName(context, packageName);
        if (c != null)
            DataCleanManager.cleanFiles(c);
    }

    public void cleanDatabases(Context context, String packageName) {
        Context c = getContextFromPackName(context, packageName);
        if (c != null)
            DataCleanManager.cleanDatabases(c);
    }

    public String getMd5(Context context, String packageName) {
        String md5 = "";
        try {
            PackageInfo p = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            md5 = PackageUtil.getSignatureDigest(p);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return md5;
    }

    public String getSHA1(Context context, String packageName) {
        String sha1 = "";
        try {
            PackageInfo p = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            sha1 = PackageUtil.getSHA1(p);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    public String getHashKey(Context context, String packageName) {
        String sha1 = "";
        try {
            PackageInfo p = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            sha1 = PackageUtil.getHashKey(p);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    private Context getContextFromPackName(Context context, String packageName) {
        try {
            return
                context.createPackageContext(packageName, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void copy(Context context, String content) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("appInfo", content);
        clipboard.setPrimaryClip(clip);
        ToastMaster.showToastMsg(R.string.copy_tip);
    }
}
