package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Member;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class ReadTelephony extends BaseHook {
    public static final String TAG = "ReadTelephony";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookSubscriptionInfoGetIccId(context))
            status = false;
        if (!hookSubscriptionInfoGetMcc(context))
            status = false;
        if (!hookSubscriptionInfoGetMnc(context))
            status = false;
        if (!hookSubscriptionInfoGetNumber(context))
            status = false;
        if (!hookSubscriptionInfoGetSubscriptionId(context))
            status = false;
        if (!hookTelephonyManagerGetDeviceId(context))
            status = false;
        if (!hookTelephonyManagerGetDeviceIdSlot(context))
            status = false;
        if (!hookTelephonyManagerGetGroupIdLevel1(context))
            status = false;
        if (!hookTelephonyManagerGetImei(context))
            status = false;
        if (!hookTelephonyManagerGetImeiSlot(context))
            status = false;
        if (!hookTelephonyManagerGetLine1Number(context))
            status = false;
        if (!hookTelephonyManagerGetMeid(context))
            status = false;
        if (!hookTelephonyManagerGetMeidSlot(context))
            status = false;
        if (!hookTelephonyManagerGetNetworkSpecifier(context))
            status = false;
        if (!hookTelephonyManagerGetSimSerialNumber(context))
            status = false;
        if (!hookTelephonyManagerGetSubscriberId(context))
            status = false;
        if (!hookTelephonyManagerGetSubscriberIdSlot(context))
            status = false;
        if (!hookTelephonyManagerGetVoiceMailAlphaTag(context))
            status = false;
        if (!hookTelephonyManagerGetVoiceMailNumber(context))
            status = false;

        return status;
    }

    private static boolean hookSubscriptionInfoGetIccId(final Context context) {
        int minSdk = 22;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.telephony.SubscriptionInfo.class.getName())
                    .getDeclaredMethod("getIccId");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookSubscriptionInfoGetMcc(final Context context) {
        int minSdk = 22;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.telephony.SubscriptionInfo.class.getName())
                    .getDeclaredMethod("getMcc");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    if (param.getResult() == null)
                        return;
                    param.setResult(1);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookSubscriptionInfoGetMnc(final Context context) {
        int minSdk = 22;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.telephony.SubscriptionInfo.class.getName())
                    .getDeclaredMethod("getMnc");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    if (param.getResult() == null)
                        return;
                    param.setResult(1);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookSubscriptionInfoGetNumber(final Context context) {
        int minSdk = 22;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.telephony.SubscriptionInfo.class.getName())
                    .getDeclaredMethod("getNumber");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult("0000000");
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookSubscriptionInfoGetSubscriptionId(final Context context) {
        int minSdk = 22;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.telephony.SubscriptionInfo.class.getName())
                    .getDeclaredMethod("getSubscriptionId");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(0);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetDeviceId(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getDeviceId");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    TelephonyManager telephonyManager = (TelephonyManager) param.thisObject;
                    String fake = null;
                    switch (telephonyManager.getPhoneType()) {
                        case 1: {
                            fake = "000000000000000";
                            break;
                        }
                        case 2: {
                            fake = "00000000000000";
                            break;
                        }
                        default: {
                        }
                    }
                    if (param.getResult() != null) {
                        param.setResult(fake);
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }


    private static boolean hookTelephonyManagerGetDeviceIdSlot(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getDeviceId", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    TelephonyManager telephonyManager = (TelephonyManager) param.thisObject;
                    String fake = null;
                    switch (telephonyManager.getPhoneType()) {
                        case 1: {
                            fake = "000000000000000";
                            break;
                        }
                        case 2: {
                            fake = "00000000000000";
                            break;
                        }
                        default: {
                        }
                    }
                    if (param.getResult() != null) {
                        param.setResult(fake);
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetGroupIdLevel1(final Context context) {
        int minSdk = 18;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getGroupIdLevel1");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetImei(final Context context) {
        int minSdk = 26;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getImei");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    TelephonyManager telephonyManager = (TelephonyManager) param.thisObject;
                    String fake = null;
                    switch (telephonyManager.getPhoneType()) {
                        case 1: {
                            fake = "000000000000000";
                            break;
                        }
                        default: {
                        }
                    }
                    if (param.getResult() != null && fake != null) {
                        param.setResult(fake);
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetImeiSlot(final Context context) {
        int minSdk = 26;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getImei");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    TelephonyManager telephonyManager = (TelephonyManager) param.thisObject;
                    String fake = null;
                    switch (telephonyManager.getPhoneType()) {
                        case 1: {
                            fake = "000000000000000";
                            break;
                        }
                        default: {
                        }
                    }
                    if (param.getResult() != null && fake != null) {
                        param.setResult(fake);
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetLine1Number(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getLine1Number");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    if (param.getResult() != null) {
                        param.setResult("00000000000");
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetMeid(final Context context) {
        int minSdk = 26;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getMeid");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    TelephonyManager telephonyManager = (TelephonyManager) param.thisObject;
                    String fake = null;
                    switch (telephonyManager.getPhoneType()) {
                        case 2: {
                            fake = "00000000000000";
                            break;
                        }
                        default: {
                        }
                    }
                    if (param.getResult() != null && fake != null) {
                        param.setResult(fake);
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetMeidSlot(final Context context) {
        int minSdk = 26;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getMeid");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    TelephonyManager telephonyManager = (TelephonyManager) param.thisObject;
                    String fake = null;
                    switch (telephonyManager.getPhoneType()) {
                        case 2: {
                            fake = "00000000000000";
                            break;
                        }
                        default: {
                        }
                    }
                    if (param.getResult() != null && fake != null) {
                        param.setResult(fake);
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetNetworkSpecifier(final Context context) {
        int minSdk = 26;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getNetworkSpecifier");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetSimSerialNumber(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getSimSerialNumber");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    TelephonyManager telephonyManager = (TelephonyManager) param.thisObject;
                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetSubscriberId(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getSubscriberId");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetSubscriberIdSlot(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getSubscriberId", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(null);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookTelephonyManagerGetVoiceMailAlphaTag(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getVoiceMailAlphaTag");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    if (param.getResult() != null)
                        param.setResult("00000000000");
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }


    private static boolean hookTelephonyManagerGetVoiceMailNumber(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(Context.TELEPHONY_SERVICE).getClass().getName())
                    .getDeclaredMethod("getVoiceMailNumber");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    if (param.getResult() != null)
                        param.setResult("00000000000");
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }
}
