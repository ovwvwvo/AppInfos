package com.ovwvwvo.appinfos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ovwvwvo.appinfos.R;

/**
 * Copyright ©2017 by Teambition
 */

public class AppDetailActivity extends BaseActivity{

    public static final String PACKAGE_NAME = "package_name";
    private String packageName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        packageName=getIntent().getStringExtra(PACKAGE_NAME);
    }
}
