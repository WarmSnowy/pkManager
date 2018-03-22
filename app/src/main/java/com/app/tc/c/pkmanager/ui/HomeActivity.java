package com.app.tc.c.pkmanager.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.tc.c.pkmanager.R;
import com.app.tc.c.pkmanager.common.BaseActivity;
import com.app.tc.c.pkmanager.common.Constant;
import com.app.tc.c.pkmanager.ui.view.MyScrollView;
import com.app.tc.c.pkmanager.utils.NavigatorUtils;
import com.app.tc.c.pkmanager.utils.TextUtils;
import com.app.tc.c.pkmanager.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyScrollView.OnScrollListener {

    private static final String TAG = HomeActivity.class.getSimpleName();


    /**
     * 左右两大块 UI
     */
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    TextView tv_name;

    /**
     * top bar 相关UI
     */
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_mini_avator)
    ImageView iv_mini_avator;

    /**
     * 其他UI
     */
    @BindView(R.id.msv_content)
    MyScrollView mScrollView;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    /*    @Bind(R.id.btn_send_big)
        Button btn_send_big;*/
    @BindView(R.id.btn_receive_big)
    Button btn_receive_big;

    @BindView(R.id.button_harassment_interception)
    RelativeLayout rl_device;


    int mContentHeight = 0;
    boolean mIsExist = false;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);


        //Android6.0 requires android.permission.READ_EXTERNAL_STORAGE
        //TODO
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_FILE);
        } else {
            //初始化
            init();
        }
    }

    @Override
    protected void onResume() {
        updateBottomData();
        super.onResume();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_WRITE_FILE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //初始化
                init();
            } else {
                // Permission Denied
                ToastUtils.show(this, getResources().getString(R.string.tip_permission_denied));
                finish();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 初始化
     */
    private void init() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        //设置设备名称
        String device = TextUtils.isNullOrBlank(android.os.Build.DEVICE) ? Constant.DEFAULT_SSID : android.os.Build.DEVICE;
        try {//设置左边抽屉的设备名称
            tv_name = mNavigationView.getHeaderView(0).findViewById(R.id.tv_name);
            tv_name.setText(device);
        } catch (Exception e) {
            //maybe occur some exception
        }

        mScrollView.setOnScrollListener(this);

        updateBottomData();

    }

    private void updateBottomData() {
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
//                super.onBackPressed();
                if (mIsExist) {
                    this.finish();
                } else {
                    ToastUtils.show(getContext(), getContext().getResources().getString(R.string.tip_call_back_agin_and_exist)
                            .replace("{appName}", getContext().getResources().getString(R.string.app_name)));
                    mIsExist = true;
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mIsExist = false;
                        }
                    }, 2 * 1000);

                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            Log.i(TAG, "R.id.nav_about------>>> click");
            showAboutMeDialog();
        } else {
            ToastUtils.show(getContext(), getResources().getString(R.string.tip_next_version_update));
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick({R.id.btn_receive_big, R.id.iv_mini_avator,
            R.id.button_harassment_interception, R.id.button_virus_scan, R.id.button_background,
            R.id.button_permission, R.id.button_networknetwork, R.id.button_donate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_receive_big: {
                NavigatorUtils.toReceiverWaitingUI(getContext());
                break;
            }
            case R.id.iv_mini_avator: {
                if (mDrawerLayout != null) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            }
            case R.id.button_permission: {
                NavigatorUtils.toAppSwitch(getContext());
                break;
            }

        }
    }

    //自定义ScrollView的监听
    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i(TAG, "l-->" + l + ",t-->" + t + ",oldl-->" + oldl + ",oldt-->" + oldt);
        mContentHeight = ll_main.getMeasuredHeight();
        //一半的位置时候
        // topbar上面的两个小按钮 跟 主页上面的两个大按钮的alpha值是对立的 即 alpha 与 1-alpha的关系
        if (t > mContentHeight / 2) {
            float sAlpha = (t - mContentHeight / 2) / (float) (mContentHeight / 2);
            ll_main.setAlpha(1 - sAlpha);
            tv_title.setAlpha(0);
        } else {
            float tAlpha = t / (float) mContentHeight / 2;
            tv_title.setAlpha(1 - tAlpha);
        }

    }

    /**
     * 显示对话框
     */
    private void showAboutMeDialog() {
        View contentView = View.inflate(getContext(), R.layout.view_about_me, null);
        contentView.findViewById(R.id.tv_github).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProject();
            }
        });
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.title_about_me))
                .setView(contentView)
                .setPositiveButton(getResources().getString(R.string.str_weiguan), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toProject();
                    }
                })
                .create()
                .show();
    }

    /**
     * 跳转到项目
     */
    private void toProject() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("https://github.com/WarmSnowy/pkManager");
        intent.setData(uri);
        getContext().startActivity(intent);
    }
}
