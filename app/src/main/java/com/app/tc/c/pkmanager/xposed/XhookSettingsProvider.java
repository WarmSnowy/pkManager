package com.app.tc.c.pkmanager.xposed;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import com.app.tc.c.pkmanager.App;
import com.app.tc.c.pkmanager.bean.Hook;
import com.app.tc.c.pkmanager.bean.Hook_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;

/**
 * Created by MrRobot on 2018/1/30.
 */

public class XhookSettingsProvider extends ContentProvider {
    public final static String AUTHORITY = "com.app.tc.c.pkmanager.provider";
    public static final Uri HOOKSETTING_URI =
            Uri.parse("content://" + AUTHORITY + "/HOOKSETTING");
    public static final Uri HOOKUSED_URI =
            Uri.parse("content://" + AUTHORITY + "/HOOKUSED");
    public final static int HOOKSETTINGS = 0;
    public final static int HOOKUSED = 1;
    private static final String[] COLUMN_NAME = {/*"uid",*/ "packagename", "privacyItem"};
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "HOOKSETTING", HOOKSETTINGS);
        uriMatcher.addURI(AUTHORITY, "HOOKUSED", HOOKUSED);
    }

    private Box<Hook> hookBox;
    private Query<Hook> hooksQuery;

    @Override
    public boolean onCreate() {
        Log.i("Provider", "初始化");
        hookBox = ((App) getContext().getApplicationContext()).getBoxStore().boxFor(Hook.class);
        return hookBox != null;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = null;
        Log.i("provider", uri.toString());
        switch (uriMatcher.match(uri)) {
            case HOOKSETTINGS: {
                Log.i("provider查询", "成功！");
                hooksQuery = hookBox.query().order(Hook_.privacyItem).equal(Hook_.packageName, selection).build();
                List<Hook> hooks = hooksQuery.find();
                cursor = new MatrixCursor(COLUMN_NAME, 1);
                for (Hook hook : hooks) {
                    Log.i("provider查询", hook.getPrivacyItem());
                    cursor.addRow(new Object[]{/*hook.getUid(),*/ hook.getPackageName(), hook.getPrivacyItem()});
                }
                break;
            }

            default:
                break;
        }

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
