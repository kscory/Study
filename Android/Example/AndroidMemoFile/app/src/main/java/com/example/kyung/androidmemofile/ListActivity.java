package com.example.kyung.androidmemofile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kyung.androidmemofile.domain.Memo;
import com.example.kyung.androidmemofile.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        initListener();
        init();
    }

    private void init(){
        ArrayList<Memo> list = loadData();
        ListAdapter adapter = new ListAdapter(this, list);
        listView.setAdapter(adapter);
    }

    private ArrayList<Memo> loadData(){
        ArrayList<Memo> memoList = new ArrayList<>();
        for(File item : getFilesDir().listFiles()){
            try {
                String text =  FileUtil.read(this,item.getName());
                Memo memo = new Memo();
                memo.parse(text);
                memoList.add(memo);
            } catch (IOException e) {
                Toast.makeText(this, "에러:"+e.toString(), Toast.LENGTH_SHORT).show();
            }

        }
        return memoList;
    }


    // 엑티비티가 종료될때 조작할 수 있도록 설정
    private static final int WRITE_ACTIVITY = 12345;
    private void initListener(){
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,WriteActivity.class);
                startActivityForResult(intent,WRITE_ACTIVITY);
            }
        });
    }
    // 호출한 엑티비티가 종료될때 실행 (조건을 걸 수 있다.)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case WRITE_ACTIVITY:
                if(resultCode==RESULT_OK){
                    init();
                }
                break;
        }
    }

    private void initView(){
        listView = (ListView) findViewById(R.id.listView);
        btnPost = (Button) findViewById(R.id.btnPost);
    }
}
