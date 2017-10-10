package com.example.kyung.basicthread;

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
        setViewPager();
        setTabLayout();
        setListener();
    }

    private void setViewPager(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        CustomPagerAdapter adapter = new CustomPagerAdapter(this);
        adapter.setView(viewPager);
    }

    private void setTabLayout(){
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("BtnRot"));
        tabLayout.addTab(tabLayout.newTab().setText("RainDrop"));
        tabLayout.addTab(tabLayout.newTab().setText("Sample1"));
        tabLayout.addTab(tabLayout.newTab().setText("Sample2"));
    }

    private void setListener(){
        // 탭 레이아웃을 뷰 페이저에 연결
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        // ViewPager의 변경사항을 탭 레이아웃에 전달
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
