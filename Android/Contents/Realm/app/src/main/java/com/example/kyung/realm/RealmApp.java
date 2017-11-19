package com.example.kyung.realm;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Kyung on 2017-11-17.
 */

// Application을 만든다 보통 (앱이름 + App으로 클래스를 생성)
// Manifest에 application name에 설정해주면 앱이 로드할때 생성시키게 된다.
// Activity에 설정하면 Activity 단위로 로드되므로 주의할 것
// 하지만 SQLLite의 경우 사용하는 자원이 달라서 여기서 사용하면 안된다.
public class RealmApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
