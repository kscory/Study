package com.example.kyung.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 안드로이드 권한요청
 * 가. 일반적인 권한 요청 -> Manifest에 설정
 *      - 디스크 쓰기, 읽기
 *
 */
public class MainActivity extends BaseActivity {

    @Override
    public void init() {
        setContentView(R.layout.activity_main);
    }

//    @Override
//    public int getLayoutView() {
//        return R.layout.activity_main;
//    }
}
