package com.example.kyung.taplayout;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTabLayout();
        setViewPager();

        setListener();

    }

    private void setListener(){
        // 탭 레이아웃을 뷰 페이저에 연결
        tabLayout.addOnTabSelectedListener( new TabLayout.ViewPagerOnTabSelectedListener(viewPager) );

        // ViewPager의 변경사항을 탭 레이아웃에 전달
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setViewPager(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        // 실제로는 data를 이렇게 넘기지 않는다.
//        List<Fragment> data = new ArrayList<>();
//        data.add(new OneFragment());
//        data.add(new TwoFragment());
//        data.add(new ThreeFragment());
//        data.add(new FourFragment());
//        CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager(),data);
//
//        viewPager.setAdapter(adapter);
    }

    private  void setTabLayout(){
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab( tabLayout.newTab().setText("One") );
        tabLayout.addTab( tabLayout.newTab().setText("Two") );
        tabLayout.addTab( tabLayout.newTab().setText("Three") );
        tabLayout.addTab( tabLayout.newTab().setText("Four") );
    }
}
