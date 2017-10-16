package com.example.kyung.basicnetwork.NetworkExample;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kyung.basicnetwork.NetworkAdapter;
import com.example.kyung.basicnetwork.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kyung on 2017-10-16.
 */

public class NetworkBasicTwo extends FrameLayout {

    FrameLayout stage;
    TextView textView;
    Context context;

    public static final int SERVER_CODE_TWO = 999;

    public NetworkBasicTwo(Context context) {
        super(context);
        this.context = context;

        initView();
        NetworkThreadTwo networkThread = new NetworkThreadTwo(handler);
        networkThread.start();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.network,null);
        stage = (FrameLayout) view.findViewById(R.id.stage);
        textView = (TextView) view.findViewById(R.id.textView);
        addView(view);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SERVER_CODE_TWO:
                    setData(msg.obj.toString());
                    break;
            }
        }
    };

    private void setData(String data){
        textView.setText(data);
    }
}


class NetworkThreadTwo extends Thread{
    Handler handler;
    public NetworkThreadTwo(Handler handler){
        this.handler = handler;
    }
    public void run(){
        final StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("http://data.seoul.go.kr/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 통신 성공여부 체크
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 파일을 꺼낸다.
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    result.append(temp).append("\n");
                }
                br.close();
                isr.close();
            } else {
                Log.e("ServerError", connection.getResponseCode() + "");
            }
            connection.disconnect();
        } catch (Exception e){
            Log.e("Error",e.toString());
        }

        Message msg = new Message();
        msg.what = NetworkBasicTwo.SERVER_CODE_TWO;
        msg.obj = result;
        handler.sendMessage(msg);
    }
}
