package com.example.kyung.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kyung on 2017-09-18.
 */

public class DrawView extends View {

    Paint paint;
    Path currentPath;

    // // 이전
    ArrayList<Paint> paintGroup= new ArrayList<>();
    ArrayList<Path> pathGroup= new ArrayList<>();


//    // 그림이 그려지는 좌표
//    // 원의 크기(반지름)
    float r =10;
//    // 좌표값을 저장하는 저장소
//    ArrayList<Float> xs = new ArrayList<>();
//    ArrayList<Float> ys = new ArrayList<>();

    // 소스코드에서 사용하기 때문에 생성자 파라미터는 context만 필요
    public DrawView(Context context) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(r/2);

        currentPath = new Path();
    }

    public void setColor(int color){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(r/2);
        paint.setColor(color);

        currentPath = new Path();
    }

    // 화면을 그려주는 onDraw 오버라이드
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        // 1.화면에 터치가 되면...
//        // 2.연속해서 그림을 그려준다.
//        // 2.1 터치된 좌표에 작은 동그라미를 그려준다.
//        if(xs.size() > 0){
//            for(int i=0 ; i<xs.size() ; i++){
//                canvas.drawCircle(xs.get(i),ys.get(i),r,paint);
//            }
//        }

        // //이전
        paintGroup.add(paint);
        pathGroup.add(currentPath);

        if(paintGroup.size()>0){
            for(int i=0 ; i <paintGroup.size() ; i++){
                canvas.drawPath(pathGroup.get(i), paintGroup.get(i));
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        // 터치가 일어나면 좌표를 세팅
//        xs.add(event.getX());
//        ys.add(event.getY());

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 좌표 터치시 해당 좌표로 이동한다. (이전점과 현재점 사이를 그리지 않는다.)
                currentPath.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                // 터치가 일어나면 패스를 해당 좌표까지 선을 긎는다. (이전점과 현재점 사이를 그린다.)
                currentPath.lineTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                // nothing to do
                break;
        }

        // onDraw를 호출하는 메서드를 호출
        invalidate();
        //<- 다른 언어에서는 대부분 그림을 그려주는 함수를 호출하는 메서드는 기존 그림을 유지하는데, 안드로이드는 지운다.

        // 리턴이 false일 경우는 touch 이벤트를 연속해서 발생시키지 않는다.
        // 즉, 드래그를 하면 onTouchEvent가 재호출되지 않는다.
        return true;
    }
}

// super(인자) 는 super.생성자(인자) 와 동일하다.