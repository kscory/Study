package com.example.kyung.basicfragment;

import android.content.res.Configuration;
import android.support.v4.app.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kyung.basicfragment.domain.Contact;

import java.util.List;

public class MainActivity extends BaseActivity implements ListFragment.CallbackDetail {

    @Override
    public void init() {
        setContentView(R.layout.activity_main);

        if(getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT){ // 현재 레이아웃 세로체크
            initFragment();
        }
    }

    @Override
    public void changeInit() {
        setContentView(R.layout.activity_main);

    }

    // 프래그 먼트를 더함
    private void initFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container,new ListFragment()) //addtoBack..Stack은 쓰지 않음
                .commit();
    }

    @Override
    public void showDetail(int id) {
        // 레이아웃이 세로이면 디테일 프레그먼트를 화면에 보이면서 값을 전달
        if(getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT){
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",id);
            // 프레그먼트로 값 전달하기
            detailFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }
        // 레이아웃이 가로이면 삽입되어 있는 프레그먼트를 가져온다,
        else {
            DetailFragment detailFragment
                    = (DetailFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragmentDetail);
            // 만들어놓은 함수를 호출해서 값을 세팅
            if(detailFragment != null){
                Contact contact;
                contact = detailFragment.loaddata(id);
                detailFragment.setTextNo(contact.getId());
                detailFragment.setTextName(contact.getName());
                detailFragment.setTextNumber(contact.getNumber());
            }
        }
    }
}
