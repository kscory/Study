package com.example.kyung.subwaysearch.subwayschedule;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kyung.subwaysearch.R;

/**
 * Created by Kyung on 2017-10-21.
 */

public class ScheduleView extends FrameLayout {

    ConstraintLayout constraintSearch;
    RelativeLayout relativeLine;
    EditText stationMain;
    TextView stationRight;
    TextView stationLeft;
    TabLayout tabLayout;
    ViewPager viewPager;

    public ScheduleView(Context context) {
        super(context);
        initView();
    }

    public void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.subwayschedule,null);
        constraintSearch = (ConstraintLayout) view.findViewById(R.id.constraintSearch);
        relativeLine = (RelativeLayout) view.findViewById(R.id.relativeLine);
        stationMain = (EditText) view.findViewById(R.id.stationMain);
        stationRight = (TextView) view.findViewById(R.id.stationRight);
        stationLeft = (TextView) view.findViewById(R.id.stationLeft);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        addView(view);
    }


}
