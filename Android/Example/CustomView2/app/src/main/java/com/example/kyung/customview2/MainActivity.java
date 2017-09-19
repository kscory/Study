package com.example.kyung.customview2;

import android.graphics.Color;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    FrameLayout stage;
    Button btnClear;
    RadioGroup radioColor;
    DrawView draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        draw = new DrawView(this);
        stage.addView(draw);

        radioColor.setOnCheckedChangeListener(radioListener);
        btnClear.setOnClickListener(onClickListener);
    }

    private void initView(){
        stage = (FrameLayout) findViewById(R.id.stage);
        btnClear = (Button) findViewById(R.id.btnClear);
        radioColor = (RadioGroup) findViewById(R.id.radioColor);
    }

    RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch(checkedId){
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
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnClear:
                    draw.memoClear();
                    break;
            }
        }
    };
}
