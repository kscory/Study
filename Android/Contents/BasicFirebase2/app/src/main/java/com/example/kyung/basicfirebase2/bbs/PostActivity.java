package com.example.kyung.basicfirebase2.bbs;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kyung.basicfirebase2.Const;
import com.example.kyung.basicfirebase2.R;
import com.example.kyung.basicfirebase2.model.Bbs;
import com.example.kyung.basicfirebase2.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference bbsRef;

    String user_id;
    String user_password;
    String user_name;

    Toolbar toolbarPost;
    TextView textPost;
    ImageButton btnfinish;
    EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent intent = getIntent();
        user_id = intent.getStringExtra(Const.user_id);
        user_password = intent.getStringExtra(Const.user_password);
        user_name = intent.getStringExtra(Const.user_name);
        Log.e("namePost==", "======" + user_name);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        bbsRef = database.getReference("bbs");

        initView();
        initListener();
        setSupportActionBar(toolbarPost);

    }

    private void initView() {
        toolbarPost = findViewById(R.id.toolbarPost);
        textPost = toolbarPost.findViewById(R.id.textPost);
        btnfinish = toolbarPost.findViewById(R.id.btnfinish);
        editContent = findViewById(R.id.editContent);
    }

    public void initListener() {
        textPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String datetime = sdf.format(new Date(System.currentTimeMillis()));
                String bbs_id = bbsRef.push().getKey();
                String content = editContent.getText().toString();
                Bbs bbs = new Bbs(content, datetime, user_id, user_name);

                bbsRef.child(bbs_id).setValue(bbs);
                userRef.child(user_id).child("bbs").child(bbs_id).setValue(bbs);

                setResult(RESULT_OK);
                finish();
            }
        });

        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}


    // 홈버튼 자동 추가
//    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
