package com.example.kyung.fragmentbasic2;

import android.content.res.Configuration;
import android.support.v4.app.*;
import android.support.v4.content.res.ConfigurationHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ListFragment.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ActivityMain을 두개를 띄운다. (layout-land에 생성하면 가로모드 Activity 생성)
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) // 이유는 각자 검색
            return;

        init();

    }

    private void init(){
        // 가로모드, 세로모드인지 체크
        if(getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT){ // 현재 레이아웃? 세로체크
            initFragment();
        }
    }

    private void initFragment(){ // 프레그먼트 더하기
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new ListFragment()) // addtoBack...Stack 은 쓰지 않는다 .=> 뒤로가면 죽어야 하므로
                .commit();
    }

    @Override
    public void goDetail(String value) {
        if(getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT){ // 현재 레이아웃? 세로체크
            // 디테일 프래그먼트를 화면에 보이면서 값을 전달
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("value", value);
            // 프래그먼트로 값 전달하기
            detailFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }else{
            // 현재 레이아웃에 삽입되어 있는 프래그먼트를 가져온다
            DetailFragment fragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
            // 만들어놓은 함수를 호출해서 값을 세팅한다.
            if(fragment != null) {
                fragment.setText(value);
            }
        }
    }
}