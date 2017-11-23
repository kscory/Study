package com.example.kyung.broadcastreceiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
    1. 리시버를 동적으로 사용할 때는 start와 stop에서 각각 regist 처리
    2. 항상 사용할 때는 manifest에 등록하여 사용
 */
public class MainActivity extends BaseActivity {
    BroadcastReceiver receiver;
    IntentFilter intentFilter;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 엑티비티가 떠있을 경우 여기서 intent를 받아서 처리
    }

    @Override
    public void init() {
        setContentView(R.layout.activity_main);
        receiver = new MyReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        // 현재 화면에 보이고 있는 엑티비티 가져오기
        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        registerReceiver(receiver, intentFilter);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        unregisterReceiver(receiver);
//    }
}
