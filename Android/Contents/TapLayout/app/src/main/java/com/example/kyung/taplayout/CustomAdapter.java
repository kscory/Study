package com.example.kyung.taplayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

/**
 * 페이저 아답터에 프래그먼트 배열을 넘겨서
 * 동작하게 한다.
 */

public class CustomAdapter extends FragmentStatePagerAdapter {

    private static final int COUNT =4;

    public CustomAdapter(FragmentManager fm) {
        super(fm);
    }


    // 사실 프레그먼트는 이렇게 메모리가 로드될때만 사용( 세개만 올라가게 된다)
    // 이는 getitem을 세번 호출하고 삭제한다.
    // 이는 선택의 문제 (두가지 방식 모두 존재)
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new TwoFragment();
            case 2:
                return new ThreeFragment();
            case 3:
                return new FourFragment();
            default:
                return new OneFragment();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    // 이렇게 하면 메모리에 계속 남아있게 된다. (위와 차이 날 수 있다.)
//    Fragment fragment;
//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = null;
//        switch (position){
//            case 1:
//                fragment = new TwoFragment();
//            case 2:
//                fragment = new ThreeFragment();
//            case 3:
//                fragment = new FourFragment();
//            default:
//                fragment = new OneFragment();
//        }
//        return fragment;
//    }


    // # 2. 만약 멀리까지 호출해야 하면 이렇게 한번에 많이 호출하는 것이 좋다.
//        List<Fragment> data;

//    public CustomAdapter(FragmentManager fm, List<Fragment> data) {
//        super(fm);
//        this.data = data;
//    }

//    @Override
//    public Fragment getItem(int position) {
//        return data.get(position);
//    }

//    @Override
//    public int getCount() {
//        return data.size();
//    }

}
