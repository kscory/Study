package com.example.kyung.firebasebasic2;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Retrofit 을 사용하기 위한 인터페이스
 */



public interface IRetro {
    // @GET("sendNotification?postData=")
    // 리턴타입 함수명(인자)
    @POST("sendNotification")
    Call<ResponseBody> sendNotification(@Body RequestBody postdata); // 따로 처리를 하지 않아도 http의 body가 담겨서 넘어간다.(@Body)


}
