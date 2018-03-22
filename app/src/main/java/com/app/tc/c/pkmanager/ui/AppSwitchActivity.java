package com.app.tc.c.pkmanager.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.tc.c.pkmanager.R;
import com.app.tc.c.pkmanager.bean.APKInfo;
import com.app.tc.c.pkmanager.ui.adapter.APPListAdapter;
import com.app.tc.c.pkmanager.utils.APKManager;
import com.app.tc.c.pkmanager.utils.NavigatorUtils;

import java.util.List;


public class AppSwitchActivity extends AppCompatActivity {

    private ListView listview;
    private List<APKInfo> apkInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_switch);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listview = findViewById(R.id.app_list);


        apkInfoList = APKManager.getPackages(getApplicationContext());
        APPListAdapter adapter = new APPListAdapter(apkInfoList, getApplicationContext());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavigatorUtils.toAPPPermissionsSetting(getApplicationContext(), apkInfoList.get(position));
            }
        });

    }
}
