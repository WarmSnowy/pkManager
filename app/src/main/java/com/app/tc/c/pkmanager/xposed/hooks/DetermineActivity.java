package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.Context;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class DetermineActivity extends BaseHook {
    public static final String TAG = "DetermineActivity";

    public static boolean hook(final Context context) {
        return true;
    }

}