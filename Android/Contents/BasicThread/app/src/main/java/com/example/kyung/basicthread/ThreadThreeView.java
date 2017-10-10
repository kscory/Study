package com.example.kyung.basicthread;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Kyung on 2017-10-10.
 */

public class ThreadThreeView extends FrameLayout {
    public ThreadThreeView(Context context) {
        super(context);
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_thread_three,null);
        addView(view);
    }
}
