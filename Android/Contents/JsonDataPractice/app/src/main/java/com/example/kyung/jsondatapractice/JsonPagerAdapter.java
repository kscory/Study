package com.example.kyung.jsondatapractice;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.kyung.jsondatapractice.githubusers.GitHubUserView;
import com.example.kyung.jsondatapractice.transferstation.TransferStationView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-17.
 */

public class JsonPagerAdapter extends PagerAdapter {
    List<View> viewList;
    Context context;

    private static final int COUNT = 2;

    public JsonPagerAdapter(Context context){
        this.context = context;
        viewList = new ArrayList<>();
        viewList.add(new GitHubUserView(context));
        viewList.add(new TransferStationView(context));
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view =viewList.get(position);
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
