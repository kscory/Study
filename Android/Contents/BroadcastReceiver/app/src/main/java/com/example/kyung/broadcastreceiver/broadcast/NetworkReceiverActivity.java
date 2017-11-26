package com.example.kyung.broadcastreceiver.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kyung.broadcastreceiver.R;

public class NetworkReceiverActivity extends AppCompatActivity {

    BroadcastReceiver networkBroadcast;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_receiver);

        setNetworkBrodcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
    }

    private void setNetworkBrodcastReceiver(){
        networkBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                    ConnectivityManager connectivityManager
                            = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
                    NetworkInfo _wifi_network =
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if(_wifi_network != null) {
                        // wifi, 데이터 둘 중 하나라도 있을 경우
                        if(_wifi_network != null && activeNetInfo != null){
                            // 로직 수행
                        }
                        // wifi, 데이터 둘 다 없을 경우
                        else{
                            // 로직 수행
                        }
                    }
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 리시버 등록
        registerReceiver(networkBroadcast, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 리시버 해제
        unregisterReceiver(networkBroadcast);
    }
}
