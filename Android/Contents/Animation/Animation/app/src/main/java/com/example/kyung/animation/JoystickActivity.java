package com.example.kyung.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

public class JoystickActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnDown;
    private Button btnLeft;
    private Button btnRight;
    private Button btnUp;
    private Button player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        initView();
        initListener();
    }

    private void initView(){
        btnDown = (Button) findViewById(R.id.btnDown);
        btnUp = (Button) findViewById(R.id.btnUp);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);
        player = (Button) findViewById(R.id.player);
    }
    public void initListener(){
        btnDown.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUp: up(); break;
            case R.id.btnDown: down(); break;
            case R.id.btnLeft: left(); break;
            case R.id.btnRight: right(); break;
        }
    }

    int playerX = 0;
    int playerY = 0;

    private void up(){
        playerY-=100;
        move();
    }
    private void down(){
        playerY+=100;
        move();
    }
    private  void left(){
        playerX-=100;
        move();
    }
    private void right(){
        playerX+=100;
        move();
    }

    private void move(){

        ObjectAnimator aniY = ObjectAnimator.ofFloat( player, "translationY", playerY );
        ObjectAnimator aniX = ObjectAnimator.ofFloat( player, "translationX", playerX );
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.playTogether(aniY, aniX);
        aniSet.start();
    }
}
