package com.ovwvwvo.appinfos.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.ui.InfoFragment;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class HomeAdapter extends FragmentPagerAdapter {
    private Context context;

    public HomeAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return InfoFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.system_app);
            case 1:
                return context.getString(R.string.my_app);
            default:
                return "";
        }
    }
}
