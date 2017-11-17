package com.example.kyung.rxbasic05;

import com.example.kyung.rxbasic05.domain.WeatherApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Kyung on 2017-11-16.
 */

// 레트로핏 인터페이스 생성
public interface IWeather {

    String SERVER_URL = "http://openapi.seoul.go.kr:8088/";
    String SERVER_KEY = "key";

    @GET("{key}/json/RealtimeWeatherStation/{skip}/{count}/{gu}")
    Observable<WeatherApi> getData(
            @Path("key") String server_key,
            @Path("skip") int skip,
            @Path("count") int count,
            @Path("gu") String gu
    );
}
