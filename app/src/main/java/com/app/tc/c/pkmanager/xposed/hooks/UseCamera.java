package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;

import java.lang.reflect.Member;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class UseCamera extends BaseHook {
    public static final String TAG = "UseCamera";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookCameraGetCameraInfo(context))
            status = false;
        if (!hookCameraGetNumberOfCameras(context))
            status = false;
        if (!hookCameraOpen(context))
            status = false;
        if (!hookCameraOpenCamera(context))
            status = false;
        if (!hookCameraOpenLegacyCamera(context))
            status = false;
        if (!hookCameraManager2OpenCamera(context))
            status = false;
        return status;
    }

    private static boolean hookCameraGetCameraInfo(final Context context) {
        int minSdk = 9;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.hardware.Camera.class.getName())
                    .getDeclaredMethod("getCameraInfo", int.class, Camera.CameraInfo.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(new java.lang.RuntimeException("privacy"));
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookCameraGetNumberOfCameras(final Context context) {
        int minSdk = 9;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.hardware.Camera.class.getName())
                    .getDeclaredMethod("getNumberOfCameras");
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


    private static boolean hookCameraOpen(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.hardware.Camera.class.getName())
                    .getDeclaredMethod("open");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(new java.lang.RuntimeException("privacy"));
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookCameraOpenCamera(final Context context) {
        int minSdk = 9;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.hardware.Camera.class.getName())
                    .getDeclaredMethod("open", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(new java.lang.RuntimeException("privacy"));
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookCameraOpenLegacyCamera(final Context context) {
        int minSdk = 21;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.hardware.Camera.class.getName())
                    .getDeclaredMethod("openLegacy", int.class, int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(new java.lang.RuntimeException("privacy"));
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookCameraManager2OpenCamera(final Context context) {
        int minSdk = 21;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(CameraManager.class).getClass().getName())
                    .getDeclaredMethod("openCamera", String.class, CameraDevice.StateCallback.class, Handler.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult(new android.hardware.camera2.CameraAccessException(CameraAccessException.CAMERA_DISABLED, "privacy"));
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

}
