package com.example.kyung.broadcastreceiver;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kyung on 2017-11-23.
 */

public abstract class BaseActivity extends AppCompatActivity implements PermissionUtil.Callback {

    private static final int REQ_CODE = 888;
    private static String[] permissions = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
    };
    PermissionUtil permissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 퍼미션 체크
        permissionUtil = new PermissionUtil(this, REQ_CODE, permissions);
        permissionUtil.checkVersion(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtil.onResult(this,requestCode,grantResults);
    }
    // 콜백함수 이용
    public void callInit(){
        init();
    }
    public abstract void init();
}
