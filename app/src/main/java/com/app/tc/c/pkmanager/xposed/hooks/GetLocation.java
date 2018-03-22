package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.OnNmeaMessageListener;
import android.os.Build;
import android.os.Handler;
import android.os.Parcel;

import java.lang.reflect.Member;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class GetLocation extends BaseHook {
    public static final String TAG = "GetLocation";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookBaseBundleGet(context))
            status = false;
        if (!hookLocationCreateFromParcel(context))
            status = false;
        if (!hookLocationManagerAddNmeaListener1(context))
            status = false;
        if (!hookLocationManagerAddNmeaListener2(context))
            status = false;
        if (!hookLocationManagerAddNmeaListener3(context))
            status = false;
        if (!hookLocationManageraddProximityAlert(context))
            status = false;
        if (!hookGeofenceSetCircularRegion(context))
            status = false;
        if (!hookPlaceLikelihoodBufferGetCount(context))
            status = false;
        if (!hookPlaceLikelihoodBufferGet(context))
            status = false;

        return status;
    }

    private static boolean hookBaseBundleGet(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.os.BaseBundle.class.getName())
                    .getDeclaredMethod("get", String.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Object object = param.getResult();
                    if (param.getThrowable() != null || object == null)
                        return;

                    if (!param.args[0].equals("location"))
                        return;
                    android.location.Location location = new android.location.Location("privacy");
                    location.setLatitude(0);
                    location.setLongitude(0);
                    param.setResult(location);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }


    private static boolean hookLocationCreateFromParcel(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForNameWithField(android.location.Location.class.getName(), "CREATOR")
                    .getDeclaredMethod("createFromParcel", Parcel.class);

            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Location location = (Location) param.getResult();
                    if (location == null)
                        return;

                    //TODO:根据配置设定位置
                    double latitude = 0;
                    double longitude = 0;

                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    param.setResult(location);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookLocationManagerAddNmeaListener1(final Context context) {
        int minSdk = 5;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.location.LocationManager.class.getName())
                    .getDeclaredMethod("addNmeaListener", GpsStatus.NmeaListener.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(false);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookLocationManagerAddNmeaListener2(final Context context) {
        int minSdk = 24;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.location.LocationManager.class.getName())
                    .getDeclaredMethod("addNmeaListener", OnNmeaMessageListener.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(false);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }


    private static boolean hookLocationManagerAddNmeaListener3(final Context context) {
        int minSdk = 24;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.location.LocationManager.class.getName())
                    .getDeclaredMethod("addNmeaListener", OnNmeaMessageListener.class, Handler.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(false);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookLocationManageraddProximityAlert(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.location.LocationManager.class.getName())
                    .getDeclaredMethod("addProximityAlert", double.class, double.class, float.class, long.class, android.app.PendingIntent.class);
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


    private static boolean hookGeofenceSetCircularRegion(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName("com.google.android.gms.location.Geofence$Builder")
                    .getDeclaredMethod("setCircularRegion", double.class, double.class, float.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    param.args[0] = 0;
                    param.args[1] = 0;
                    param.args[2] = 0.1;
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

    private static boolean hookPlaceLikelihoodBufferGetCount(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName("com.google.android.gms.location.places.PlaceLikelihoodBuffer")
                    .getDeclaredMethod("getCount");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(0);
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

    private static boolean hookPlaceLikelihoodBufferGet(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName("com.google.android.gms.location.places.PlaceLikelihoodBuffer")
                    .getDeclaredMethod("get", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(null);
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
}
