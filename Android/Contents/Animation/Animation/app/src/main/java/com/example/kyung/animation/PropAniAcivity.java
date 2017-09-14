package com.example.kyung.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ViewAnimator;

public class PropAniAcivity extends AppCompatActivity implements View.OnClickListener {

    Button btnObject, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prop_ani_acivity);

        btnObject = (Button) findViewById(R.id.btnObject);
        btnObject.setOnClickListener(this);

        btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(this);
    }

    // (참고)
    // onClick을 안달고 버튼 하는 방법
    // 함수 설정 후 acitivity에서 onclick을 등록함


    public void move(View view){
        // property Animation 적용

        // 방법 1
        // btnView.animate().x(10).withLayer().setDuration(30).rotation(200)

        // 방법 2
        ObjectAnimator aniY = ObjectAnimator.ofFloat(btnObject, "translationY", 300);
        ObjectAnimator aniX = ObjectAnimator.ofFloat(btnObject, "translationX", 300);
        ObjectAnimator anirot = ObjectAnimator.ofFloat(btnObject, "rotation", 0F,360F);
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.playTogether(aniY, aniX, anirot);
        aniSet.setDuration(3000);
        aniSet.setInterpolator(new LinearInterpolator());
        aniSet.start();

        // 방법 3
        Animator animation = AnimatorInflater.loadAnimator(this, R.animator.move);
        animation.setTarget(btnView);
        animation.start();
    }

    public void goJoyStick(View view){
        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnObject){
            goJoyStick(btnObject);
        }

    }
}
