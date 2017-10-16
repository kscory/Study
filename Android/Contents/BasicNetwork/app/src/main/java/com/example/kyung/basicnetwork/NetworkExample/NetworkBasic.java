package com.example.kyung.basicnetwork.NetworkExample;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kyung.basicnetwork.R;

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
public class NetworkBasic extends FrameLayout {

    FrameLayout stage;
    TextView textView;
    Context context;

    public static final int SERVER_CODE = 999;

    // 데이터를 메인 쓰레드에 정의
    String data="";

    public NetworkBasic(Context context) {
        super(context);
        this.context = context;

        initView();
        NetworkThread networkThread = new NetworkThread(handler);
        networkThread.start();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.network,null);
        stage = (FrameLayout) view.findViewById(R.id.stage);
        textView = (TextView) view.findViewById(R.id.textView);
        addView(view);
    }

    public void setData(){
        textView.setText(data);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SERVER_CODE:
                    setData();
                    break;
            }
        }
    };

    /*
        - HttpURLConnection 사용하기
        1. URL 객체를 선언 (웹주소를 가지고 생성)
        2. URL 객체에서 서버 연결을 해준다 > HttpURLConnection 을 생성 = Stream
        3. 커넥션의 방식을 설정 (기본값 = GET)
        4. 연결되어 있는 Stream 을 통해서 데이터를 가져온다.
        5. 연결(Stream)을 닫는다.
     */
    class NetworkThread extends Thread{
        Handler handler = new Handler();
        public NetworkThread(Handler handler){
            this.handler = handler;
        }
        public void run(){
            final StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://learnbranch.urigit.com/");
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

            // 액티비티에 직접 값 넣기
            data = result.toString();
            // 핸들러로 메세지를 전달해서 함수 실행
            handler.sendEmptyMessage(NetworkBasic.SERVER_CODE);
        }
    }
}
