package com.example.kyung.spreadcubes;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3, btn4, btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView(){
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btnChange = (Button) findViewById(R.id.btnChange);
    }
    private void initListener(){
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnChange){
            if(btnChange.getText().toString().equals("Spread")){
                spread();
            } else{
                converge();
            }
        }
    }

    int btn1X = 0; int btn1Y = 0;
    int btn2X = 0; int btn2Y = 0;
    int btn3X = 0; int btn3Y = 0;
    int btn4X = 0; int btn4Y = 0;

    public void spread(){
        btn1X+=btn1.getWidth()/2 ; btn1Y+=btn1.getHeight()/2;
        btn2X+=btn2.getWidth()/2 ; btn2Y-=btn2.getHeight()/2;
        btn3X-=btn3.getWidth()/2 ; btn3Y+=btn3.getHeight()/2;
        btn4X-=btn4.getWidth()/2 ; btn4Y-=btn4.getHeight()/2;

        animationEffect(btn1, btn1X, btn1Y);
        animationEffect(btn2,btn2X,btn2Y);
        animationEffect(btn3,btn3X,btn3Y);
        animationEffect(btn4,btn4X,btn4Y);

        btnChange.setText("Converge");

    }

    public void converge(){
        btn1X-=btn1.getWidth()/2 ; btn1Y-=btn1.getHeight()/2;
        btn2X-=btn2.getWidth()/2 ; btn2Y+=btn2.getHeight()/2;
        btn3X+=btn3.getWidth()/2 ; btn3Y-=btn3.getHeight()/2;
        btn4X+=btn4.getWidth()/2 ; btn4Y+=btn4.getHeight()/2;

        animationEffect(btn1, btn1X, btn1Y);
        animationEffect(btn2,btn2X,btn2Y);
        animationEffect(btn3,btn3X,btn3Y);
        animationEffect(btn4,btn4X,btn4Y);

        btnChange.setText("Spread");
    }

    public void animationEffect(View v, int x, int y){
        ObjectAnimator aniBtnX = ObjectAnimator.ofFloat(v, "translationY", x);
        ObjectAnimator aniBtnY = ObjectAnimator.ofFloat(v, "translationX", y);
        aniBtnX.setDuration(1500);
        aniBtnY.setDuration(1500);

        ObjectAnimator aniRotateBtn = ObjectAnimator.ofFloat(v,"rotation",0F,1080F);
        aniRotateBtn.setDuration(2000);
        aniRotateBtn.setInterpolator(new AccelerateInterpolator());

        AnimatorSet aniSet = new AnimatorSet();
        aniSet.playTogether(aniBtnX, aniBtnY, aniRotateBtn);
        aniSet.start();
    }
}
