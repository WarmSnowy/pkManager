package com.app.tc.c.pkmanager.bean;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrRobot on 2018/1/18.
 */

public class APKInfo implements Comparable<APKInfo> {
    public Drawable icon;
    public String apk_name;
    public String package_name;
    public int flags;
    public String[] requestedPermissions;
    public List<PermissionSetting> permissionsSettings = new ArrayList<PermissionSetting>();

    public APKInfo() {
        super();
        icon = null;
        apk_name = "";
        package_name = "";
    }

    @Override
    public int compareTo(APKInfo o) {
        int i = this.apk_name.compareTo(o.apk_name);
        if (i == 0) {
            return this.package_name.compareTo(o.package_name);
        }
        return i;
    }
}
