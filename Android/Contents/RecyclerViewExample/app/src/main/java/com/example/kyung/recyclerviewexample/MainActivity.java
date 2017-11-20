package com.example.kyung.recyclerviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    List<String> data = new ArrayList<>();
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. 데이터를 정의
        for(i=0 ; i<10 ; i++){
            data.add("item : "+i);
        }
        i--;

        // 2. 아답터를 재정의 (CustomAdapter.java)

        // 3. 재정의한 아답터를 생성하면서 데이터저장
        adapter = new CustomAdapter(this);
        // 4. 아답터와 RecyclerView 컨테이너를 연결
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.addItemDecoration(new CustomDivider(this, 40));
        recyclerView.setAdapter(adapter);
        adapter.setDataAndRefresh(data);
        // 5. RecyclerView에 레이아웃매니저를 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new CustomItemAnimator());
    }

    public void addItem(View v){
        i++;
        data.add("item : "+i);
        adapter.addDate(data);
    }
    public void deleteItem(View v){
        data.remove(i);
        i--;
        adapter.removeDate(data);
    }

}
