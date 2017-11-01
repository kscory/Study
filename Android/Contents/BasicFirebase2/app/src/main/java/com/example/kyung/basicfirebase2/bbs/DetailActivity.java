package com.example.kyung.basicfirebase2.bbs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kyung.basicfirebase2.Const;

public class DetailActivity extends AppCompatActivity {

    String bbs_id, user_id, username, datetime, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
    }

    private void init(){
        Intent intent = getIntent();
        bbs_id = intent.getStringExtra(Const.bbs_id);
        user_id = intent.getStringExtra(Const.user_id);
        username = intent.getStringExtra(Const.user_name);
        datetime = intent.getStringExtra(Const.datetime);
        content = intent.getStringExtra(Const.content);
    }
}
