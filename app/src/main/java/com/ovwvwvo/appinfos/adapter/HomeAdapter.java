package com.ovwvwvo.appinfos.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.ui.AppListFragment;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class HomeAdapter extends FragmentPagerAdapter {

    private Context context;
    private AppListFragment allFragment;
    private AppListFragment meFragment;

    public static final int ME = 0;
    public static final int SYSTEM = 1;

    public HomeAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == ME) {
            if (meFragment == null)
                return meFragment = AppListFragment.newInstance(ME);
            else
                return meFragment;
        } else {
            if (allFragment == null)
                return allFragment = AppListFragment.newInstance(SYSTEM);
            else return allFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.my_app);
            case 1:
                return context.getString(R.string.system_app);
            default:
                return "";
        }
    }
}
