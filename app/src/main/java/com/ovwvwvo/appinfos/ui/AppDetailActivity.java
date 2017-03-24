package com.ovwvwvo.appinfos.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovwvwvo.appinfos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright ©2017 by Teambition
 */

public class AppDetailActivity extends BaseActivity {

    public static final String PACKAGE_NAME = "package_name";
    private String packageName;

    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.version)
    TextView version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        packageName = getIntent().getStringExtra(PACKAGE_NAME);
        initData();
    }

    private void initData() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            icon.setImageDrawable(pm.getApplicationIcon(packageName));
            appName.setText(packageInfo.applicationInfo.loadLabel(pm).toString());
            String str = String.format(getString(R.string.detail_version), packageInfo.versionName);
            version.setText(str);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.launch)
    void launch() {

    }

    @OnClick(R.id.clear_data)
    void clearData() {

    }


    @OnClick(R.id.clear_cache)
    void clearCache() {

    }

    @OnClick(R.id.open_setting)
    void openSetting() {

    }

    @OnClick(R.id.read_android_manifest)
    void readAndroidManifest() {

    }

    @OnClick(R.id.save_apk)
    void saveApk() {

    }

    @OnClick(R.id.open_in_appstore)
    void openInAppstore() {

    }

    @OnClick(R.id.uninstall)
    void uninstall() {

    }


}
