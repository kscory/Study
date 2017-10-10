package com.example.kyung.basicthread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Kyung on 2017-10-10.
 */

public class ThreadFourView extends FrameLayout {
    public ThreadFourView(Context context) {
        super(context);
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_thread_four,null);
        addView(view);
    }
}
