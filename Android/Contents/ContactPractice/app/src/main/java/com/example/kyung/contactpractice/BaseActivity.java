package com.example.kyung.contactpractice;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.kyung.contactpractice.util.PermissionUtil;

/**
 * Created by Kyung on 2017-09-27.
 */

public abstract class BaseActivity extends AppCompatActivity implements PermissionUtil.Callback {

    private static final int REQ_CODE = 888;
    private static String[] permissions = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS
    };
    PermissionUtil permissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 퍼미션 체크
        permissionUtil = new PermissionUtil(REQ_CODE,permissions);
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
