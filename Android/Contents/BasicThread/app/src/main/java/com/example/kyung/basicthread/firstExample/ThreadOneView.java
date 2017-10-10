package com.example.kyung.basicthread.firstExample;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import com.example.kyung.basicthread.R;

/**
 * Created by Kyung on 2017-10-10.
 */

public class ThreadOneView extends FrameLayout {

    Button rotObject;
    ToggleButton toggleRot;
    Rotator rotator;
    boolean isCheckFirst = false;

    public static final int ACTION_SET = 999;

    public ThreadOneView(Context context) {
        super(context);
        initView();
        prcess();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_thread_one,null);
        rotObject = (Button) view.findViewById(R.id.rotObject);
        toggleRot = (ToggleButton) view.findViewById(R.id.toggleRot);
        addView(view);
    }

    private void prcess(){
        rotator = new Rotator(handler);
        // 참고 : rotater.setSeekbar(); // 이러면 mainthread에서 실행된다.
        toggleRot.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    // 버튼을 변경하는 Hnadler 작성
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case ACTION_SET:
                    float curRot = rotObject.getRotation();
                    rotObject.setRotation(curRot + 6);
                    break;
            }
        }
    };

    // 토글버튼에 따라 회전 결정
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                rotator.start();
            } else{
                rotator.setStop();
            }
        }
    };
}
