package com.example.kyung.camera;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.kyung.camera.util.PermissionUtil;

/**
 * Created by Kyung on 2017-09-29.
 */

public abstract class BaseActivity extends AppCompatActivity implements PermissionUtil.Callback {

    private static final int REQ_CODE = 123;
    private static final String[] permissions = {
            // 카메라를 이용하기 위해서 필요한 퍼미션 두가지
            // 갤러리는 READ_EXTERNAL 인데 WRITE_EXTERNAL이 상위 개념이라 안해줘도 됨
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    PermissionUtil permissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permissionUtil = new PermissionUtil(this, REQ_CODE, permissions);
        permissionUtil.checkVersion();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtil.onResult(requestCode,grantResults);
    }

    @Override
    public void callinit() {
        init();
    }

    public abstract void init();
}
