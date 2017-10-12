package com.example.kyung.servicebasic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    // 컴포넌트는 바인더를 통해 서비스에 접근할 수 있다.
    class CustomBinder extends Binder {
        public CustomBinder(){

        }
        public MyService getService(){
            return MyService.this;
        }
    }

    IBinder binder = new CustomBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyService", "=====================onBind()");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("MyService", "=====================onUnbind()");
        return super.onUnbind(intent);
    }

    public int getTotal(){
        return total;
    }



    private int total=0;
    // 애너테이션 부분은 정해진 flag만 넣도록 쌓여져 있는데 아직 정상동작 안하는것 같음
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 포어 그라운드 서비스하기
        startForeground();

        Log.d("MyService", "=====================onStartCommand()");
        // Stop이 눌리지 않는다. 반복문이기 때문!!
        for(int i=0 ; i<1000 ; i++){
            total+=i;
            System.out.println("서비스에서 동작중입니다."+i);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    // 포어그라운드 서비스 번호
    public static final int FLAG = 17465;

    // 포어 그라운드 서비스하기
    // 포어그라운드 실행 메소드 구현
    private void startForeground(){
        // 포어그라운드 서비스에서 보여질 노티바 만들기
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this); //혹은 getBaseContext

        Notification notification = builder
                .setSmallIcon(R.mipmap.ic_launcher) // 아이콘
                .setContentTitle("노티 타이틀") // 타이틀
                .setContentText("노티 내용")  // 내용
                .build();
        startForeground(FLAG,notification);

        // 노티바 노출시키기
        // 노티피케이션 매니저를 통해서 노티바를 출력
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(FLAG,notification);
    }

    private void stopForeground(){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(FLAG);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "=====================onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "=====================onDestroy()");
//        stopForeground();
    }

}
