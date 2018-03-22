package com.app.tc.c.pkmanager.xposed.hooks;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;

import java.lang.reflect.Member;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by MrRobot on 2018/3/13.
 */

public class ReadAccount extends BaseHook {
    public static final String TAG = "ReadAccount";

    public static boolean hook(final Context context) {
        boolean status = true;
        classLoader = context.getClassLoader();
        if (!hookAccountCreateFromParcel(context))
            status = false;
        return status;
    }

    private static boolean hookAccountCreateFromParcel(final Context context) {
        int minSdk = 5;
        int maxSdk = Integer.MAX_VALUE;
        try {
            if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk)
                return false;
            Member member = getClassForNameWithField(android.accounts.Account.class.getName(), "CREATOR")
                    .getDeclaredMethod("createFromParcel", Parcel.class);

            XposedBridge.hookMethod(member, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Account account = (Account) param.getResult();
                    if (account == null)
                        return;

                    AccountManager accountManager = AccountManager.get(context);
                    AuthenticatorDescription authenticatorDescriptions[] = accountManager.getAuthenticatorTypes();
                    String packageName = context.getPackageName();
                    boolean restricted = true;

                    for (AuthenticatorDescription auth : authenticatorDescriptions) {
                        if (auth.type.equals(account.type)
                                && auth.packageName.equals(packageName)) {
                            restricted = false;
                            break;
                        }
                    }

                    if (restricted) {
                        Account newAccount = new Account("private@pkManager", account.type);
                        param.setResult(newAccount);
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }
}
