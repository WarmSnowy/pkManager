package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;

import java.lang.reflect.Member;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class GetCalendars extends BaseHook {
    public static final String TAG = "GetCalendars";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookQuery1(context))
            status = false;
        if (!hookQuery2(context))
            status = false;
        if (!hookQuery3(context))
            status = false;
        return status;
    }

    private static boolean hookQuery1(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getContentResolver().getClass().getName())
                    .getMethod("query", Uri.class, String[].class, String.class, String[].class, String.class);
            XposedBridge.hookMethod(member, new QueryMethodHook());
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookQuery2(final Context context) {
        int minSdk = 16;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getContentResolver().getClass().getName())
                    .getMethod("query", Uri.class, String[].class, String.class, String[].class, String.class, CancellationSignal.class);
            XposedBridge.hookMethod(member, new QueryMethodHook());
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookQuery3(final Context context) {
        int minSdk = 26;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getContentResolver().getClass().getName())
                    .getMethod("query", Uri.class, String[].class, Bundle.class, CancellationSignal.class);
            XposedBridge.hookMethod(member, new QueryMethodHook());
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static class QueryMethodHook extends XC_MethodHook {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            Cursor cursor = (Cursor) param.getResult();
            Uri uri = (Uri) param.args[0];
            if (cursor == null || uri == null)
                return;
            if (uri.getAuthority().equals("com.android.calendar")) {
                MatrixCursor matrixCursor = new MatrixCursor(cursor.getColumnNames());
                param.setResult(matrixCursor);
            }
        }
    }
}
