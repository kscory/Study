package com.example.kyung.musicplayer;

import android.Manifest;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.kyung.musicplayer.util.PermissionUtil;

/**
 * Created by Kyung on 2017-10-15.
 */

public abstract class BaseActivity extends AppCompatActivity implements PermissionUtil.PermCheckAfterInit {

    private String permissions[] = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    PermissionUtil permissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 퍼미션 체크
        checkPerm();
        // 미디어 볼륨을 기본으로 변경
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    private void checkPerm(){
        permissionUtil = new PermissionUtil(this,permissions);
        permissionUtil.checkVersion();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtil.onResult(requestCode,grantResults);
    }

    @Override
    public void baseInit() {
        init();
    }

    public abstract void init();
}
