package com.example.kyung.androidmemofile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kyung.androidmemofile.domain.Memo;
import com.example.kyung.androidmemofile.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;

public class WriteActivity extends AppCompatActivity {

    Button btnPost;
    EditText editTitle;
    EditText editAuthor;
    EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();
        initListener();
    }

    public void write(Memo memo){
        try {
            String filename = System.currentTimeMillis()+".txt";
            FileUtil.write(this,filename,memo.toString());

            // 작성 완료시 OK를 보냄
            setResult(RESULT_OK);

            Toast.makeText(this,"등록완료",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this,"등록실패",Toast.LENGTH_LONG).show();
        }

        // 작성 완료시 종료
        finish();
    }

    // 메모를 생성하여 내용을 저장
    private Memo getMemoFromString(){
        Memo memo = new Memo();
        memo.setNo(1);
        memo.setTitle(editTitle.getText().toString());
        memo.setAuthor(editAuthor.getText().toString());
        memo.setDatetime(System.currentTimeMillis());
        memo.setContent(editContent.getText().toString());

        return memo;
    }

    // 메모를 만드는 메서드를 호출하고 file에 써주는 작업 실행
    public void initListener(){
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Memo memo = getMemoFromString();
                write(memo);
            }
        });
    }

    private void initView(){
        btnPost = (Button) findViewById(R.id.btnPost);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editContent = (EditText) findViewById(R.id.editContent);
    }
}
