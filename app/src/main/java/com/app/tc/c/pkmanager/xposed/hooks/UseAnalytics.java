package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.Context;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class UseAnalytics extends BaseHook {
    public static final String TAG = "UseAnalytics";

    public static boolean hook(final Context context) {
        classLoader = context.getClassLoader();
        return false;
    }

    //TODO:补全
}
