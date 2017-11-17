package com.example.kyung.rxbasic05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kyung.rxbasic05.domain.Row;
import com.example.kyung.rxbasic05.domain.WeatherApi;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText idSearch;
    IWeather service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idSearch = findViewById(R.id.idSearch);

        // 0. 레트로핏 인터페이스 생성
        // 1. 생성
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(IWeather.SERVER_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create()); // builder도 디자인 패턴중에 하나이다.

        Retrofit retrofit = builder.build(); // 레트로핏 객체로 만들어 준다.

        // 2. 서비스 만들기 < 인터페이스로부터
        service = retrofit.create(IWeather.class);

    }

    public void weatherSearch(View view){
        // 3. 옵저버블(Emitter) 생성
        Observable<WeatherApi> observable = service.getData(IWeather.SERVER_KEY,1,22,idSearch.getText().toString());

        // 4. 동작시작
        observable
                // 네트워크에서 데이터를 구독한다.
                .subscribeOn(Schedulers.io()) // <- service.getData 함수가 여기서 동작
                // 구독한 데이터를 실제 적용
                .observeOn(AndroidSchedulers.mainThread()) // <- service.getData 함수가 읽어온 결과를 받는다.
                // 5. 구독
                .subscribe(
                        weatherApi -> {
                            String result = "";
                            if(weatherApi.getRealtimeWeatherStation() == null){
                                return;
                            }
                            for(Row row : weatherApi.getRealtimeWeatherStation().getRow()){
                                result += "지역명: " +row.getSTN_NM() +"\n";
                                result += "온도: " +row.getSAWS_TA_AVG() +"\n";
                                result += "습도: " +row.getSAWS_HD() +"\n";
                            }
                            ((TextView)findViewById(R.id.result)).setText(result);
                        },
                        e -> { /* 에러처리*/},
                        () -> { /* 성공처리*/}
                );
    }
}