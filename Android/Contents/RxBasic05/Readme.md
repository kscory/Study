# RxJava Basic5
  - RxJava 와 Retrofit을 이용하여 외부 Api 불러오기
  - 날씨 정보 받아오기

---

## 내용
  ### 0. 시작하기
  - gradle 에 rx 안드로이드, 레트로핏, 레트로핏의 json 컨버터, 레트로핏의 rx 아답터를 설치한다.
  - [참고_retrofit git](https://github.com/square/retrofit)

  ```java
  implementation 'io.reactivex.rxjava2:rxandroid:2.0.1' // rx 안드로이드
  implementation 'com.squareup.retrofit2:retrofit:2.3.0' // 레트로핏
  implementation 'com.squareup.retrofit2:converter-gson:2.3.0' // 레트로핏 json 컨버터
  implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0' // 레트로핏 rx 아답터
  ```

  ### 1. 데이터 모델
  - hanasodlibrary를 이용하여 데이터를 불러온다.
  - [링크](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/RxBasic05/app/src/main/java/com/example/kyung/rxbasic05/domain)

  ### 2. Retrofit interface
  - path 어노테이션을 주어 값을 대입시킬 수 있다.
  - get 어노테이션을 이용하여 명령을 전달한다.

  > IWeather.java

  ```java
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
  ```

  ### 3. 사용 방법
  - 레트로핏 builder를 이용해 빌더를 생성
  - 서비스를 생성
  - 옵저버블 생성
  - 발행 및 구독

  ```java
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
          builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
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
  ```

  ### 4. 결과

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RxBasic05/picture/result.png)
