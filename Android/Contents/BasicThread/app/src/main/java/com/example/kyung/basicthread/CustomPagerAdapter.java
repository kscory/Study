package com.example.kyung.basicthread;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.kyung.basicthread.firstexample.ThreadOneView;
import com.example.kyung.basicthread.secondexample.ThreadTwoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-10.
 */

public class CustomPagerAdapter extends PagerAdapter {

    private static final int COUNT = 4;
    Context context;
    List<View> viewList;

    // view를 add 해준다.
    public CustomPagerAdapter(Context context){
        this.context = context;
        viewList = new ArrayList<>();
        viewList.add(new ThreadOneView(context));
        viewList.add(new ThreadTwoView(context));
        viewList.add(new ThreadThreeView(context));
        viewList.add(new ThreadFourView(context));
    }

    public void setView(ViewPager viewPager){
        viewPager.setAdapter(this);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    // instantiateItem 에서 리턴된 object가 View가 맞는지 확인
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 현재 사용하지 않는 View는 제거
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    // view를 세팅
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        container.addView(view);
        return view;
    }
}
