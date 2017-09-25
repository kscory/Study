package com.example.kyung.recyclerviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. 데이터를 정의
        for(int i=0 ; i<100 ; i++){
            data.add("item : "+i);
        }

        // 2. 아답터를 재정의 (CustomAdapter.java)

        // 3. 재정의한 아답터를 생성하면서 데이터저장
        CustomAdapter adapter = new CustomAdapter(this,data);
        // 4. 아답터와 RecyclerView 컨테이너를 연결
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        // 5. RecyclerView에 레이아웃매니저를 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 레이아웃 메니저 종류 (3가지) 외울필요는 없다.
        /*
        1. LinearLayoutManager
           - 리사이클러 뷰에서 가장 많이 쓰이는 레이아웃으로 수평, 수직 스크롤을 제공하는 리스트를 만들 수 있다.
        2. StaggeredGridLayoutManager
           - 이 레이아웃을 통해 뷰마다 크기가 다른 레이아웃을 만들 수 있다. 마치 Pinterest 같은 레이아웃 구성가능.
        3. GridLayoutManager
           - 갤러리(GridView) 같은 격자형 리스트를 만들 수 있습니다.
        - 사용예시// StaggeredGrid 레이아웃을 사용한다
            RecyclerView.LayoutManager lm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            lm = new LinearLayoutManager(this);
            lm = new GridLayoutManager(this,3);
        */



    }
}
