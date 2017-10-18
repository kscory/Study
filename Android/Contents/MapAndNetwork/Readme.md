# Map & BasicNetwork2
  - GoogleMap 사용법 (Activity & Fragement)
  - (추가)

---

## GoogleMap 예제 결과
  - 서울 열린데이터에서 일반병원 위치 api를 이용
  - 결과 사진
  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/MapAndNetwork/picture/map.png)

---

## GoogleMap 예제1 (Activity 상에서 이용)
  ### 1. MapsActivity 생성 & 레이아웃 구성 & 서울시 api
  - EmptyActivity가 아니라 MapsActivity로 생성한다. (Activity로 이용)
  - `android:name="com.google.android.gms.maps.SupportMapFragment"`를 이용한다.

  > activity_maps.xml

  ```xml
  <fragment xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:map="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:id="@+id/map"
      android:name="com.google.android.gms.maps.SupportMapFragment"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      tools:context="com.example.kyung.bicycle.MapsActivity" />
  <!-- SupportMapFragment를 new 해서 만듦, 나중에는 이를 상속받아서 만들어 낸다. -->
  ```

  ### 2. GoogleMap Api 이용법
  - 링크를 타고 들어가 project를 생성하고 api를 생성한다.
  - api가 작동이 잘 안될 경우에는 아래 사진과 같이 제한사항을 없음으로 수정한 후 이용한다.
  - api를 복사하여 안드로이드 스튜디오 알맞은 곳에 적용한다.
  - 간단한 사용방법 사진

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/MapAndNetwork/picture/googlemap.png)


  ### 3. Remote 클래스 & Json 데이터 클래스
  - 네트워크를 연동하는 Remote 클래스 생성 ( [참고](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/MapAndNetwork/app/src/main/java/com/example/kyung/mapandnetwork/Remote.java) )
  - 오픈api를 이용해 Json 데이터를 자바 클래스 형태로 변환한다. ( [참고_JsonData Basic](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/JsonDataPractice) )

  ### 4. 지도를 뿌려주는 MapsActivity
  - `FramentAcitivty`를 상속받아 이용한다.
  - `onMapReadyCallback` 인터페이스를 상속받아 이를 구현한다.
  - `SupportMapFragment` 를 이용하며 `mapFragment.getMapAsync(context);` 를 이용하여 맵이 사용할 준비체크 후 `onMapReady `메소드를 실행시킨다.

  ```java
  public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

      private GoogleMap mMap;
      SupportMapFragment mapFragment;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_maps);
          // Obtain the SupportMapFragment and get notified when the map is ready to be used.
          mapFragment = (SupportMapFragment) getSupportFragmentManager()
                  .findFragmentById(R.id.map);
          load();
      }

      private void load(){
          new AsyncTask<Void, Void, String>(){
              @Override
              protected String doInBackground(Void... params) {
                  return Remote.getData("http://openapi.seoul.go.kr:8088/(인증키)/json/GeoInfoBikeConvenientFacilitiesWGS/1/1000/");
              }
              @Override
              protected void onPostExecute(String s) {

                  Gson gson = new Gson();
                  Json json = gson.fromJson(s,Json.class);
                  rows = json.getGeoInfoBikeConvenientFacilitiesWGS().getRow();
                  Log.d("MapsActivity","====="+rows);
                  // 맵이 사용할 준비가 되었는지를 비동기로 확인
                  // 맵이 사용할 준비가 되었으면 -> onMapReadyCallback.onMapReady를 호출
                  mapFragment.getMapAsync(MapsActivity.this);
              }
          }.execute();
      }

      Row[] rows = null;
      // 데이터를 사용해서 마크를 각 좌표에 출력
      @Override
      public void onMapReady(GoogleMap googleMap){
          mMap = googleMap;
          LatLng point = null;
          // Add a marker in Sydney and move the camera
          for(Row row : rows){
              point = new LatLng(Double.parseDouble(row.getLAT()),Double.parseDouble(row.getLNG()));
              mMap.addMarker(new MarkerOptions().position(point).title(row.getCLASS()));
          }
          LatLng location = new LatLng(37.5669029,126.9889492);
          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14)); // point로 움직임
      }
  }
  ```

---

