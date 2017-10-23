package com.example.kyung.subwaysearch.CustomButton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyung.subwaysearch.R;

/**
 * Created by Kyung on 2017-10-23.
 */

public class CustomTab extends FrameLayout{

    private String day;
    private TextView textTab;
    private ImageView imageTab;
    private View tabView;

    public CustomTab(Context context,String day) {
        super(context);
        this.day=day;
        init();
    }

    private void init(){
        tabView = LayoutInflater.from(getContext()).inflate(R.layout.custom_tab,null);
        textTab = (TextView) tabView.findViewById(R.id.textTab);
        imageTab = (ImageView) tabView.findViewById(R.id.imageTab);
        textTab.setText(day);
        imageTab.setImageResource(R.drawable.tabdesign);
        addView(tabView);
    }
    public void clickTab(){
        imageTab.setImageResource(R.drawable.tabdesign_check);
    }
    public void unClickTab(){
        imageTab.setImageResource(R.drawable.tabdesign);
    }
}
