package com.ovwvwvo.appinfos.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.model.perference.SettingPreference;
import com.ovwvwvo.jlibrary.utils.AppUtil;
import com.ovwvwvo.jlibrary.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class AboutActivity extends BaseActivity {
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

        version.setText("v"+AppUtil.getVersionName(this));
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
                        ToastUtil.showShort(getApplicationContext(), R.string.hided_ads);
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

    @OnClick(R.id.share)
    void openShapeBoard() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardBackgroundColor(Color.WHITE)
            .setCancelButtonVisibility(false)
            .setIndicatorColor(Color.WHITE, Color.WHITE)
            .setTitleVisibility(false);
        new ShareAction(AboutActivity.this)
            .withText(getString(R.string.share_content))
            .withExtra(new UMImage(mContext, R.mipmap.ic_launcher))
            .withTitle(getString(R.string.app_name))
            .withTargetUrl(getString(R.string.share_url))
            .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.MORE)
            .setCallback(umShareListener)
            .open(config);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t != null) {
                Toast.makeText(AboutActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    };

}
