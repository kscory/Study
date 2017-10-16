package com.example.kyung.basicnetwork;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-16.
 */

public class NetworkAdapter extends PagerAdapter {

    private static final int COUNT = 2;

    List<View> viewList;
    Context context;

    public NetworkAdapter(Context context){
        this.context = context;
        viewList = new ArrayList<>();
        viewList.add(new NetworkBasic(context));
        viewList.add(new AsyncTask(context));
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
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
