package com.app.tc.c.pkmanager.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.app.tc.c.pkmanager.App;
import com.app.tc.c.pkmanager.R;
import com.app.tc.c.pkmanager.bean.APKInfo;
import com.app.tc.c.pkmanager.bean.Hook;
import com.app.tc.c.pkmanager.bean.Hook_;
import com.app.tc.c.pkmanager.bean.PermissionSetting;
import com.app.tc.c.pkmanager.common.NoScrollListView;
import com.app.tc.c.pkmanager.ui.adapter.APPPermissionsAdapter;
import com.app.tc.c.pkmanager.utils.APKManager;
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

import io.objectbox.Box;
import io.objectbox.query.Query;

public class APPHooksSettingActivity extends AppCompatActivity {

    NoScrollListView listview;
    private Box<Hook> hookBox;
    private Query<Hook> hooksQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //查询Hook配置
        hookBox = ((App) getApplication()).getBoxStore().boxFor(Hook.class);
        hooksQuery = hookBox.query().order(Hook_.privacyItem).equal(Hook_.packageName, getIntent().getStringExtra("apkinfo")).build();
        List<Hook> hooks = hooksQuery.find();
        List<PermissionSetting> permissionSettingList = getHookList();
        for (PermissionSetting permissionSetting : permissionSettingList) {
            for (Hook hook : hooks) {
                if (permissionSetting.getPrivacyItem().equals(hook.getPrivacyItem())) {
                    permissionSetting.setEnable(1);
                    permissionSetting.setId(hook.getId());
                }
            }
        }


        APKInfo apkInfo = APKManager.getPackagePermission(getApplicationContext(), getIntent().getStringExtra("apkinfo"));

        setContentView(R.layout.activity_apppermissions_setting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listview = findViewById(R.id.app_permissions_list);

        toolbar.setLogo(apkInfo.icon);
        toolbar.setTitle(apkInfo.apk_name);

        APPPermissionsAdapter adapter = new APPPermissionsAdapter(permissionSettingList, getApplicationContext());
        listview.setAdapter(adapter);
    }

    private List<PermissionSetting> getHookList() {
        List<PermissionSetting> permissionsSettings = new ArrayList<PermissionSetting>();
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), DetermineActivity.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), GetApplications.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), GetCalendars.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), GetCallLog.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), GetContacts.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), GetLocation.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), GetMessages.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), GetSensors.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), ReadAccount.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), ReadClipboard.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), ReadIdentifiers.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), ReadNetwork.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), ReadNotifications.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), ReadSync.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), ReadTelephony.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), RecordAudio.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), RecordVideo.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), SendMessages.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), UseAnalytics.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), UseCamera.TAG, 0));
        permissionsSettings.add(new PermissionSetting(0L, getIntent().getStringExtra("apkinfo"), UseTracking.TAG, 0));
        return permissionsSettings;
    }
}
