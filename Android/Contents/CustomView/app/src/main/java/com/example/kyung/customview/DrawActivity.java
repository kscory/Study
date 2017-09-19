package com.example.kyung.customview;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

public class DrawActivity extends AppCompatActivity {

    FrameLayout stage;
    DrawView draw;
    RadioGroup radioColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        // 라디오 버튼이 선택되면 draw의 paint 색상을 바꿔준다.
        radioColor = (RadioGroup) findViewById(R.id.radioColor);

        stage = (FrameLayout) findViewById(R.id.stage);
        draw = new DrawView(this);

        stage.addView(draw);

        radioColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.radioBlack:
                        draw.setColor(Color.BLACK);
                        break;
                    case R.id.radioCyan:
                        draw.setColor(Color.CYAN);
                        break;
                    case R.id.radioMagenta:
                        draw.setColor(Color.MAGENTA);
                        break;
                    case R.id.radioYellow:
                        draw.setColor(Color.YELLOW);
                        break;
                }
            }
        });

    }
}
