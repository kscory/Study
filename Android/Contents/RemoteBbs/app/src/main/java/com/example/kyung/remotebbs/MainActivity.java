package com.example.kyung.remotebbs;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.kyung.remotebbs.model.Result;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Intent postIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postIntent = new Intent(MainActivity.this, PostActivity.class);
        initView();
        load();
    }

    RecyclerAdapter adapter;
    LinearLayoutManager lmManager;
    private void setList(Result result) {
        adapter = new RecyclerAdapter(result.getData());
        recyclerView.setAdapter(adapter);
        lmManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lmManager);
    }

    private void addList(Result result) {
        adapter.addDataAndRefresh(result.getData());
    }

    private int page = 1;
    private void load() {
        new AsyncTask<Void, Void, Result>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Result doInBackground(Void... voids) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = Remote.getData("http://192.168.0.154:8090/bbs?type=all&page="+page);
                Log.d("LOAD","result=================="+result);
                Gson gson = new Gson();
                Result data = gson.fromJson(result, Result.class);
                return data;
            }

            @Override
            protected void onPostExecute(Result result) {
                progressBar.setVisibility(View.GONE);
                if(result.isSuccess()){
                    if(page == 1){
                        setList(result);
                    } else if(page > 1){
                        addList(result);
                    }
                    page++;
                }
            }
        }.execute();
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
                /* 이는 알아두어야 함
                int visibleCount = ImManager.getChildCount(); // 화면에 보이는 아이템 개수
                int firstPosition = ImManager.findFirstVisibleItemPosition(); // 화면에 최상단에 보이는 아이템
                boolean bottom = false; // 바닥인지 체크
                */
                int totalCount = lmManager.getItemCount();
                int lastPosition = lmManager.findLastVisibleItemPosition();

                // 현재 바닥에 도달했으면
                if(lastPosition == totalCount-1) load();
            }
        });
    }

    public static final int POST = 999;
    public void openPost(View view){
        startActivityForResult(postIntent,POST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case POST:
                if(resultCode == RESULT_OK){
                    page=1;
                    load();
                } else{

                }
                break;
        }
    }
}
