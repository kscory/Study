package com.example.kyung.subwaysearch.subwayschedule;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 넘긴 뷰를 연결만 한다.
 */

public class DayPagerAdpater extends PagerAdapter {

    List<View> viewList;
    private int COUNT = 3;

    public DayPagerAdpater(List<View> viewList){
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
