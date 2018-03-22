package com.app.tc.c.pkmanager.xposed.hooks;

import android.app.Notification;
import android.content.Context;
import android.os.Build;

import java.lang.reflect.Member;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class ReadNotifications extends BaseHook {
    public static final String TAG = "ReadNotifications";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookStatusBarNotificationGetNotification(context))
            status = false;
        return false;
    }


    private static boolean hookStatusBarNotificationGetNotification(final Context context) {
        int minSdk = 18;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.service.notification.StatusBarNotification.class.getName())
                    .getDeclaredMethod("getNotification");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    android.app.Notification result = (android.app.Notification) param.getResult();
                    if (result == null || result.number == 0)
                        return;

                    param.setResult(new Notification(result.icon, "private", result.when));
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }
}
