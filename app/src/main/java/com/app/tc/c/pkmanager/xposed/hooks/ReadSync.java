package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.Context;
import android.os.Build;

import java.lang.reflect.Member;
import java.util.ArrayList;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class ReadSync extends BaseHook {
    public static final String TAG = "ReadSync";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookContentResolverGetCurrentSync(context))
            status = false;
        if (!hookContentResolverGetCurrentSyncs(context))
            status = false;

        return status;
    }

    private static boolean hookContentResolverGetCurrentSync(final Context context) {
        int minSdk = 8;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getContentResolver().getClass().getName())
                    .getMethod("getCurrentSync");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("【hookContentResolverGetCurrentSync】！");
                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookContentResolverGetCurrentSyncs(final Context context) {
        int minSdk = 11;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getContentResolver().getClass().getName())
                    .getMethod("getCurrentSyncs");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }
}