## GoogleMap 예제2 (Frgment로 이용)
  ### 1. 레이아웃 구성
  - Fragment에 Map을 구현하는 경우 fragment가 아닌 MapView를 이용한다.
  - MapView는 `android:name="com.google.android.gms.maps.MapFragment"` 을 사용한다.

  > activity_maps.xml

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:tools="http://schemas.android.com/tools"
      android:id="@+id/frameLayout"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:orientation="vertical"
      tools:context="com.example.kyung.mapandnetwork.hospitalmap.MapsActivity">

      <com.google.android.gms.maps.MapView
          android:id="@+id/mapView"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:name="com.google.android.gms.maps.MapFragment">
      </com.google.android.gms.maps.MapView>

  </FrameLayout>
  ```
  ### 2. 지도를 뿌려주는 MapsActivity (MapsFragment)
  - `Frament`를 상속받아 이용한다.
  - `onMapReadyCallback` 인터페이스를 상속받아 이를 구현한다.
  - `SupportMapFragment` 를 이용하며 `mapFragment.getMapAsync(context);` 를 이용하여 맵이 사용할 준비체크 후 `onMapReady `메소드를 실행시킨다.
  - Frament를 이용하면 `onCreateView` 에서 mapView와 View를 초기화하며 데이터를 로드한다.
  - 반드시 `onActivityCreated` 를 이용하여 엑티비티가 생성될때 mapView를 생성해주어야만 한다.
  - mapView는 Frament의 생명주기에 따라 함께 관리되어져야 한다.

  ```java
  public class MapsActivity extends Fragment implements OnMapReadyCallback {

      public MapsActivity() { }

      MapView mapView;
      private GoogleMap mMap;
      JsonHos jsonHosData;
      Row[] rowHos = null;
      Activity activity;

      @Override
      public void onAttach(Context context) {
          super.onAttach(context);
          if(context instanceof Activity){
              activity = (Activity) context;
          }
      }

      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.mapview, container, false);
          mapView = (MapView) view.findViewById(R.id.mapView);
          load();
          return view;
      }

      public void load(){
          new AsyncTask<Void, Void, String>(){
              @Override
              protected String doInBackground(Void... params) {
                  return Remote.getData("http://openapi.seoul.go.kr:8088/(인증키)/json/SdeGiGhospP2015WGS/1/100/");
              }
              @Override
              protected void onPostExecute(String jsonHospital) {
                  if(jsonHospital=="ConnectionError"){
                      Toast.makeText(activity, "접속이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
                  } else{
                      Gson gson = new Gson();
                      jsonHosData = gson.fromJson(jsonHospital,JsonHos.class);
                      rowHos = jsonHosData.getSdeGiGhospP2015WGS().getRow();

                      mapView.getMapAsync(MapsActivity.this);
                  }
              }
          }.execute();
      }

      @Override
      public void onMapReady(GoogleMap googleMap) {
          Log.e("onMapReady","=================연결완료");
          mMap = googleMap;
          LatLng point;
          // 데이터를 사용해서 마크를 각 좌표에 출력
          for(Row hos : rowHos){
              point = new LatLng(Double.parseDouble(hos.getLAT()),Double.parseDouble(hos.getLNG()));
              mMap.addMarker(new MarkerOptions().position(point).title(hos.getHOSP_NM()));
          }
          LatLng location = new LatLng(37.5669029,126.9889492);
          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12)); // location 위치로 움직임
      }
      // 엑티비티가 생성될때 호출되는 메소드
      @Override
      public void onActivityCreated(@Nullable Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
          if(mapView !=null){
              mapView.onCreate(savedInstanceState);
          }
      }
      // Fragment 생명주기 관리
      @Override
      public void onStart() {
          super.onStart();
          mapView.onStart();
      }
      @Override
      public void onStop() {
          super.onStop();
          mapView.onStop();
      }
      @Override
      public void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);
          mapView.onSaveInstanceState(outState);
      }
      @Override
      public void onResume() {
          super.onResume();
          mapView.onResume();
      }
      @Override
      public void onPause() {
          super.onPause();
          mapView.onPause();
      }
      @Override
      public void onLowMemory() {
          super.onLowMemory();
          mapView.onLowMemory();
      }
      @Override
      public void onDestroy() {
          super.onDestroy();
          mapView.onLowMemory();
      }
  }
  ```

---

## 추가사항
  ### 1. 대제목
  - 내용
