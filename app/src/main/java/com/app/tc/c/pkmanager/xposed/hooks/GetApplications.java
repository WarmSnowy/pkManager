package com.app.tc.c.pkmanager.xposed.hooks;

import android.app.ActivityManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.UserHandle;

import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class GetApplications extends BaseHook {
    public static final String TAG = "GetApplications";
    //private static ClassLoader classLoader;

    public static boolean hook(final Context context, final int uid) {
        classLoader = context.getClassLoader();
        boolean status = true;
        if (!hookGetRecentTasks(context))
            status = false;
        if (!hookGetRunningAppProcesses(context, uid))
            status = false;
        if (!hookGetRunningServices(context, uid))
            status = false;
        if (!hookGetRunningTasks(context))
            status = false;
        if (!hookGetInstalledProviders(context))
            status = false;
        if (!hookGetInstalledProvidersForPackage(context))
            status = false;
        if (!hookGetInstalledProvidersForProfile(context))
            status = false;
        if (!hookIntentCreateFromParcelOrPackage(context))
            status = false;
        if (!hookGetInstalledApplications(context, uid))
            status = false;
        if (!hookGetInstalledPackages(context, uid))
            status = false;
        if (!hookGetPackagesHoldingPermissions(context, uid))
            status = false;
        if (!hookQueryIntentActivities(context, uid))
            status = false;
        if (!hookGetPreferredPackages(context, uid))
            status = false;
        if (!hookQueryIntentActivityOptions(context, uid))
            status = false;
        if (!hookQueryIntentContentProviders(context, uid))
            status = false;
        if (!hookQueryIntentServices(context, uid))
            status = false;
        return status;
    }

    private static boolean hookGetRecentTasks(final Context context) {
        int minSdk = 1;
        int maxSdk = 20;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;

            Member member = getClassForName(context.getSystemService(ActivityManager.class).getClass().getName())
                    .getDeclaredMethod("getRecentTasks", int.class, int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookGetRunningAppProcesses(final Context context, final int uid) {
        int minSdk = 3;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(ActivityManager.class).getClass().getName())
                    .getMethod("getRunningAppProcesses");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = (List<ActivityManager.RunningAppProcessInfo>) param.getResult();
                    if (runningAppProcessInfoList == null)
                        return;
                    for (int i = 0; i < runningAppProcessInfoList.size(); i++) {
                        if (runningAppProcessInfoList.get(i).uid == uid) {
                            continue;
                        }
                        runningAppProcessInfoList.remove(i);
                        i--;
                    }
                    param.setResult(runningAppProcessInfoList);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookGetRunningServices(final Context context, final int uid) {
        int minSdk = 1;
        int maxSdk = 25;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(ActivityManager.class).getClass().getName())
                    .getDeclaredMethod("getRunningServices", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List<ActivityManager.RunningServiceInfo> runningServiceInfoList = (List<ActivityManager.RunningServiceInfo>) param.getResult();
                    if (runningServiceInfoList == null)
                        return;
                    for (int i = 0; i < runningServiceInfoList.size(); i++) {
                        if (runningServiceInfoList.get(i).uid == uid) {
                            continue;
                        }
                        runningServiceInfoList.remove(i);
                        i--;
                    }
                    param.setResult(runningServiceInfoList);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookGetRunningTasks(final Context context) {
        int minSdk = 1;
        int maxSdk = 20;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(ActivityManager.class).getClass().getName())
                    .getDeclaredMethod("getRunningTasks", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookGetInstalledProviders(final Context context) {
        int minSdk = 3;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(AppWidgetManager.class).getClass().getName())
                    .getMethod("getInstalledProviders");
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookGetInstalledProvidersForPackage(final Context context) {
        int minSdk = 26;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(AppWidgetManager.class).getClass().getName())
                    .getMethod("getInstalledProvidersForPackage", String.class, UserHandle.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookGetInstalledProvidersForProfile(final Context context) {
        int minSdk = 21;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getSystemService(AppWidgetManager.class).getClass().getName())
                    .getDeclaredMethod("getInstalledProvidersForProfile", UserHandle.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(new ArrayList<>());
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookIntentCreateFromParcelOrPackage(final Context context) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForNameWithField(android.content.Intent.class.getName(), "CREATOR")
                    .getDeclaredMethod("createFromParcel", Parcel.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Intent intent = (Intent) param.getResult();
                    if (intent == null)
                        return;
                    String action = intent.getAction();
                    if (action == null)
                        return;

                    switch (action) {
                        case "android.intent.action.PACKAGE_ADDED":
                        case "android.intent.action.PACKAGE_CHANGED":
                        case "android.intent.action.PACKAGE_DATA_CLEARED":
                        case "android.intent.action.PACKAGE_FIRST_LAUNCH":
                        case "android.intent.action.PACKAGE_FULLY_REMOVED":
                        case "android.intent.action.PACKAGE_INSTALL":
                        case "android.intent.action.PACKAGE_NEEDS_VERIFICATION":
                        case "android.intent.action.PACKAGE_REMOVED":
                        case "android.intent.action.PACKAGE_REPLACED":
                        case "android.intent.action.PACKAGE_RESTARTED":
                        case "android.intent.action.PACKAGE_VERIFIED": {
                            Uri uri = Uri.parse("package:" + context.getPackageName());
                            intent.setData(uri);
                            param.setResult(intent);
                            break;
                        }
                        case "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE":
                        case "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE":
                        case "android.intent.action.PACKAGES_SUSPENDED":
                        case "android.intent.action.PACKAGES_UNSUSPENDED": {
                            String extra = Array.newInstance(String.class, 0).toString();
                            intent.putExtra("android.intent.extra.changed_package_list", extra);
                            param.setResult(intent);
                            break;
                        }
                        default:
                            break;
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookGetInstalledApplications(final Context context, final int uid) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getPackageManager().getClass().getName())
                    .getDeclaredMethod("getInstalledApplications", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List<ApplicationInfo> applicationInfoList = (List<ApplicationInfo>) param.getResult();
                    if (applicationInfoList == null)
                        return;
                    for (int i = 0; i < applicationInfoList.size(); i++) {
                        if (applicationInfoList.get(i).uid == uid) {
                            continue;
                        }
                        applicationInfoList.remove(i);
                        i--;
                    }
                    param.setResult(applicationInfoList);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookGetInstalledPackages(final Context context, final int uid) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getPackageManager().getClass().getName())
                    .getDeclaredMethod("getInstalledPackages", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List<PackageInfo> packages = (List<PackageInfo>) param.getResult();
                    if (packages == null) {
                        return;
                    }
                    for (int i = 0; i < packages.size(); i++) {
                        if (packages.get(i).applicationInfo.uid == uid) {
                            continue;
                        }
                        packages.remove(i);
                        i--;
                    }
                    param.setResult(packages);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookGetPackagesHoldingPermissions(final Context context, final int uid) {
        int minSdk = 18;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getPackageManager().getClass().getName())
                    .getDeclaredMethod("getPackagesHoldingPermissions", String[].class, int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List<PackageInfo> packageInfoList = (List<PackageInfo>) param.getResult();
                    if (packageInfoList == null) {
                        return;
                    }
                    for (int i = 0; i < packageInfoList.size(); i++) {
                        if (packageInfoList.get(i).applicationInfo.uid == uid) {
                            continue;
                        }
                        packageInfoList.remove(i);
                        i--;
                    }
                    param.setResult(packageInfoList);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookQueryIntentActivities(final Context context, final int uid) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getPackageManager().getClass().getName())
                    .getDeclaredMethod("queryIntentActivities", Intent.class, int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List<ResolveInfo> resolveInfoList = (List<ResolveInfo>) param.getResult();
                    if (resolveInfoList == null) {
                        return;
                    }
                    for (int i = 0; i < resolveInfoList.size(); i++) {
                        if (resolveInfoList.get(i).activityInfo.applicationInfo.uid == uid) {
                            continue;
                        }
                        resolveInfoList.remove(i);
                        i--;
                    }
                    param.setResult(resolveInfoList);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookGetPreferredPackages(final Context context, final int uid) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getPackageManager().getClass().getName())
                    .getDeclaredMethod("getPreferredPackages", int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List<PackageInfo> resolveInfoList = (List<PackageInfo>) param.getResult();
                    if (resolveInfoList == null) {
                        return;
                    }
                    for (int i = 0; i < resolveInfoList.size(); i++) {
                        if (resolveInfoList.get(i).applicationInfo.uid == uid) {
                            continue;
                        }
                        resolveInfoList.remove(i);
                        i--;
                    }
                    param.setResult(resolveInfoList);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookQueryIntentActivityOptions(final Context context, final int uid) {
        int minSdk = 1;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getPackageManager().getClass().getName())
                    .getDeclaredMethod("queryIntentActivityOptions", ComponentName.class, Intent[].class, Intent.class, int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List<ResolveInfo> resolveInfoList = (List<ResolveInfo>) param.getResult();
                    if (resolveInfoList == null) {
                        return;
                    }
                    for (int i = 0; i < resolveInfoList.size(); i++) {
                        if (resolveInfoList.get(i).activityInfo.applicationInfo.uid == uid) {
                            continue;
                        }
                        resolveInfoList.remove(i);
                        i--;
                    }
                    param.setResult(resolveInfoList);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookQueryIntentContentProviders(final Context context, final int uid) {
        int minSdk = 19;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getPackageManager().getClass().getName())
                    .getDeclaredMethod("queryIntentContentProviders", Intent.class, int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List<ResolveInfo> resolveInfoList = (List<ResolveInfo>) param.getResult();
                    if (resolveInfoList == null) {
                        return;
                    }
                    for (int i = 0; i < resolveInfoList.size(); i++) {
                        if (resolveInfoList.get(i).activityInfo.applicationInfo.uid == uid) {
                            continue;
                        }
                        resolveInfoList.remove(i);
                        i--;
                    }
                    param.setResult(resolveInfoList);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    private static boolean hookQueryIntentServices(final Context context, final int uid) {
        int minSdk = 19;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForName(context.getPackageManager().getClass().getName())
                    .getDeclaredMethod("queryIntentServices", Intent.class, int.class);
            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List<ResolveInfo> resolveInfoList = (List<ResolveInfo>) param.getResult();
                    if (resolveInfoList == null) {
                        return;
                    }

                    for (int i = 0; i < resolveInfoList.size(); i++) {
                        if (resolveInfoList.get(i).serviceInfo.applicationInfo.uid == uid) {
                            continue;
                        }
                        resolveInfoList.remove(i);
                        i--;
                    }
                    param.setResult(resolveInfoList);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

}
