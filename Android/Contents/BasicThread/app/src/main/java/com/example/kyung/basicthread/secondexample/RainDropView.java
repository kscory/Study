package com.example.kyung.basicthread.secondexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.MailTo;
import android.view.View;

import com.example.kyung.basicthread.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Kyung on 2017-10-11.
 */

public class RainDropView extends View{

    Paint paint;
    List<RainDrop> rainDrops = new CopyOnWriteArrayList<>();

    public RainDropView(Context context) {
        super(context);

        //색 지정
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(rainDrops.size() >= 0){
            for(int i=0 ; i<rainDrops.size() ; i++){
                RainDrop rainDrop = rainDrops.get(i);
                paint.setColor(rainDrop.color);
                canvas.drawCircle(rainDrop.x
                        , rainDrop.y
                        , rainDrop.size
                        , paint);
            }
        }
    }

    public void addRainDrop(RainDrop rainDrop){
        rainDrops.add(rainDrop);
        rainDrop.start();
    }

    public void runStage(){
        new Thread(){
            public void run(){
                while(ThreadTwoView.runFlag){
                    // 반복문을 돌면서 전체 오브젝트의 좌표값을 갱신
                    for(int i=0 ; i<rainDrops.size() ; i++){
                        RainDrop rainDrop = rainDrops.get(i);
                        if(rainDrop.y < rainDrop.limit){
                            rainDrop.y += rainDrop.speed;
                        } else{
                            rainDrops.remove(rainDrop);
                            i--;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
