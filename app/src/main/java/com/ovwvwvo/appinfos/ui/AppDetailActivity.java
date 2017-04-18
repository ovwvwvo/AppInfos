package com.ovwvwvo.appinfos.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.presenter.AppDetailPresenter;
import com.ovwvwvo.jlibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Copyright ©2017 by rawer
 */

public class AppDetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;

    public static final String PACKAGE_NAME = "package_name";
    private String packageName;

    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_icon)
    CircleImageView appIcon;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.app_version)
    TextView appVersion;

    private AppDetailPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        packageName = getIntent().getStringExtra(PACKAGE_NAME);
        if (TextUtils.isEmpty(packageName)) {
            ToastUtil.showShort(this, R.string.app_no_exit);
            finish();
        }
        appBarLayout.addOnOffsetChangedListener(this);

        presenter = new AppDetailPresenter();

        initData();
    }

    private void initData() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            appIcon.setImageDrawable(pm.getApplicationIcon(packageName));
            appName.setText(packageInfo.applicationInfo.loadLabel(pm).toString());
            String str = String.format(getString(R.string.detail_version), packageInfo.versionName);
            appVersion.setText(str);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.launch)
    void launch() {
        presenter.launchApp(this, packageName);
    }

    @OnClick(R.id.clear_data)
    void clearData() {

    }

    @OnClick(R.id.clear_cache)
    void clearCache() {

    }

    @OnClick(R.id.open_setting)
    void openSetting() {
        presenter.openSetting(this, packageName);
    }

    @OnClick(R.id.read_android_manifest)
    void readAndroidManifest() {

    }

    @OnClick(R.id.save_apk)
    void saveApk() {

    }

    @OnClick(R.id.open_in_appstore)
    void openInAppStore() {
        presenter.openAppStore(this, packageName);
    }

    @OnClick(R.id.uninstall)
    void uninstall() {
        presenter.uninstall(this, packageName);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        handleToobarDisplay(percentage);
    }

    private void handleToobarDisplay(float percentage) {
        if (percentage > PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            toolbar.setTitle(appName.getText());
        } else {
            toolbar.setTitle("");
        }
    }
}
