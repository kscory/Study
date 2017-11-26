package com.example.kyung.broadcastreceiver.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Kyung on 2017-11-26.
 */

public class GPSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if("android.location.PROVIDERS_CHANGED".equals(intent.getAction())) {
            Log.e("GPS","찍힘==============");
            Toast.makeText(context, "GPS 상태 바뀜", Toast.LENGTH_SHORT).show();
        }
    }
}
