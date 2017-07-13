package com.ovwvwvo.appinfos.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Copyright ©2016 by rawer
 */

public class BaseActivity extends com.ovwvwvo.common.activity.BaseActivity {
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=this;
    }
}
