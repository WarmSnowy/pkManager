package com.app.tc.c.pkmanager.xposed.hooks;

import android.content.Context;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;

import java.lang.reflect.Member;
import java.util.ArrayList;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class RecordAudio extends BaseHook {
    public static final String TAG = "RecordAudio";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookAudioRecordStartRecording(context))
            status = false;
        if (!hookAudioRecordStartRecordingMediaSyncEvent(context))
            status = false;
        if (!hookAudioRecordStop(context))
            status = false;
        if (!hookAudioManagerGetActiveRecordingConfigurations(context))
            status = false;
        if (!hookAudioManagerGetDevices(context))
            status = false;
        if (!hookAudioManagerRegisterAudioDeviceCallback(context))
            status = false;
        if (!hookAudioManagerRegisterAudioRecordingCallback(context))
            status = false;
        if (!hookMediaRecorderSetAudioSource(context))
            status = false;
        if (!hookMediaRecorderStartAudio(context))
            status = false;
        if (!hookMediaRecorderStopAudio(context))
            status = false;
        return status;
    }

    private static boolean hookAudioRecordStartRecording(final Context context) {
        int minSdk = 3;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.media.AudioRecord.class.getName())
                    .getDeclaredMethod("startRecording");
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

    private static boolean hookAudioRecordStartRecordingMediaSyncEvent(final Context context) {
        int minSdk = 16;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.media.AudioRecord.class.getName())
                    .getDeclaredMethod("startRecording", android.media.MediaSyncEvent.class);
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

    private static boolean hookAudioRecordStop(final Context context) {
        int minSdk = 3;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.media.AudioRecord.class.getName())
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

    private static boolean hookAudioManagerGetActiveRecordingConfigurations(final Context context) {
        int minSdk = 24;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(AudioManager.class).getClass().getName())
                    .getDeclaredMethod("getActiveRecordingConfigurations");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    if (param.getResult() != null)
                        param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookAudioManagerGetDevices(final Context context) {
        int minSdk = 23;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(AudioManager.class).getClass().getName())
                    .getDeclaredMethod("getDevices", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    AudioDeviceInfo[] audioDeviceInfos = (AudioDeviceInfo[]) param.getResult();
                    if (audioDeviceInfos == null || audioDeviceInfos.length == 0)
                        return;
                    param.setResult(new AudioDeviceInfo[0]);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }


    private static boolean hookAudioManagerRegisterAudioDeviceCallback(final Context context) {
        int minSdk = 23;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(AudioManager.class).getClass().getName())
                    .getDeclaredMethod("registerAudioDeviceCallback", AudioDeviceCallback.class, Handler.class);
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


    private static boolean hookAudioManagerRegisterAudioRecordingCallback(final Context context) {
        int minSdk = 24;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(AudioManager.class).getClass().getName())
                    .getDeclaredMethod("registerAudioRecordingCallback", AudioManager.AudioRecordingCallback.class, Handler.class);
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

    private static boolean hookMediaRecorderSetAudioSource(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(android.media.MediaRecorder.class.getName())
                    .getDeclaredMethod("setAudioSource", int.class);
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

    private static boolean hookMediaRecorderStartAudio(final Context context) {
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


    private static boolean hookMediaRecorderStopAudio(final Context context) {
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

