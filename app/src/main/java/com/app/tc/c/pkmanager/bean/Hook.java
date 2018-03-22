package com.app.tc.c.pkmanager.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by MrRobot on 2018/3/4.
 */

@Entity
public class Hook {
    @Id
    Long id;
    //int uid;
    String packageName;
    String privacyItem;

    public Hook() {
    }

    public Hook(Long id, String packageName, String privacyItem) {
        this.id = id;
        //this.uid = uid;
        this.packageName = packageName;
        this.privacyItem = privacyItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String name) {
        packageName = name;
    }

/*    public int getUid() {
        return uid;
    }

    public void setUid(int uid1) {
        uid = uid1;
    }*/

    public String getPrivacyItem() {
        return privacyItem;
    }

    public void setPrivacyItem(String privacyItem1) {
        privacyItem = privacyItem1;
    }
}