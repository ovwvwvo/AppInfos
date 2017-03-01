package com.ovwvwvo.appinfos.util;

import android.content.Context;

/**
 * Created by ovwvwvo on 17/3/1.
 */
public class AppWrapper {

    private AppWrapper(){}

    private static AppWrapper mInstance = new AppWrapper();
    public static AppWrapper getInstance() {
        return mInstance;
    }

    private Context mAppContext = null;

    public Context getAppContext() {
        if (mAppContext == null)
            throw new IllegalStateException("application context has not been injected");
        else {
            return mAppContext;
        }
    }

    public void setAppContext(Context context) {
        mAppContext = context;
    }
}
