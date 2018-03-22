package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.Context;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class ReadNetwork extends BaseHook {
    public static final String TAG = "ReadNetwork";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (hookNetworkInfoGetExtraInfo(context))
            status = false;
        if (hookTelephonyManagerGetAllCellInfo(context))
            status = false;
        if (hookTelephonyManagerGetCellLocation(context))
            status = false;
        if (hookTelephonyManagerGetNeighboringCellInfo(context))
            status = false;
        if (hookTelephonyManagerListen(context))
            status = false;
        if (hookWifiManagerGetConfiguredNetworks(context))
            status = false;
        if (hookWifiManagerGetScanResults(context))
            status = false;
        if (hookWifiInfoGetBSSID(context))
            status = false;
        if (hookWifiInfoGetSSID(context))
            status = false;
        return status;
    }

    private static boolean hookNetworkInfoGetExtraInfo(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.net.NetworkInfo.class.getName())
                    .getDeclaredMethod("getExtraInfo");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    String result = (String) param.getResult();
                    if (result == null)
                        return;

                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetAllCellInfo(final Context context) {
        int minSdk = 17;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getAllCellInfo");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    List<CellInfo> result = (List<CellInfo>) param.getResult();
                    if (result == null)
                        return;

                    param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetCellLocation(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getCellLocation");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    android.telephony.CellLocation result = (android.telephony.CellLocation) param.getResult();
                    if (result == null)
                        return;

                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetNeighboringCellInfo(final Context context) {
        int minSdk = 3;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getNeighboringCellInfo");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    List<NeighboringCellInfo> result = (List<NeighboringCellInfo>) param.getResult();
                    if (result == null)
                        return;

                    param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerListen(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("listen", PhoneStateListener.class, int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    boolean restricted = false;
                    int events = (int) param.args[1];
                    if ((events & 16) != 0) {
                        restricted = true;
                        events = events ^ 16;
                    }
                    if ((events & 1024) != 0) {
                        restricted = true;
                        events = events ^ 1024;
                    }
                    if (restricted)
                        param.args[1] = events;
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean hookWifiManagerGetConfiguredNetworks(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.WIFI_SERVICE).getClass().getName())
                    .getDeclaredMethod("getConfiguredNetworks");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    if (param.getResult() == null)
                        return;
                    param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean hookWifiManagerGetScanResults(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.WIFI_SERVICE).getClass().getName())
                    .getDeclaredMethod("getScanResults");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    if (param.getResult() == null)
                        return;
                    param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean hookWifiInfoGetBSSID(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.net.wifi.WifiInfo.class.getName())
                    .getDeclaredMethod("getBSSID");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    if (param.getResult() == null)
                        return;
                    param.setResult("00:00:00:00:00:00");
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean hookWifiInfoGetSSID(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.net.wifi.WifiInfo.class.getName())
                    .getDeclaredMethod("getSSID");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    if (param.getResult() == null)
                        return;

                    param.setResult("\"private\"");
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }
}
