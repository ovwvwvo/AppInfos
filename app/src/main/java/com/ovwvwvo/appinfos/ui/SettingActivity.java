package com.ovwvwvo.appinfos.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.instabug.library.Instabug;
import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.model.perference.SettingPreference;
import com.ovwvwvo.jkit.utils.AppUtil;
import com.ovwvwvo.jkit.weight.ToastMaster;
import com.ovwvwvo.share.util.ShareUtil;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright ©2016 by rawer
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ads)
    TextView ads;
    @BindView(R.id.version)
    TextView version;

    private boolean isDisplayAds = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        isDisplayAds = SettingPreference.getDisplayAds(this);
        if (isDisplayAds)
            ads.setText(R.string.hide_ads);
        else
            ads.setText(R.string.display_ads);

        version.setText("v" + AppUtil.getVersionName(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.ads)
    void hideAds() {
        if (!isDisplayAds) {
            SettingPreference.setDisplayAds(this, true);
            isDisplayAds = true;
            swapAdsButton();
        } else {
            new MaterialDialog.Builder(this)
                .content(R.string.hide_ads_tip)
                .negativeText(R.string.cancel)
                .positiveText(R.string.sure)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SettingPreference.setDisplayAds(getApplicationContext(), false);
                        isDisplayAds = false;
                        swapAdsButton();
                        ToastMaster.showToastMsg(R.string.hided_ads);
                    }
                })
                .show();
        }
    }

    private void swapAdsButton() {
        ads.setText(isDisplayAds ? R.string.hide_ads : R.string.display_ads);
    }

    @OnClick(R.id.github)
    void gotoGithub() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/ovwvwvo/AppInfos"));
        startActivity(intent);
    }

    @OnClick(R.id.about)
    void about() {

    }

    @OnClick(R.id.feedback)
    void feedback() {
        Instabug.invoke();
    }

    @OnClick(R.id.share)
    void openShapeBoard() {
        String title = getString(R.string.app_name);
        String desc = getString(R.string.share_content);
        String url = getString(R.string.share_url);
        UMImage umImage = new UMImage(mContext, R.mipmap.ic_launcher);
        ShareUtil.startThirdShare(this, title, desc, url, umImage);
    }
}
