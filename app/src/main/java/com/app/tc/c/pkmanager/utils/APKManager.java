package com.app.tc.c.pkmanager.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.app.tc.c.pkmanager.bean.APKInfo;
import com.app.tc.c.pkmanager.bean.PermissionSetting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MrRobot on 2018/1/18.
 */

public class APKManager {
    public static List<APKInfo> getPackages(Context context) {
        // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        List<APKInfo> apks = new ArrayList<APKInfo>();

        for (PackageInfo i : packages) {
            APKInfo temp = new APKInfo();
            temp.apk_name = i.applicationInfo.loadLabel(context.getPackageManager()).toString();
            temp.package_name = i.packageName;
            temp.icon = i.applicationInfo.loadIcon(context.getPackageManager());
            temp.flags = i.applicationInfo.flags;
            apks.add(temp);
            //getPackagePermission(context.getApplicationContext(),temp.package_name);
        }
        Collections.sort(apks);
        return apks;
    }

    public static APKInfo getPackagePermission(Context context, String packageName) {
        APKInfo apkInfo = new APKInfo();

        try {
            PackageInfo pack = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            apkInfo.icon = pack.applicationInfo.loadIcon(context.getPackageManager());
            apkInfo.package_name = pack.packageName;
            apkInfo.apk_name = pack.applicationInfo.loadLabel(context.getPackageManager()).toString();
            apkInfo.flags = pack.applicationInfo.flags;
            if (pack.requestedPermissions != null) {
                for (String permission : pack.requestedPermissions) {
                    PermissionSetting temp = new PermissionSetting();
                    temp.setPackageName(pack.packageName);
                    temp.setPrivacyItem(permission);
                    //temp.setUid(getUID(context, packageName));
                    temp.setEnable(0);
                    apkInfo.permissionsSettings.add(temp);
                }
            }
            //apkInfo.requestedPermissions = pack.requestedPermissions;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            apkInfo = null;
        }
        return apkInfo;
    }

    public static int getUID(Context context, String packageName) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo("com.speedsoftware.rootexplorer", PackageManager.GET_META_DATA);
            return ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
