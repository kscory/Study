package com.example.kyung.taplayout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 페이저 아답터에 customview를 추가시켜
 * 동작하게 한다.
 */

public class CustomAdapter extends PagerAdapter {
    private static final int COUNT =4;
    List<View> views;

    public CustomAdapter(Context context){
        views = new ArrayList<>();
        views.add(new One(context));
        views.add(new Two(context));
        views.add(new Three(context));
        views.add(new Four(context));
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView( (View)object );
        //super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}

