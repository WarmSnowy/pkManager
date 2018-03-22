package com.app.tc.c.pkmanager.xposed.hooks;

/**
 * Created by MrRobot on 2018/3/13.
 */

public abstract class BaseHook {
    //public static  String TAG;
    protected static ClassLoader classLoader;


    protected static Class<?> getClassForName(String name) throws ClassNotFoundException {
        return Class.forName(name, false, classLoader);
    }

    protected static Class<?> getClassForNameWithField(String name, String field) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getClassForName(name).getField(field).get(null).getClass();
    }

}
