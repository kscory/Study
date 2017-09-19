package com.example.kyung.customview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-09-18.
 */

public class DrawView extends View {

    Paint paint;
    Path currentPath;

    List<PathTool> paths = new ArrayList<>();

    public DrawView(Context context) {
        super(context);

        paint = new Paint();
        init();
    }

    public void init(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        setColor(Color.BLACK);
    }

    public void setColor(int color){
        PathTool tool = new PathTool();
        tool.setColor(color);
        currentPath = tool;
        paths.add(tool);
    }

    public void memoClear(){
        int iColor = paths.get(paths.size()-1).getColor() ;
        paths.clear();
        init();
        paths.get(0).setColor(iColor);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(PathTool tool : paths){
            paint.setColor(tool.getColor());
            canvas.drawPath(tool,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentPath.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
}

class PathTool extends Path{
    private int color;
    public void setColor(int color){
        this.color = color;
    }
    public int getColor(){
        return this.color;
    }
}
