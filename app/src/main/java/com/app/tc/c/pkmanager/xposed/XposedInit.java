package com.app.tc.c.pkmanager.xposed;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Process;
import android.util.Log;

import com.app.tc.c.pkmanager.BuildConfig;
import com.app.tc.c.pkmanager.bean.Hook;
import com.app.tc.c.pkmanager.xposed.hooks.DetermineActivity;
import com.app.tc.c.pkmanager.xposed.hooks.GetApplications;
import com.app.tc.c.pkmanager.xposed.hooks.GetCalendars;
import com.app.tc.c.pkmanager.xposed.hooks.GetCallLog;
import com.app.tc.c.pkmanager.xposed.hooks.GetContacts;
import com.app.tc.c.pkmanager.xposed.hooks.GetLocation;
import com.app.tc.c.pkmanager.xposed.hooks.GetMessages;
import com.app.tc.c.pkmanager.xposed.hooks.GetSensors;
import com.app.tc.c.pkmanager.xposed.hooks.ReadAccount;
import com.app.tc.c.pkmanager.xposed.hooks.ReadClipboard;
import com.app.tc.c.pkmanager.xposed.hooks.ReadIdentifiers;
import com.app.tc.c.pkmanager.xposed.hooks.ReadNetwork;
import com.app.tc.c.pkmanager.xposed.hooks.ReadNotifications;
import com.app.tc.c.pkmanager.xposed.hooks.ReadSync;
import com.app.tc.c.pkmanager.xposed.hooks.ReadTelephony;
import com.app.tc.c.pkmanager.xposed.hooks.RecordAudio;
import com.app.tc.c.pkmanager.xposed.hooks.RecordVideo;
import com.app.tc.c.pkmanager.xposed.hooks.SendMessages;
import com.app.tc.c.pkmanager.xposed.hooks.UseAnalytics;
import com.app.tc.c.pkmanager.xposed.hooks.UseCamera;
import com.app.tc.c.pkmanager.xposed.hooks.UseTracking;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class XposedInit implements IXposedHookZygoteInit, IXposedHookLoadPackage {
    private static final String TAG = "pkManager.Xposed";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        int uid = Process.myUid();
        String self = XposedInit.class.getPackage().getName();
        Log.i(TAG, "加载 " + loadPackageParam.packageName + ":" + uid);

        if (!"android".equals(loadPackageParam.packageName) && !self.equals(loadPackageParam.packageName))
            hookApplication(loadPackageParam);
    }

    private void hookApplication(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        final int uid = Process.myUid();
        Class<?> at = Class.forName("android.app.LoadedApk", false, lpparam.classLoader);

        XposedBridge.hookAllMethods(at, "makeApplication", new XC_MethodHook() {
            private boolean made = false;

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    if (!made) {
                        made = true;
                        Application app = (Application) param.getResult();

                        ContentResolver resolver = app.getContentResolver();

                        // Get hooks
                        List<Hook> hooks = new ArrayList<>();
                        Cursor cursor = null;
                        try {
                            cursor = resolver
                                    .query(XhookSettingsProvider.HOOKSETTING_URI, null,
                                            lpparam.packageName, null, null);
                            while (cursor != null && cursor.moveToNext()) {
                                Hook hook = new Hook();
                                hook.setPrivacyItem(cursor.getString(cursor.getColumnIndex("privacyItem")));
                                hooks.add(hook);
                            }
                        } finally {
                            if (cursor != null)
                                cursor.close();
                        }
                        hookPackage(app, hooks, uid);
                    }
                } catch (Throwable ex) {
                    Log.e(TAG, Log.getStackTraceString(ex));
                    XposedBridge.log(ex);
                }
            }

            private void hookPackage(final Context context, List<Hook> hooks, final int uid) throws Throwable {
                for (final Hook hook : hooks)
                    try {
                        switch (hook.getPrivacyItem()) {
                            case DetermineActivity.TAG:
                                break;
                            case GetApplications.TAG:
                                GetApplications.hook(context, uid);
                                break;
                            case GetCalendars.TAG:
                                GetCalendars.hook(context);
                                break;
                            case GetCallLog.TAG:
                                GetCallLog.hook(context);
                                break;
                            case GetContacts.TAG:
                                GetContacts.hook(context);
                                break;
                            case GetLocation.TAG:
                                GetLocation.hook(context);
                                break;
                            case GetMessages.TAG:
                                GetMessages.hook(context);
                                break;
                            case GetSensors.TAG:
                                GetSensors.hook(context);
                                break;
                            case ReadAccount.TAG:
                                ReadAccount.hook(context);
                                break;
                            case ReadClipboard.TAG:
                                ReadClipboard.hook(context);
                                break;
                            case ReadIdentifiers.TAG:
                                ReadIdentifiers.hook(context, lpparam);
                                break;
                            case ReadNetwork.TAG:
                                ReadNetwork.hook(context);
                                break;
                            case ReadNotifications.TAG:
                                ReadNotifications.hook(context);
                                break;
                            case ReadSync.TAG:
                                ReadSync.hook(context);
                                break;
                            case ReadTelephony.TAG:
                                ReadTelephony.hook(context);
                                break;
                            case RecordAudio.TAG:
                                RecordAudio.hook(context);
                                break;
                            case RecordVideo.TAG:
                                RecordVideo.hook(context);
                                break;
                            case SendMessages.TAG:
                                SendMessages.hook(context);
                                break;
                            case UseAnalytics.TAG:
                                UseAnalytics.hook(context);
                                break;
                            case UseCamera.TAG:
                                UseCamera.hook(context);
                                break;
                            case UseTracking.TAG:
                                UseTracking.hook(context);
                                break;
                            default:
                                break;
                        }


                    } catch (Throwable ex) {
                        XposedBridge.log(ex);
                    }
            }

        });
    }


    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        Log.i(TAG, "initZygote system=" + startupParam.startsSystemServer + " debug=" + BuildConfig.DEBUG);
    }

}
