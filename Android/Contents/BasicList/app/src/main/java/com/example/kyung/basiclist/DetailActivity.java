package com.example.kyung.basiclist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // 인텐트를 통해 넘어온 값 꺼내기
        Intent intent = getIntent(); // startActivity를 통해 넘어온 intent를 꺼낸다.

        // # 1. 번들을 거쳐 값을 꺼내기
//        // 인텐트에서 값의 묶음인 번들을 꺼내고
//        Bundle bundle = intent.getExtras();
//        // 번들에서 최종 값을 꺼낸다.
//        String result = bundle.getString("valueKey");

        // # 2. 인텐트에서 바로 값을 꺼내기
        String result = intent.getStringExtra("valueKey");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(result);


    }
}
