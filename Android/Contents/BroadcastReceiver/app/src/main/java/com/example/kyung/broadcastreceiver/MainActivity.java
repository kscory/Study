package com.example.kyung.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;

import com.example.kyung.broadcastreceiver.broadcast.SMSReceiver;

/*
    1. 리시버를 동적으로 사용할 때는 start와 stop에서 각각 regist 처리
    2. 항상 사용할 때는 manifest에 등록하여 사용
 */
public class MainActivity extends BaseActivity {
    private TextView textSMS;
    private TextView textGPS;
    private TextView textNetwork;

    SMSReceiver sMSReceiver;
    IntentFilter sMSIntentFilter;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 엑티비티가 떠있을 경우 여기서 intent를 받아서 처리
    }

    @Override
    public void init() {
        setContentView(R.layout.activity_main);
        initView();
        setSMSReceiver();
    }

    private void setSMSReceiver(){
        sMSReceiver = new SMSReceiver(textSMS);
        sMSIntentFilter = new IntentFilter();
        sMSIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 리시버를 등록
        registerReceiver(sMSReceiver, sMSIntentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 리시버를 해제
        unregisterReceiver(sMSReceiver);
    }

    private void initView() {
        textSMS = findViewById(R.id.textSMS);
        textGPS = findViewById(R.id.textGPS);
        textNetwork = findViewById(R.id.textNetwork);
    }
}
