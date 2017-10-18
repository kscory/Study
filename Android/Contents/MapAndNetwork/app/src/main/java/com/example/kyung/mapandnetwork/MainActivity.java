package com.example.kyung.mapandnetwork;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initTabLayout();
        setViewAdapter();
        initSetListenr();
    }

    private void initView(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

    private void initTabLayout(){
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_maphos)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_temp)));
    }

    private void initSetListenr(){
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setViewAdapter(){
        FrgPagerAdapter adapter = new FrgPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
