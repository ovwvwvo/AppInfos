package com.ovwvwvo.appinfos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.ovwvwvo.appinfos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setTitle(getString(R.string.settings));
    }
}
