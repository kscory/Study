package com.example.kyung.rxbasic01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/* Subject를 활용 //
public class MainActivity extends AppCompatActivity {

    Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 발행자 생성 1. Observable, Subject
        subject = new Subject();
        subject.start();
    }

    // 구독자에 id를 부여하기 위한 코드
    int clientIdx = 0;
    // 위에 생성한 발행자에 클라이언트를 클릭할 때마다 등록한다.
    public void addObserver(View view){

        Subject.Observer client = new Client(clientIdx++);
        subject.observers.add(client);

    }
    // 구독자
    class Client implements Subject.Observer{

        String name = "";
        public Client(int idx){
            name = "Client" + idx;
        }
        @Override
        public void notification(String msg) {
            Log.d(name, msg);
        }
    }


}

// Subject : 발행자 클래스
class Subject extends Thread {
    // 옵저버 목록
    List<Observer> observers = new ArrayList<>();

    // 실행코드
    public void run(){
        int count = 0;
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(Observer obs : observers){
                obs.notification("Hello!!! = " + count++);
            }
        }
    }
    // 옵저버 인터페이스
    public interface Observer{
        public void notification(String msg);
    }
}
*/

// Observable 활용
public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    CustomAdapter adapter;

    // 데이터 저장 변수
    List<String> months = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        adapter = new CustomAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // 데이터 - 인터넷에서 순차적으로 가져오는 것
        final String data[] = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        // 1. 발행자 생성 operator from
        Observable<String> observableFrom = Observable.fromArray(data);
        // 1.1. 구독자
        /* 기존 표현 방법 //
        observableFrom.subscribe(new Consumer<String>() {   // onNext 데이터가 있으면 호출된다.
            @Override
            public void accept(String s) throws Exception {
                months.add(s);
            }
        }, new Consumer<Throwable>() { // onError 가 호출된다
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() { // onComplete 이 호출된다.
            @Override
            public void run() throws Exception {
                adapter.setDataAndRefresh(months);
            }
        });
        */

        // 람다식으로 표현 //
        // 코드가 한줄일 때는 {}을 안써주어도 된다.
        // () 호출되는 콜백함수의 파라미터가 없을때 사용
        observableFrom.subscribe(
                str -> months.add(str) ,
                throwable -> {},
                () -> adapter.setDataAndRefresh(months)
        );

        // 2. just
        Observable<String> observableJust = Observable.just("JAN","FEB","MAR");
        observableJust.subscribe(str -> months.add(str));

        // 3. defer
        Observable<String> observableDefer = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just("JAN","FEB","MAR");
            }
        });
        observableDefer.subscribe(
                str -> months.add(str),
                throwable -> {},
                () -> {adapter.setDataAndRefresh(months);}
        );
    }

    public void goNext(View view){
        Intent intent = new Intent(this, Rx2Activity.class);
        startActivity(intent);
    }
}
