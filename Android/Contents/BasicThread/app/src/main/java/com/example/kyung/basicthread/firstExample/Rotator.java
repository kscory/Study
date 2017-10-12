package com.example.kyung.basicthread.firstexample;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Kyung on 2017-10-10.
 */

public class Rotator extends Thread {
    Handler handler;
    public static boolean ROTATINGFLAG = true;

    public Rotator(Handler handler){
        this.handler=handler;
    }

    // start 메소드가 호출되면 시작
    // run 함수의 코드만 subThread에서 실행
    public void run(){
        ROTATINGFLAG = true;
        while(ROTATINGFLAG){
            // 핸들러 측으로 메세지 전송
            Message msg = new Message();
            msg.what = ThreadOneView.ACTION_SET;
            handler.sendMessage(msg);

            //참고 : handler.sendEmptyMessage(MainActivity.ACTION_SET); =>  액션만 정해준다.

            // 매초 버튼의 회전값을 변경
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //run 이외의 함수는 subThread에서 실행되지 않는다.
    public void setStop(){
        ROTATINGFLAG = false;
    }
}
