package com.example.kyung.servicebasic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent  = new Intent(this,MyService.class);
    }

    // 서비스 시작
    public void start(View view){
        startService(intent);
    }
    // 서비스 종료
    public void stop(View view){
        stopService(intent);
        // 항상 해주어야 한다.
        isService = false;
    }

    boolean isService = false;
    // 서비스를 담아두는 변수
    MyService service;
    // 서비스와의 연결 통로
    ServiceConnection con = new ServiceConnection() {
        // 서비스와 연결되는 순간 호출되는 함수
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) { // I가 있으면 Interface 따라서 서비스에서 Interface를 구현해야 함.
            isService = true;
            service = ((MyService.CustomBinder)iBinder).getService();
            Toast.makeText(MainActivity.this,"total= " + service.getTotal(),Toast.LENGTH_SHORT).show();
        }
        // 서비스가 중단되거나 연결이 도중에 끊겼을 때 발생한다.
        // 예) 정상적으로 stop이 호출되고, onDestroy가 발생하면 호출되지 않는다.ㅋㅋ;;
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isService = false;
        }
    };

    public void bind(View view){
        bindService(intent, con, Context.BIND_AUTO_CREATE); // BindAutoCreate - bind가 없으면 생성, 있으면 불러옴. 거의 얘만 씀
    }
    public void unbind(View view){
        if(isService) {
            unbindService(con);
        }
        isService = false;
    }

    public void startForeground(View view){

    }

    public void stopForeground(View view){

    }
}


