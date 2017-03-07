package com.ovwvwvo.appinfos.model.perference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class SettingPreference extends BasePreference {
    private static final String FILENAME = "preference";

    private static final String FIRST_LAUNCH = "first_launch";

    public static void setFirstLaunch(Context context, boolean is_first_launch) {
        SharedPreferences.Editor editor = getEditor(context, FILENAME);
        editor.putBoolean(FIRST_LAUNCH, is_first_launch);
        editor.apply();
    }

    public static boolean getFirstLaunch(Context context) {
        SharedPreferences preferences = getPreference(context, FILENAME);
        return preferences.getBoolean(FIRST_LAUNCH, false);
    }
}
