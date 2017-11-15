package com.example.kyung.rxbasic03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {

    List<String> data = new ArrayList<>();

    RecyclerView recycler;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        adapter = new CustomAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * publish 서브젝트
     * 구독 시점부터 데이터를 읽을 수 있다. > 이전에 발행된 아이템은 읽을 수 없다.
     */
    PublishSubject<String> publish = PublishSubject.create();
    public void doPublish(View view){
        new Thread(
            () -> {
                for(int i=0 ; i<10 ; i++) {
                    publish.onNext("(Publish)SOMETHING... " + i);
                    Log.e("Publish","=========================="+i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        ).start();
    }
    // publish 에서 값 가져와서 세팅
    public void getPublish(View view){
        data.clear();
        publish
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                str -> {
                    data.add(str);
                    adapter.setDataAndRefresh(data);
                }
        );
    }

    /**
     * Behavior 서브젝트
     * 구독 이전에 발행된 마지막 발행된 아이템부터 구독할 수 있다.
     */
    BehaviorSubject<String> behavior = BehaviorSubject.create();
    public void doBehavior(View view){
        new Thread(
                () -> {
                    for(int i=0 ; i<10 ; i++) {
                        behavior.onNext("(Behavior)SOMETHING... " + i);
                        Log.e("Behavior","=========================="+i);
                        try { Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
                    }
                }
        ).start();
    }
    public void getBehavior(View view){
        data.clear();
        behavior
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        str -> {
                            data.add(str);
                            adapter.setDataAndRefresh(data);
                        }
                );
    }

    /**
     * Replay 서브젝트
     * 발행된 아이템을 모두 구독할 수 있다.
     */
    ReplaySubject<String> replay = ReplaySubject.create();
    public void doReplay(View view){
        new Thread(
                () -> {
                    for(int i=0 ; i<10 ; i++) {
                        replay.onNext("(Replay)SOMETHING... " + i);
                        Log.e("Replay","=========================="+i);
                        try { Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
                    }
                }
        ).start();
    }
    public void getReplay(View view){
        data.clear();
        replay
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        str -> {
                            data.add(str);
                            adapter.setDataAndRefresh(data);
                        }
                );
    }

    /**
     * Async 서브젝트
     * 발행이 완료된 시점에 마지막 데이터만 보여준다.
     */
    AsyncSubject<String> async = AsyncSubject.create();
    public void doAysnc(View view){
        new Thread(
                () -> {
                    for(int i=0 ; i<10 ; i++) {
                        async.onNext("(Aysnc)SOMETHING... " + i);
                        Log.e("Aysnc","=========================="+i);
                        try { Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
                    }
                    async.onComplete(); // Aysnc는 onComplete을 선언해야 구독이 가능
                }
        ).start();
    }
    public void getAysnc(View view){
        data.clear();
        async
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        str -> {
                            data.add(str);
                            adapter.setDataAndRefresh(data);
                        }
                );
    }
}
