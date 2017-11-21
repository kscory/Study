# GoogleMap
  - GoogleMap 사용법 (Activity & Fragement)
  - 오픈 api 이용
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

## 그외 map과 관련된 기능
  ### 1. LocationManager & LocationListener (google api 이용하지 않을 시)
  - `LocationManager를` 활용하여 내 위치를 가져올 수 있다.
  - `LocationListener`의 종류는 아래 네 가지가 존재
    - `void onLocationChanged(Location location)` : 위치 정보가 location에 담겨 있어 현재 위치정보가 특정 조건에 의해 가져올 수 있는 메소드, 단순히 위치 정보를 구한다면, 이 부분에서만 처리해도 됨
    - `void onProviderDisabled` : 위치 공급자가 사용 불가능해질(disabled) 때 호출
    - `void onProviderEnabled` : 위치 공급자가 사용 가능해질(enabled) 때 호출.
    - `void onStatusChanged` : 위치 공급자의 상태가 바뀔 때 호출

  > CustomLocationManager

  ```java
  public class CustomLocationManager implements LocationListener {

      private LocationManager locationManager;
      private Location lastLocation;
      private double lastLat;
      private double lastLan;

      public CustomLocationManager(Context context){
          locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
      }

      // GPS 혹은 네트워크가 켜져있는지 체크해주는 메소드
      public boolean checkGPSOrNetwork(){
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return (gpsEnabled || networkEnabled);
      }

      //내 위치를 자동으로 업데이트 해주는 메소드 (시작, 끝을 선언해준다,)
      @SuppressLint("MissingPermission")
      public void stratUpdateLocation(long intervalMiliTime, float intervalMeter){
          // 인자 : GPS 등 어느 것을 체크할지 , 몇초마다 갱신할지, 몇미터마다, 리스너
          locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,intervalMiliTime, intervalMeter, this);
          locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,intervalMiliTime, intervalMeter, this);
      }
      public void stopUpdateLocation(){
          locationManager.removeUpdates(this);
      }

      // locationManager에 달리는 리스너 정의
      @Override
      public void onLocationChanged(Location location) {
          lastLat = location.getLatitude();
          lastLan = location.getLongitude();
          lastLocation = location;
      }
      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) { }

      @Override
      public void onProviderEnabled(String provider) {}

      @Override
      public void onProviderDisabled(String provider) {}
  }
  ```

---

## 참고
  ### 1. GoogleMap API release 버전 (keystore)-[참고](https://developers.google.com/maps/documentation/android-api/signup)
  - 먼저 AndroidStudio에서 keystore를 생성

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/MapAndNetwork/picture/releasemap1.png)

  - 생성 후 위에서의 방법과 같이 구글 프로젝트에 등록
    - 명령어는 .android 경로에 들어가서 `keytool -list -v -keystore "저장한 key파일경로"` 입력
    - 키 정보는 반드시 `release 경로에 있는 xml에 입력할 것`

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/MapAndNetwork/picture/releasemap2.png)

  - gradle에 명령어를 추가
    - 사용하기 위해서는 gradle에서 `signingConfig`를 정의해주어야 한다.
    - 이 key 정보를 git에 올리지 않기 위해 `gradle.properties` 파일에 정보를 입력해주면 바로 쓸 수 있으며 gitignore에서 `/gradle.properties` 를 추가해준다.
    - 참고로 아래와 같이 서버 url을 정의하여 사용할 수 있으며 자바코드에서 `BuildConfig."이름"`을 호출하면 url 불러올 수 있다.

  > app gradle

  ```java
  signingConfigs{
      release{
          storeFile file("C:\\workspaces\\studykey\\mapex.jks") // 파일경로 입력
          storePassword storePw // 패스워드 입력
          keyAlias aliasId // key의 id 입력
          keyPassword aliasPw // key의 password 입력
      }
  }
  buildTypes {
      debug {
          buildConfigField "String", "SERVER_URL", "\"http://192.168.1.184:8080/\"" // debug 를 뒤에 붙인다.
      }
      release {
          buildConfigField "String", "SERVER_URL", "\"http://192.168.1.184:8080/\"" // release 를 뒤에 붙인다.

          signingConfig signingConfigs.release // map을 실행
          minifyEnabled false
          proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
      }
  }
  ```

  > gradle.properties

  ```java
  storePw=PW
  aliasId=별칭
  aliasPw=KEYPW
  ```

  ### 2. buildType 바꾸기
  - 아래 그림과 같이 AndroidStudio에서 왼쪽 아래의 `build variants` 를 클릭 후 debug / release 등의 모드를 바꿀 수 있다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/MapAndNetwork/picture/releasemap3.png)
