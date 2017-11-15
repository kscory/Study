package com.example.kyung.rxbasic02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    CustomAdapter adapter;
    List<String> months = new ArrayList<>();

    String monthString[];

    Observable<String> observable;
    Observable<String> observableZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recycler);
        adapter = new CustomAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // 1월부터 12월 가져오기
        DateFormatSymbols dfs = new DateFormatSymbols();
        monthString = dfs.getMonths();

        // 1. 발행자
        observable = Observable.create( e -> {
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

//        // 예시 1(just만 사용)
//        observableZip = Observable.zip(
//                Observable.just("BeWhy", "Curry"),
//                Observable.just("Singer", "Basketball Player"),
//                (item1, item2) -> "job= ".concat(item2).concat(", name= ").concat(item1)
//        );
        // 예시 2(Crate 사용)
        Observable<String> obs1 = Observable.create(e -> {
            try {
                Thread.sleep(1000);
                e.onNext("Singer");
                Thread.sleep(1000);
                e.onNext("Athlete");
                Thread.sleep(5000);
                e.onNext("Rapper");
                e.onComplete();
            }catch(Exception ex){
                throw ex;
            }
        });
        observableZip = Observable.zip(
                Observable.just("BeWhy", "Curry", "Zico"),
                obs1,
                (item1, item2) -> "job= ".concat(item2).concat(", name= ").concat(item1)
        );
    }

    /**
     * Map은 데이터 자체를 변형 할 수 있다.
     * @param view
     */
    public void doMap(View view){
        // 2. 구독자
        months.clear();
        observable
                .subscribeOn(Schedulers.io()) // 옵저버블의 thread를 지정
                .observeOn(AndroidSchedulers.mainThread()) // 옵저버의 thread를 지정 (안드로이드에만 있음)
                .filter( (str) -> str.equals("3월") ? false : true ) // 걸러주는 역할(데이터에는 손대지 못한다.)
                .map((str) -> {
                        if(str.equals("4월")) return "[" + str + "]";
                        else return str;
                })
                .subscribe(
                        str -> {
                            months.add(str);
                            adapter.setDataAndRefresh(months);
                        } // 반응형으로 구현하려면 onNext에서 data를 refresh 시켜준다.
                );
    }

    /**
     * 하나의 데이터를 두개로 쪼개기 위해 사용
     * flatMap은 아이템을 여러개로 분리할 수 있다.
     * @param view
     */
    public void doFlatMap(View view){
        months.clear();
        observable
                .subscribeOn(Schedulers.io()) // 옵저버블의 thread를 지정
                .observeOn(AndroidSchedulers.mainThread()) // 옵저버의 thread를 지정
                .filter( (str) -> str.equals("3월") ? false : true )
                .flatMap(item -> {
                    return Observable.fromArray(new String[] {"name:"+item, "["+item+"]"});
                })
                .subscribe(
                        str -> {
                            months.add(str);
                            adapter.setDataAndRefresh(months);
                        } // 반응형으로 구현하려면 onNext에서 data를 refresh 시켜준다.
                );
    }

    /**
     * 복수개의 observable을 하나로 묶어준다.
     * @param view
     */
    public void doZip(View view){
        months.clear();
        observableZip
                .subscribeOn(Schedulers.io()) // 옵저버블의 thread를 지정
                .observeOn(AndroidSchedulers.mainThread()) // 옵저버의 thread를 지정
                .subscribe(
                        str -> {
                            months.add(str);
                            adapter.setDataAndRefresh(months);
                        } // 반응형으로 구현하려면 onNext에서 data를 refresh 시켜준다.
                );
    }
}