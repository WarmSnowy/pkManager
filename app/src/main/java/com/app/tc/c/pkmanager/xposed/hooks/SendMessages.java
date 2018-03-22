package com.app.tc.c.pkmanager.xposed.hooks;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;

import java.lang.reflect.Member;
import java.util.ArrayList;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class SendMessages extends BaseHook {
    public static final String TAG = "SendMessages";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookSmsManagerSendDataMessage(context))
            status = false;
        if (!hookSmsManagerSendMultimediaMessage(context))
            status = false;
        if (!hookSmsManagerSendMultipartTextMessage(context))
            status = false;
        if (!hookSmsManagerSendTextMessage(context))
            status = false;
        return status;
    }

    private static boolean hookSmsManagerSendDataMessage(final Context context) {
        int minSdk = 4;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(SmsManager.getDefault().getClass().getName())
                    .getDeclaredMethod("sendDataMessage", String.class, String.class, short.class, byte[].class, PendingIntent.class, PendingIntent.class);
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

    private static boolean hookSmsManagerSendMultimediaMessage(final Context context) {
        int minSdk = 21;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(SmsManager.getDefault().getClass().getName())
                    .getDeclaredMethod("sendMultimediaMessage", Context.class, Uri.class, String.class, Bundle.class, PendingIntent.class);
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


    private static boolean hookSmsManagerSendMultipartTextMessage(final Context context) {
        int minSdk = 4;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(SmsManager.getDefault().getClass().getName())
                    .getDeclaredMethod("sendMultipartTextMessage", String.class, String.class, ArrayList.class, ArrayList.class, ArrayList.class);
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

    private static boolean hookSmsManagerSendTextMessage(final Context context) {
        int minSdk = 4;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(SmsManager.getDefault().getClass().getName())
                    .getDeclaredMethod("sendTextMessage", String.class, String.class, String.class, PendingIntent.class, PendingIntent.class);
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
