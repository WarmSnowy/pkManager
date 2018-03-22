package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;

import java.lang.reflect.Member;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class ReadClipboard extends BaseHook {
    public static final String TAG = "ReadClipboard";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookClipDataCreateFromParcel(context))
            status = false;
        return status;
    }

    private static boolean hookClipDataCreateFromParcel(final Context context) {
        int minSdk = 11;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForNameWithField(android.content.ClipData.class.getName(), "CREATOR")
                    .getDeclaredMethod("createFromParcel", Parcel.class);

            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    ClipData clipData = (ClipData) param.getResult();
                    if (clipData == null || clipData.getItemCount() == 0)
                        return;

                    param.setResult(ClipData.newPlainText("pkManager", "private"));
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }
}
