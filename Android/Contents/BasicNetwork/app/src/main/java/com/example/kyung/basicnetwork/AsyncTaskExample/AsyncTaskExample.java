package com.example.kyung.basicnetwork.AsyncTaskExample;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kyung.basicnetwork.R;

/**
 *  AsyncTask = 세개의 기본함수를 지원하는 Thread
 *  1. onPreExecute : doInBackground() 함수가 실행되기 전에 실행되는 함수
 *
 *  2. doInBackground : 백그라운드(sub thread)에서 코드를 실행하는 함수 < 얘만 sub thread
 *
 *          ↓ onPostExecute는 doInBackground로 부터 데이터를 받을 수 있다.
 *
 *  3. onPostExecute : doInBackground() 함수가 실행된 후에 실행되는 함수
 */

// AsyncTask는 곧바로 처음과 끝을 곧바로 사용하기 위해서 이용
public class AsyncTaskExample extends FrameLayout {

    TextView textView;
    FrameLayout stage;
    Context context;

    public AsyncTaskExample(Context context) {
        super(context);
        this.context = context;

        initView();
        getServer("http://google.com");
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.asynctask,null);
        stage = (FrameLayout) view.findViewById(R.id.stage);
        textView = (TextView) view.findViewById(R.id.textView);
        addView(view);
    }

    private void getServer(String url){

        new AsyncTask<String, Void, String>(){
            // 1(첫번째 Generic) : doInBackground 함수의 파라미터로 사용
            // 2(두번째 Generic) : onProgressUpdate 함수의 파라미터로 사용
            //                      => 주로 진행상태의 percent 값(int)으로 사용된다.
            // 3(세번째 Generic) : doInBackground 의 리턴값이면서 onPostExecute의 파라미터

            @Override
            protected String doInBackground(String... params) {
                // 사용할 때
                String param1 = params[0];
                String result = Remote.getData(param1);
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                result = result.substring(result.indexOf("<title>")+"<title>".length()
                        ,result.indexOf("</title>"));
                textView.setText(result);
                super.onPostExecute(result);
            }

        }.execute(url);
    }
}
