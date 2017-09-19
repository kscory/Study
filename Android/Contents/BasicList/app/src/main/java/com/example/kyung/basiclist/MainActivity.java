package com.example.kyung.basiclist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 리스트 사용하기
 */

public class MainActivity extends AppCompatActivity {

    // 1. 데이터를 정의
    List<String> data = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // (1) (100개의 가상 값을 담는다.)
        for(int i=0 ; i<100 ; i++){
            data.add("item : "+i);
        }
        // 2. 데이터와 리스트뷰를 연결하는 아답터를 생성
        CustomAdapter adapter = new CustomAdapter(this, data);
        // 3. 아답터와 리스트뷰를 연결
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}


