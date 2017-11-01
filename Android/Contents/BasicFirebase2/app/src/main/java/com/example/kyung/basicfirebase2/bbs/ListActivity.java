package com.example.kyung.basicfirebase2.bbs;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.kyung.basicfirebase2.Const;
import com.example.kyung.basicfirebase2.R;
import com.example.kyung.basicfirebase2.bbs.bbsview.BbsAdapter;
import com.example.kyung.basicfirebase2.bbs.bbsview.BbsAll;
import com.example.kyung.basicfirebase2.bbs.bbsview.BbsMine;
import com.example.kyung.basicfirebase2.model.Bbs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements BbsAdapter.IMoveDetail {

    FirebaseDatabase database;
    DatabaseReference bbsRef;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button btnPost;
    private ConstraintLayout stage;
    List<View> viewList = new ArrayList<>();

    BbsAll bbsAll;
    BbsMine bbsMine;

    String mId;
    String mPassword;
    String mName;

    List<Bbs> bbsAllList = new ArrayList<>();
    List<Bbs> bbsMineList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();

        initView();
        initTabLayout();
        initViewPager();
        initTabwithViewPager();
        initButton();
    }

    private void init(){
        viewList.clear();

        Intent intent = getIntent();
        mId = intent.getStringExtra(Const.user_id);
        mPassword = intent.getStringExtra(Const.user_password);
        mName = intent.getStringExtra(Const.user_name);

        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");
    }

    private void initView(){
        stage = findViewById(R.id.stage);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btnPost = findViewById(R.id.btnPost);
        bbsAll = new BbsAll(this);
        bbsMine = new BbsMine(this);

        bbsAll.setDataAll(bbsAllList);
        bbsMine.setDataMine(bbsMineList);
    }
    // 탭레이아웃 및 뷰페이저 연결
    private void initTabLayout(){
        tabLayout.addTab(tabLayout.newTab().setText("내가 쓴 글"));
        tabLayout.addTab(tabLayout.newTab().setText("전체 글"));
    }
    private void initViewPager(){
        viewList.add(bbsMine);
        viewList.add(bbsAll);
        BbsPagerAdapter adapter = new BbsPagerAdapter(this,viewList);
        viewPager.setAdapter(adapter);
    }
    private void initTabwithViewPager(){
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    // 버튼 클릭시 게시판 글 작성 엑티비티로 이동
    private void initButton(){
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, PostActivity.class);
                intent.putExtra(Const.user_id, mId);
                intent.putExtra(Const.user_password, mPassword);
                intent.putExtra(Const.user_name, mName);
                startActivityForResult(intent, Const.bbspostcode);
            }
        });
    }
    // 게시판 글 작성 후 리스트(뷰) 업데이트
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Const.bbspostcode:
                if(resultCode == RESULT_OK){
                    // 알아서 업데이트 됨
                }
                break;
        }
    }
    // 리스트 데이터를 업데이트하는 리스너
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            bbsAllList.clear();
            bbsMineList.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                Bbs bbs = snapshot.getValue(Bbs.class);
                bbs.bbs_id = snapshot.getKey();
                bbsAllList.add(bbs);
                if(bbs.user_id.equals(mId)){
                    bbsMineList.add(bbs);
                }
            }
            bbsAll.setDataAll(bbsAllList);
            bbsMine.setDataMine(bbsMineList);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) { }
    };

    @Override
    protected void onResume() {
        super.onResume();
        bbsRef.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bbsRef.removeEventListener(valueEventListener);
    }

    @Override
    public void goDetail(final String bbs_id) {

        for(Bbs bbs : bbsAllList){
            if(bbs.bbs_id.equals(bbs_id)){
                Intent intent = new Intent(ListActivity.this,DetailActivity.class);
                intent.putExtra(Const.Bbs,bbs);
                startActivity(intent);
            }
        }
    }
}
