package com.ovwvwvo.appinfos.model.perference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by guang on 16/8/30.
 */
public abstract class BasePreference {

    protected static SharedPreferences getPreference(Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    protected static SharedPreferences.Editor getEditor(Context context, String fileName) {
        return getPreference(context, fileName).edit();
    }
}
