package com.example.kyung.basicthread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Kyung on 2017-10-10.
 */

public class ThreadTwoView extends FrameLayout {
    public ThreadTwoView(Context context) {
        super(context);

        prcess();

        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_thread_two,null);
        addView(view);
    }

    private void prcess(){
        
    }
}
