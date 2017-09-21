package com.example.kyung.androidmemofile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.kyung.androidmemofile.domain.Memo;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    TextView textNo, textTitle, textAuthor, textDate, textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        init();
    }

    public void init(){
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",-1);

        Memo memo = ListAdapter.data.get(position);

        textNo.setText(memo.getNo()+"");
        textTitle.setText(memo.getTitle());
        textAuthor.setText(memo.getAuthor());
        textDate.setText(memo.getDatetime()+"");
        textContent.setText(memo.getContent());
    }

    private void initView(){
        textNo = (TextView) findViewById(R.id.textNo);
        textTitle = (TextView) findViewById(R.id.textTitle);
        textAuthor = (TextView) findViewById(R.id.textAuthor);
        textDate = (TextView) findViewById(R.id.textDate);
        textContent = (TextView) findViewById(R.id.textContent);
    }
}
