package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.ContentResolver;
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
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class ReadIdentifiers extends BaseHook {
    public static final String TAG = "ReadIdentifiers";

    public static boolean hook(final Context context, final XC_LoadPackage.LoadPackageParam lpparam) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookAdvertisingIdClient(context))
            status = false;
        if (!hookBuildGetSerial(context))
            status = false;
        if (!hookBuildSerial(context, lpparam))
            status = false;
        if (!hookQuery1(context))
            status = false;
        if (!hookQuery2(context))
            status = false;
        if (!hookQuery3(context))
            status = false;
        if (!hookSettingsSecureGetString(context))
            status = false;
        if (!hookSystemPropertiesGetSerial1(context))
            status = false;
        if (!hookSystemPropertiesGetSerial2(context))
            status = false;
        if (!hookBuildGetRadioVersion(context))
            status = false;
        return status;
    }

    private static boolean hookAdvertisingIdClient(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info")
                    .getDeclaredMethod("getId");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    String result = (String) param.getResult();
                    if (result == null)
                        return;

                    param.setResult("00000000-0000-0000-0000-000000000000");
                }
            });
        } catch (ClassNotFoundException e) {
            return false;
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookBuildGetSerial(final Context context) {
        int minSdk = 26;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.os.Build.class.getName())
                    .getDeclaredMethod("getSerial");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    String result = (String) param.getResult();
                    if (result == null)
                        return;

                    param.setResult("unknown");
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookBuildSerial(final Context context, final XC_LoadPackage.LoadPackageParam lpparam) {
        int minSdk = 9;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Class cls = XposedHelpers.findClass(android.os.Build.class.getName(), lpparam.classLoader);
            XposedHelpers.setStaticObjectField(cls, "SERIAL", "unknown");

        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
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
        } catch (ClassNotFoundException e) {
            return false;
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
        } catch (ClassNotFoundException e) {
            return false;
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
        } catch (ClassNotFoundException e) {
            return false;
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }


    private static boolean hookSettingsSecureGetString(final Context context) {
        int minSdk = 3;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.provider.Settings.Secure.class.getName())
                    .getDeclaredMethod("getString", ContentResolver.class, String.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    String result = (String) param.getResult();
                    if (result == null)
                        return;
                    String key = (String) param.args[1];
                    if (key == null)
                        return;
                    if (key.equals("android_id"))
                        param.setResult("0000000000000000");
                    else if (key.equals("bluetooth_name"))
                        param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookSystemPropertiesGetSerial1(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName("android.os.SystemProperties")
                    .getDeclaredMethod("get", String.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    String result = (String) param.getResult();
                    if (result == null)
                        return;
                    String key = (String) param.args[0];
                    if (key == null)
                        return;
                    if (key.equals("ro.serialno") || key.equals("ro.boot.serialno")) {
                        param.setResult("unknown");
                    } else if (key.substring(1, "ro.build.".length()).equals("ro.build.")) {
                        param.setResult(null);
                    } else if (key.equals("gsm.operator.alpha") || key.equals("gsm.sim.operator.alpha")) {
                        param.setResult("unknown");
                    } else if (key.equals("gsm.operator.numeric") || key.equals("gsm.sim.operator.numeric")) {
                        param.setResult("00101");
                    } else if (key.equals("gsm.operator.iso-country") || key.equals("gsm.sim.operator.iso-country")) {
                        param.setResult("xx");
                    } else if (key.substring(1, "ro.vendor.".length()).equals("ro.vendor.")) {
                        param.setResult(null);
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookSystemPropertiesGetSerial2(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName("android.os.SystemProperties")
                    .getDeclaredMethod("get", String.class, String.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    String result = (String) param.getResult();
                    if (result == null)
                        return;
                    String key = (String) param.args[0];
                    if (key == null)
                        return;
                    if (key.equals("ro.serialno") || key.equals("ro.boot.serialno")) {
                        param.setResult("unknown");
                    } else if (key.substring(1, "ro.build.".length()).equals("ro.build.")) {
                        param.setResult(null);
                    } else if (key.equals("gsm.operator.alpha") || key.equals("gsm.sim.operator.alpha")) {
                        param.setResult("unknown");
                    } else if (key.equals("gsm.operator.numeric") || key.equals("gsm.sim.operator.numeric")) {
                        param.setResult("00101");
                    } else if (key.equals("gsm.operator.iso-country") || key.equals("gsm.sim.operator.iso-country")) {
                        param.setResult("xx");
                    } else if (key.substring(1, "ro.vendor.".length()).equals("ro.vendor.")) {
                        param.setResult(null);
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookBuildGetRadioVersion(final Context context) {
        int minSdk = 14;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.os.Build.class.getName())
                    .getDeclaredMethod("getRadioVersion");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    String result = (String) param.getResult();
                    if (result == null)
                        return;
                    param.setResult("unknown");
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static class QueryMethodHook extends XC_MethodHook {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {

            Cursor cursor = (Cursor) param.getResult();
            Uri uri = (Uri) param.args[0];
            if (cursor == null || uri == null)
                return;
            if (uri.getAuthority().equals("com.google.android.gsf.gservices")) {
                MatrixCursor matrixCursor = new MatrixCursor(cursor.getColumnNames());
                param.setResult(matrixCursor);
            }
        }
    }
}
