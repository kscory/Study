package com.example.kyung.basicfirebase2.bbs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kyung.basicfirebase2.Const;
import com.example.kyung.basicfirebase2.R;
import com.example.kyung.basicfirebase2.model.Bbs;

public class DetailActivity extends AppCompatActivity {

    String bbs_id, user_id, username, datetime, content;
    ImageButton btnfinish;
    TextView textName, textDate, textBbs, textUser, textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        init();
        setTextValue();
    }

    private void init(){
        Intent intent = getIntent();
        Bbs bbs = (Bbs) intent.getSerializableExtra(Const.Bbs);

        bbs_id = bbs.bbs_id;
        user_id = bbs.user_id;
        username = bbs.username;
        datetime = bbs.date;
        content = bbs.content;
    }

    private void initView(){
        btnfinish = findViewById(R.id.btnfinish);
        textName = findViewById(R.id.textName);
        textDate = findViewById(R.id.textDate);
        textBbs = findViewById(R.id.textBbs);
        textUser = findViewById(R.id.textUser);
        textContent = findViewById(R.id.textContent);

        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setTextValue(){
        textName.setText(username);
        textBbs.setText(bbs_id);
        textDate.setText(datetime);
        textUser.setText(user_id);
        textContent.setText(content);
    }
}
