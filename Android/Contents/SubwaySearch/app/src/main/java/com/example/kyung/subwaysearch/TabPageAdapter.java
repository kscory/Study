package com.example.kyung.subwaysearch;

import android.content.ContentResolver;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kyung.subwaysearch.linesearch.LineSearch;
import com.example.kyung.subwaysearch.stationsearch.StationSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-19.
 */

public class TabPageAdapter extends PagerAdapter {

    Context context;
    private final int COUNT=2;
    List<View> viewList;

    public TabPageAdapter(Context context){
        this.context = context;
        viewList = new ArrayList<>();
        viewList.add(new StationSearch(context));
        viewList.add(new LineSearch(context));
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
