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
import android.view.View;
import android.widget.Button;

/**
 * Created by Kyung on 2017-09-18.
 *
 */

//Alt+enter를 이용해 확인하여 메소드 변수?? 결정 + supper 등 설정
@SuppressLint("AppCompatCustomView")
public class AniButton extends AppCompatButton {

// Button 과 AppCompatButton의 차이 -> AppCompatButton은 하위레벨 버튼을 포함시켜서 API 레벨이 낮은 경우에도 버튼을 실행시킬 수 있다.

    /**
     * animation 속성이 true일 경우
     * scale 애니메이션을 사용해서 클릭시 살짝 커졌다 작아지는 버튼을 만들어 보세요
     */
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

                        this.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                scaleAni(v);
                            }
                        });
                    }
                    break;
                case R.styleable.AniButton_delimeter:
                    break;
            }
        }
    }

    public void scaleAni(View view){
        if (view instanceof AniButton){
            ObjectAnimator startAniScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.5f);
            ObjectAnimator startAniScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.5f);

            ObjectAnimator endAniScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f);
            ObjectAnimator endAniScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f);

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
}
