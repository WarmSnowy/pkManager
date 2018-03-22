package com.app.tc.c.pkmanager.bean;

/**
 * Created by MrRobot on 2018/3/5.
 */

public class PermissionSetting extends Hook {
    private int enable;

    public PermissionSetting() {

    }

    public PermissionSetting(Long id, String packageName, String privacyItem, int enable) {
        this.id = id;
        //this.uid = uid;
        this.packageName = packageName;
        this.privacyItem = privacyItem;
        this.enable = enable;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable1) {
        enable = enable1;
    }
}
