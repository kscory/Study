package com.example.kyung.basicnetwork.NetworkExample;

import android.app.Activity;
import android.content.Context;
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

public class NetworkBasicOne extends FrameLayout {
    FrameLayout stage;
    TextView textView;
    Context context;
    Activity activity;

    public NetworkBasicOne(Context context) {
        super(context);
        this.context = context;
        if(context instanceof Activity){
            activity = (Activity) context;
        }
        initView();

        new NetworkThreadOne().start();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.network,null);
        stage = (FrameLayout) view.findViewById(R.id.stage);
        textView = (TextView) view.findViewById(R.id.textView);
        addView(view);
    }

    /*
        - HttpURLConnection 사용하기
        1. URL 객체를 선언 (웹주소를 가지고 생성)
        2. URL 객체에서 서버 연결을 해준다 > HttpURLConnection 을 생성 = Stream
        3. 커넥션의 방식을 설정 (기본값 = GET)
        4. 연결되어 있는 Stream 을 통해서 데이터를 가져온다.
        5. 연결(Stream)을 닫는다.
     */
    class NetworkThreadOne extends Thread{
        public void run(){
            final StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://fastcampus.co.kr");
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

            // RunOnUiThread를 이용하여 데이터를 세팅
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(result.toString());
                }
            });
        }
    }
}
