# GoogleMap Cluster & Daum Map
  - [GoogleMap Cluster](https://developers.google.com/maps/documentation/android-api/utility/marker-clustering?hl=ko) 사용법
  - [Daum(KaKao) Map](http://apis.map.daum.net/android/) 시작 및 사용법
  - [SwipeRefreshLayout](https://developer.android.com/reference/android/support/v4/widget/SwipeRefreshLayout.html) 사용법
  - [AQuery](https://code.google.com/archive/p/android-query/) 라이브러리 사용
  - [Picasso](https://github.com/square/picasso) 라이브러리 사용
  - 기타 : 서울 열린 데이터 광장 Api / SQLite 데이터베이스 / RecyclerView Precache / Network 연결 등 이용

---

## 개요 및 결과

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/GoogleMapFunction/picture/result1.png)

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/GoogleMapFunction/picture/result2.png)

---

## GoogleMap Cluster 사용법
  ### 1. 시작하기
  - Gradle dependency에 아래를 추가

  ```java
  implementation 'com.google.maps.android:android-maps-utils:0.4+'
  ```

  ### 2. Cluster에 사용될 marker 정의
  - 반드시 `ClusterItem` 을 상속받아 사용하며 필요에 따라 다른 변수를 저장한다.

  ```java
  public class MarkerItem implements ClusterItem {
      private LatLng position;
      private String objId;

      public MarkerItem(double lat, double lng, String objId){
          position = new LatLng(lat,lng);
          this.objId = objId;
      }

      @Override
      public LatLng getPosition() {
          return position;
      }
      public String getObjId() {
          return objId;
      }
  }  
  ```

  ### 3. ClusterManager 이용
  - ① 클러스터 매니저 초기화
  - ② 클러스터에 대한 특정 효과 추가
  - ③ 클러스터 매니저에 마커를 등록
    - 여기서는 setCluster를 호출하면 클러스터를 수정하게 된다.

  > CustomClusterManager.java

  ```java
  public class CustomClusterManager {

      ClusterManager<MarkerItem> clusterManager;
      GoogleMap googleMap;
      Contract.IClickAction clickAction;

      /**
       * 1. 클러스터 매니저 초기화
       * @param context
       * @param googleMap
       */
      public CustomClusterManager(Context context, GoogleMap googleMap){
          // 1. 클러스터 매니저 초기화
          clusterManager = new ClusterManager<MarkerItem>(context, googleMap);
          if(context instanceof Contract.IClickAction)
              clickAction = (Contract.IClickAction)context;
          else throw new RuntimeException(context.toString()  + " must implement IClickAction");
          this.googleMap = googleMap;
          init();
      }

      private void init(){
          setClickListener();
          setMapListener();
      }

      private void setMapListener(){
          googleMap.setOnCameraIdleListener(clusterManager);
          googleMap.setOnMarkerClickListener(clusterManager);
      }

      /**
       * 2. 클러스터에 대한 특정 효과 추가
       *    - 마커에 대한 클릭효과 추가
       */
      private void setClickListener(){
          // 클러스터 그룹 클릭시 효과
          clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MarkerItem>() {
              @Override
              public boolean onClusterClick(Cluster<MarkerItem> cluster) {
                  List<String> objs = new ArrayList<>();
                  for(MarkerItem markerItem : cluster.getItems()){
                      objs.add(markerItem.getObjId());
                  }
                  clickAction.showList(objs);
                  return false;
              }
          });

          // 단일 마커 클릭시 효과
          clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MarkerItem>() {
              @Override
              public boolean onClusterItemClick(MarkerItem markerItem) {
                  clickAction.showDetail(markerItem.getObjId());
                  return false;
              }
          });
      }

      /**
       * 3. 클러스터 매니저에 마커를 등록
       *    - 이를 호출하면 맵에 있는 클러스터를 수정하게 된다.
       * @param bikeConventions
       */
      public void setCluster(List<Row> bikeConventions){
          clusterManager.clearItems();
          for(Row conInfo : bikeConventions){
              // 2. 클러스터 매니저에 마커를 등록
              MarkerItem markerItem = new MarkerItem(conInfo.getLAT(), conInfo.getLNG(), conInfo.getOBJECTID());
              clusterManager.addItem(markerItem);
          }
          if(bikeConventions.size() > 1000 || bikeConventions.size()<=0) {
              googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Const.DEFAULT_LAT, Const.DEFAULT_LNG), 10));
          } else {
              LatLng latLng = new LatLng(bikeConventions.get(bikeConventions.size()-1).getLAT(), bikeConventions.get(bikeConventions.size()-1).getLNG());
              googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
          }
      }
  }
  ```

---

## Daum Map 시작하기
  ### 1. 시작하기
  - [다음맵 시작 공식 홈페이지](http://apis.map.daum.net/android/guide/) 를 보고 진행하며 가이드와 다르게 실행해야 할 것들을 적어놓음..
  - KaKao API에서 키 해시를 등록할 때 keytool을 사용하는 것이 아니라 `참고 4` 에 있는 것과 같이 패키지의 해시값을 찾은 후에 그것을 복사하여 넣도록 한다.
  - 라이브러리 파일 중 `libDaumMapAndroid.jar` 파일은 `libs` 디렉토리로, `armeabi.so, armeabi-v7a.so` 의 파일은 아래처럼 `jniLibs` 파일을 만들어 그 디렉토리에 넣어주어야 함

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/GoogleMapFunction/picture/daummap.png)

  - gradle에 아래를 추가한다.

  ```java
  implementation files('libs/libDaumMapAndroid.jar')
  ```

  ### 2. Map을 호출
  - xml에서 add할 layout을 설정

  ```xml
  <RelativeLayout
      android:id="@+id/kaKaoMap"
      android:layout_width="match_parent"
      android:layout_height="200dp" />
  ```

  - 코드에서 MapView를 연결 (MapView는 android가 아니라 `net.daum.mf.map.api.MapView` 에 있는 MapView로 설정한다.)

  ```java
  MapView mapView = new MapView(this);
  ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
  mapViewContainer.addView(mapView);
  ```

  ### 3. 간단한 기능
  - daumMap에 마커를 생성
  - 지도의 줌 바운드를 결정
  - 마커의 클릭효과를 설정

  ```java
  private void setKaKaoMap(Row bikeConvention){
      // 마커 세팅하기 (MapPoint를 만들어 세팅)
      MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(bikeConvention.getLAT(),bikeConvention.getLNG());
      setKaKaoMapMarker(mapPoint,bikeConvention.getADDRESS());
      MapPoint defaultMapPoint = MapPoint.mapPointWithGeoCoord(Const.DEFAULT_LAT,Const.DEFAULT_LNG);
      setKaKaoMapMarker(defaultMapPoint,"기본위치");
      // 지도 나오는 범위를 조절
      MapPoint[] mapPoints = {defaultMapPoint, mapPoint};
      setKaKaoMapBounds(mapPoints);
  }

  /**
   * daum map 에서 마커를 세팅
   * @param mapPoint
   * @param markerName
   */
  private void setKaKaoMapMarker(MapPoint mapPoint, String markerName){
      MapPOIItem marker = new MapPOIItem();
      marker.setItemName(markerName);
      marker.setMapPoint(mapPoint);
      marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
      marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
      mapView.addPOIItem(marker);
      // 마커로 포인트를 이동 및 줌 시킴 (boolean 값은 animation 을 활성화할지 결정)
      mapView.setMapCenterPoint(mapPoint,true);
      mapView.setZoomLevel(9,true);
      // 클릭효과 등록
      setMarkerClickListener(marker);
  }

  /**
   * 지도의 줌 바운드를 결정
   * @param mapPoints
   */
  private void setKaKaoMapBounds(MapPoint[] mapPoints){
      MapPointBounds mapPointBound = new MapPointBounds(mapPoints);
      int padding = 50;
      mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBound,padding));
  }

  /**
   * 마커의 클릭효과를 세팅
   * @param marker
   */
  private void setMarkerClickListener(MapPOIItem marker){
      mapView.setPOIItemEventListener(new MapView.POIItemEventListener() {
          @Override
          public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
              // POI Item 아이콘(마커)를 터치한 경우 호출
          }
          @Override
          public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
              // 삭제된 메소드
          }
          @Override
          public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
              // 클릭시 호출될 말풍선을 호출
              // CalloutBalloonButtonType : 말풍선의 버튼 타입을 의미
          }
          @Override
          public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
              // 마커의 drag 를 가능하게 하고 그 마커의 drag가 되었을 시에 호출
          }
      });
  }
}
  ```

---

## 참고
  ### 1. SwipeRefreshLayout
  - xml을 설정

  ```xml
  <android.support.v4.widget.SwipeRefreshLayout
          android:id="@+id/swipeRefresh"
          android:layout_width="0dp"
          android:layout_height="200dp"
          map:layout_constraintBottom_toBottomOf="parent"
          map:layout_constraintEnd_toEndOf="parent"
          map:layout_constraintStart_toStartOf="parent">

          <android.support.v7.widget.RecyclerView
              android:id="@+id/recyclerView"
              android:layout_width="match_parent"
              android:layout_height="match_parent">
          </android.support.v7.widget.RecyclerView>

      </android.support.v4.widget.SwipeRefreshLayout>
  ```

  - SwipeRefreshLayout의 효과 및 리스너 세팅

  ```java
  private void setSwipeLayout(){
       // 색깔 결정
       swipeRefresh.setColorSchemeColors(Color.GREEN);
       // 리소스면 setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent) 를 통해 색깔 결정

       // swipe 효과 결정
       swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               // 새로고침 시 검색된 모든 데이터 불러와서 뿌려주기
               adapter.setDateAndRefresh(bikeConventionList);
               // animation을 멈추려면 false 로 설
               swipeRefresh.setRefreshing(false);
           }
       });
   }
  ```

  ### 2. AQuery
  - grdle dependency에 추가

  ```java
  implementation 'com.googlecode.android-query:android-query:0.25.9'
  ```

  - 이미지를 로드 (data를 바인딩하듯이 세팅)

  ```java
  public static void loadImageByAQuery(Context context, String imageUrl, ImageView imageView){
      new AQuery(context).id(imageView).image(imageUrl);
  }
  ```

  ### 3. Picasso
  - grdle dependency에 추가

  ```java
  implementation 'com.squareup.picasso:picasso:2.5.2'
  ```

  - 이미지를 로드
  - 추가적으로 `resize` 등 사용 가능

  ```java
  public static void loadImageByPicasso(Context context, String imageUrl, ImageView imageView){
      Picasso.with(context)
              .load(imageUrl)
              .fit() // imageView의 크기에 맞게 화질을 바꾼다.
              .into(imageView);
  }
  ```

  ### 4. 패키지의 해시값 가져오기(keytool X)
  - 출처 : [stackoverflow](https://stackoverflow.com/questions/17423870/is-there-any-way-to-get-key-hash-from-signed-apk)
  - 아래의 코드를 입력하여 실행시키면 Log에서 SHA-1 으로 암호화하고 Base64로 인코딩된 해시키를 확인할 수 있다.

  ```java
  try {
      PackageInfo info = getPackageManager().getPackageInfo(
              getPackageName(), PackageManager.GET_SIGNATURES);
      for (Signature signature : info.signatures) {
          MessageDigest md = MessageDigest.getInstance("SHA");
          md.update(signature.toByteArray());
          Log.e("MY KEY HASH:",
                  Base64.encodeToString(md.digest(), Base64.DEFAULT));
      }
  } catch (NameNotFoundException e) {

  } catch (NoSuchAlgorithmException e) {

  }
  ```

  ### 5. Scroll 되는 View 한에 Map이 있을 시 스크롤 막기
  - MapView를 스크롤할 시 ScrollView가 터치가 되어 Map이 잘 움직이지 않는 현상을 막는다.
  - MapView 가 터치되면 `requestDisallowInterceptTouchEvent` 를 호출하여 parentView의 scroll 을 막는다.

  ```java
  private void setScrollPreventionByMap(){
      mapView.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
              switch (event.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                      v.getParent().requestDisallowInterceptTouchEvent(true);
                      break;
                  case MotionEvent.ACTION_UP:
                      v.getParent().requestDisallowInterceptTouchEvent(false);
                      break;
              }
              return false;
          }
      });
  }
  ```
