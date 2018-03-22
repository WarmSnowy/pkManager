package com.app.tc.c.pkmanager;

import android.app.Application;

import com.app.tc.c.pkmanager.bean.MyObjectBox;

import io.objectbox.BoxStore;

/**
 * Created by MrRobot on 2018/3/4.
 */

public class App extends Application {
    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        getBoxStore();

    }

    public BoxStore getBoxStore() {
        if (boxStore == null)
            boxStore = MyObjectBox.builder().androidContext(App.this).build();
        return boxStore;
    }
}
