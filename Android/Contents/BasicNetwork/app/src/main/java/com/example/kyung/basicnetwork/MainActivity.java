package com.example.kyung.basicnetwork;

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
        initViewPager();
        setListener();
    }

    private void initView(){
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initTabLayout(){
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_network));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_asynctask));
    }

    private void initViewPager(){
        NetworkAdapter adapter = new NetworkAdapter(this);
        viewPager.setAdapter(adapter);
    }

    private void setListener(){
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
