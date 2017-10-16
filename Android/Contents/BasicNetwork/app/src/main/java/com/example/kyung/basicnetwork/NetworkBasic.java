package com.example.kyung.basicnetwork;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kyung on 2017-10-16.
 */

/*
    - 네트워킹
    1. 권한설정 > 런타임권한(x)
    2. Thread > 네트워크를 통한 데이터 이용은 Sub Thread
    3. HttpURLConnection > 내장 Api (안드로이드에서 제공하는 기본 Api)
        > Retrofit (내부에 Thread 포함)
        > Rx (내부에 Thread 포함, Thread 관리기능 포함, 예외처리 특화)
 */
public class NetworkBasic extends View {

    TextView textView;
    Context context;
    Activity activity;

    public NetworkBasic(Context context) {
        super(context);
        this.context = context;
        if(context instanceof Activity){
            activity = (Activity) context;
        }

        textView = (TextView) findViewById(R.id.textView);
        new NetworkThread().start();
    }

    /*
        - HttpURLConnection 사용하기
        1. URL 객체를 선언 (웹주소를 가지고 생성)
        2. URL 객체에서 서버 연결을 해준다 > HttpURLConnection 을 생성 = Stream
        3. 커넥션의 방식을 설정 (기본값 = GET)
        4. 연결되어 있는 Stream 을 통해서 데이터를 가져온다.
        5. 연결(Stream)을 닫는다.
     */
    class NetworkThread extends Thread{
        public void run(){
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("fastcampus.co.kr");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                // 통신이 성공인지 체크
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //여기서부터는 파일에서 데이터를 가져오는 것과 동일
                    InputStreamReader isr = new InputStreamReader(con.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    String temp = "";
                    while ((temp = br.readLine()) != null) {
                        result.append(temp).append("\n");
                    }
                    br.close();
                    isr.close();
                } else {
                    Log.e("ServerError",con.getResponseCode()+"");
                }
                con.disconnect();
            } catch (Exception e){
                Log.e("Error",e.toString());
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }
}
