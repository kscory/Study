package com.example.kyung.basicfragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.kyung.basicfragment.util.PermissionUtil;

/**
 * Created by Kyung on 2017-10-02.
 */

public abstract class BaseActivity extends AppCompatActivity implements PermissionUtil.Callback {
    // 상수 정의
    private static final int PER_CODE = 999;
    private static final String[] permission = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS
    };
    PermissionUtil pUtil;

    // 추상 메소드 정의 (init을 강제로 호출)
    public abstract void init();
    public abstract void changeInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 가로 모드시 리셋되게 하지 않음
        if(savedInstanceState != null) {
            changeInit();
            return;
        }

        pUtil = new PermissionUtil(this,PER_CODE,permission);
        pUtil.checkVersion();
    }

    // 퍼미션 체크
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pUtil.onResult(requestCode,grantResults);
    }

    // 인터페이스 구현 및 callback 호출
    public void callinit(){
        init();
    }
}
