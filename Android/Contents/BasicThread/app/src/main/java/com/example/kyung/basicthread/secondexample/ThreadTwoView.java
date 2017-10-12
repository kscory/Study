package com.example.kyung.basicthread.secondexample;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.kyung.basicthread.R;

import java.util.Random;

/**
 * Created by Kyung on 2017-10-10.
 */

public class ThreadTwoView extends FrameLayout {
    FrameLayout stage;
    Button btnRain;
    RainDropView rainView;
    Context context;
    // 쓰레드는 Flag값을 static으로 선언하면 한번에 관리하기 편하다.
    public static boolean runFlag = true;
    public static boolean viewFlag = true;
    int width;
    int height;
    Random random = new Random();

    public ThreadTwoView(Context context) {
        super(context);
        this.context = context;

        initParameter();
        initView();
        setListener();

    }

    private void initParameter(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_thread_two,null);
        stage = (FrameLayout) view.findViewById(R.id.stage);
        btnRain = (Button) view.findViewById(R.id.btnRain);

        rainView = new RainDropView(context); // 생성자까지 호출
        stage.addView(rainView); // addView가 되면 그 때 onDraw 호출

        addView(view);
    }

    private void setListener(){
        btnRain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 화면을 지속적으로 다시 그려준다.
                if(viewFlag){
                    rainView.runStage();
                    viewFlag=false;
                }
                runFlag=true;
                new Thread(){
                    public void run(){
                        while(runFlag){
                            int x = random.nextInt(width);
                            int y = 50 * -1;
                            int speed = random.nextInt(2)+1;
                            int size = random.nextInt(50)+10;
                            RainDrop rainDrop = new RainDrop(x,y,speed,size, Color.CYAN,height);
                            rainView.addRainDrop(rainDrop);

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });
    }

    public static void stopRain(){
        runFlag=false;
        viewFlag=true;
    }
}
