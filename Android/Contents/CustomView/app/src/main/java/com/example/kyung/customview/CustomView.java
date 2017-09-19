package com.example.kyung.customview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kyung on 2017-09-18.
 */

public class CustomView extends View{

    // 내가 소스코드에서 생성할 때
    public CustomView(Context context){
        super(context);

    }

    //xml 에서 태그로 사용할 때 시스템에서 호출해주는 생성자
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 색, 두께 정하기
        Paint paint = new Paint(); // 물감 - 겉모양을 결정하는 도구
        paint.setColor(Color.MAGENTA);

        // 화면에 사각형 그리기
        canvas.drawRect(100, 100, 200, 200, paint);

    }
}
