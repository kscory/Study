package com.example.kyung.rxbasic01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Rx2Activity extends AppCompatActivity {

    RecyclerView recycler;
    CustomAdapter adapter;
    List<String> months = new ArrayList<>();

    String monthString[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx2);
        recycler = findViewById(R.id.recycler);
        adapter = new CustomAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // 1월부터 12월 가져오기
        DateFormatSymbols dfs = new DateFormatSymbols();
        monthString = dfs.getMonths();

        // 1. 발행자
        Observable<String> observable = Observable.create(e -> {
            try {
                for (String month : monthString) {
                    e.onNext(month); // subscribe를 함과 동시에 onNext를 호출하면서 구독자의 onNext를 호출, 여기서는 12번 호출
                    Thread.sleep(1000);
                }
                e.onComplete(); // 완료되었다고 호출
            } catch (Exception ex){
                throw ex; // 에러가 날 경우가 있을 경우 trycatch문으로 감싸고 에러를 호출
            }
        });

        // 2. 구독자
        observable
                .subscribeOn(Schedulers.io()) // 옵저버블의 thread를 지정
                .observeOn(AndroidSchedulers.mainThread()) // 옵저버의 thread를 지정 (안드로이드에만 있음)
                .subscribe(
                        str -> {
                            months.add(str);
                            adapter.setDataAndRefresh(months);
                        } // 반응형으로 구현하려면 onNext에서 data를 refresh 시켜준다.
                );
    }
}
