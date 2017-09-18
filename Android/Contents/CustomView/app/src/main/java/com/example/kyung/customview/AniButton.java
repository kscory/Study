package com.example.kyung.customview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
// v4 < v7 이라고 되어 있지만 v4에서 지원하는 것을 v7이 지원하지 않을 때도 있다. 대표적으로 ViewPage.
// 따라서 상황에 따라 다르게 해주어야 한다.
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Kyung on 2017-09-18.
 *
 */

//Alt+enter를 이용해 확인하여 메소드 변수?? 결정 + supper 등 설정
@SuppressLint("AppCompatCustomView")
public class AniButton extends AppCompatButton implements View.OnTouchListener {
    // animation이 true일 때만 실행
    boolean animation = false;

    public AniButton(Context context, AttributeSet attrs) { // attr에 정의한 모든 속성이 담겨진다.
        super(context, attrs);
        // 1. attrs.xml 에 정의된 속성을 가져온다.
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.AniButton);
        // 2. 해당 이름으로 정의된 속성의 개수를 가져온다.
        int size = typed.getIndexCount();
        Log.d("AniButton", "size="+size);
        // 3. 반복문을 돌면서 해당 속성에 대한 처리를 해준다.
        for(int i =0 ; i<size ; i++){
            // 3.1 현재 배열에 있는 속성 아이디 가져오기
            int current_attr = typed.getIndex(i);
            switch (current_attr){
                case R.styleable.AniButton_animation:
                    String animation = typed.getString(current_attr);
                    if("true".equals(animation)){ // 이렇게 해야 animation이 null 일때 exception이 발생하지 않는다.
                        String currentText = getText().toString(); // 현재 입력된 값을 가져올 수 있다.
                        setText("[animation]\n" + currentText );
                        this.animation=true;
                    }
                    break;
                case R.styleable.AniButton_delimeter:
                    break;
            }
        }
        setOnTouchListener(this);
    }

    private void scaleAni(){
        if(animation){
            // ObjectAnimator startAniScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1.0f);
            // 위와같이 하면 커졌다 돌아올 수 있다.

            ObjectAnimator startAniScaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.5f);
            ObjectAnimator startAniScaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.5f);

            ObjectAnimator endAniScaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f);
            ObjectAnimator endAniScaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f);

            AnimatorSet aniSet = new AnimatorSet();
            AnimatorSet aniSet1 = new AnimatorSet();
            AnimatorSet aniSet2 = new AnimatorSet();

            aniSet1.playTogether(startAniScaleX,startAniScaleY);
            aniSet2.playTogether(endAniScaleX,endAniScaleY);

            aniSet.playSequentially(aniSet1,aniSet2);
            aniSet.setDuration(1000);

            aniSet.start();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scaleAni();
                break;
        }
        return false;
    }
}
