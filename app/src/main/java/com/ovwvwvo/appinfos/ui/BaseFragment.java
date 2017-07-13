package com.ovwvwvo.appinfos.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Copyright ©2016 by rawer
 */

public class BaseFragment extends com.ovwvwvo.common.fragment.BaseFragment {
    Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }
}
