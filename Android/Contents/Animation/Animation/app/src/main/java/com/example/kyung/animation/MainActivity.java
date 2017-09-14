package com.example.kyung.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMove, btnRotate, btnScale, btnAlpha, object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView(){
        btnMove = (Button) findViewById(R.id.btnMove);
        btnRotate = (Button) findViewById(R.id.btnRotate);
        btnScale = (Button) findViewById(R.id.btnScale);
        btnAlpha = (Button) findViewById(R.id.btnAlpha);
        object = (Button) findViewById(R.id.object);
    }
    private void initListener(){
        btnMove.setOnClickListener(this);
        btnRotate.setOnClickListener(this);
        btnScale.setOnClickListener(this);
        btnAlpha.setOnClickListener(this);
        object.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMove: move(); break;
            case R.id.btnRotate: rotate(); break;
            case R.id.btnScale: scale(); break;
            case R.id.btnAlpha: alpha(); break;
            case R.id.object:
                Intent intent = new Intent(this, PropAniAcivity.class);
                startActivity(intent);
        }

    }

    // View 애니메이션 정의 1
    private void move(){
        //Animation 설정
        Animation aniView = new TranslateAnimation(0,100,0,300);
        // 조건 설정
        aniView.setDuration(1000);
        aniView.setFillAfter(true);
        aniView.setInterpolator(new AccelerateInterpolator());
        // 실제 위젯에 적용
        object.startAnimation(aniView);
    }


    // 버튼에서 호출되는 함수정의 2
    // 1. 애니메이션 xml 정의
    // 2. AnimationUtil로 정의된 애니메이션을 로드
    // 3. 로드된 애니메이션을 실제 위젯에 적용
    private void rotate(){
        // 2. AnimationUtil로 정의된 애니메이션을 로드
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        // 3. 로드된 애니메이션을 실제 위젯에 적용
        object.startAnimation(animation);
    }
    private void scale(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        object.startAnimation(animation);
    }
    private void alpha(){
        // 2. AnimationUtil로 정의된 애니메이션을 로드
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        // 3. 로드된 애니메이션을 실제 위젯에 적용
        object.startAnimation(animation);

    }
}
