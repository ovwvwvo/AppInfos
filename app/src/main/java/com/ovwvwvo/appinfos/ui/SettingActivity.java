package com.ovwvwvo.appinfos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.common.Base.BaseActivity;
import com.ovwvwvo.common.mvp.presenter.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected Presenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(getString(R.string.settings));
    }
}
