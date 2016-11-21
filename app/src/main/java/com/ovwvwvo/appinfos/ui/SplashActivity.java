package com.ovwvwvo.appinfos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ovwvwvo.common.Base.BaseActivity;
import com.ovwvwvo.common.mvp.presenter.Presenter;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected Presenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(mContext, HomeActivity.class));
        finish();
    }
}
