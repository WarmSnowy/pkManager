package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.Context;
import android.os.Build;

import java.lang.reflect.Member;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class RecordVideo extends BaseHook {
    public static final String TAG = "RecordVideo";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookMediaRecorderSetVideoSource(context))
            status = false;
        if (!hookMediaRecorderStartVideo(context))
            status = false;
        if (!hookMediaRecorderStopVideo(context))
            status = false;
        return status;
    }


    private static boolean hookMediaRecorderSetVideoSource(final Context context) {
        int minSdk = 3;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.media.MediaRecorder.class.getName())
                    .getDeclaredMethod("setVideoSource", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookMediaRecorderStartVideo(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.media.MediaRecorder.class.getName())
                    .getDeclaredMethod("start");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }


    private static boolean hookMediaRecorderStopVideo(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.media.MediaRecorder.class.getName())
                    .getDeclaredMethod("stop");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }
}
