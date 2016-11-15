package com.ovwvwvo.appinfos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ovwvwvo.appinfos.model.perference.SettingPreference;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingPreference.getFirstLaunch(mContext))
            startActivity(new Intent(mContext, GuideActivity.class));
        else
            startActivity(new Intent(mContext, SplashActivity.class));
        finish();
    }
}
