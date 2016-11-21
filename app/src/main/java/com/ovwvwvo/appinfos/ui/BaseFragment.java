package com.ovwvwvo.appinfos.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.appcompat.BuildConfig;

import com.umeng.analytics.MobclickAgent;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class BaseFragment extends Fragment {
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    public void onResume() {
        super.onResume();
        if (!BuildConfig.DEBUG)
            MobclickAgent.onResume(mContext);
    }

    public void onPause() {
        super.onPause();
        if (!BuildConfig.DEBUG)
            MobclickAgent.onPause(mContext);
    }
}
