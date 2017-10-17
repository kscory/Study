package com.example.kyung.jsondatapractice;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initTabLayout();
        initViewPager();
        setTabWithViewPager();
    }

    private void initView(){
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initTabLayout(){
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_git)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_TrsnS)));
    }

    private void initViewPager(){
        JsonPagerAdapter adapter = new JsonPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }

    private void setTabWithViewPager(){
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
