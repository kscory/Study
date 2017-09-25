# Persmission / BaseActivity
- Permission에 대해

---
## Permission
- 마시멜로우 버전 이상의 경우 Runtime Permission이 추가되어 코드에서 작성해주며 그 이하일 경우 Manifest에만 설정한 후 진행해야 한다.

  ### 1. Manifest에 권한 설정
  - Runtime Permission : 코드로 권한설정을 한번 더 체크 필요
  - Buildtime Permission : 코드로 권한설정 한번 더 체크 필요 X

  ```xml
  <!-- Runtime Permission -->
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  <!-- Buildtime Permission -->
  <uses-permission android:name="android.permission.INTERNET" />
  ```

  ### 2. 권한 여부 확인 및 요청
  - `checkSelfPermission()` : return type이 integer이며, 리턴값은 상수로 이미 정의되어 있다.
  - `PackageManager.PERMISSION_GRANTED` : 승인되었음을 알려준다.
  - `@TargetApi(Build.VERSION_CODES.M)` : complie 되라고 하는 에노테이션
  - 권한여부를 확인후 허용
  - 권한이 없으면 권한을 요청한다.

  ```java
  // 상수 정의
  private static final int REQ_CODE = 999;
  private static final String permissions[]={
          Manifest.permission.READ_EXTERNAL_STORAGE,
          Manifest.permission.WRITE_EXTERNAL_STORAGE
  };

  // # 1. 기본적인 방법
  @TargetApi(Build.VERSION_CODES.M)
  private void checkPermission(){
      // 1. 권한이 있는지 여부 확인
      if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
              checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
          // 진행 허용 처리
          init();

      } else{ // 2. 권한이 없으면 권한을 요청
          // 2.1 요청할 권한을 정의
          String permissions[]={
                  Manifest.permission.READ_EXTERNAL_STORAGE,
                  Manifest.permission.WRITE_EXTERNAL_STORAGE
          };
          // 2.2 권한 요청
          requestPermissions(permissions, REQ_CODE);
      }
  }  

  // # 2. 아래와 같이 List로 사용하면 유지보수하기 편하다.
  @TargetApi(Build.VERSION_CODES.M)
  private void requestPermission(Activity activity){
      // 3. 권한에 대한 승인 여부
      List<String> requires = new ArrayList<>();
      for(String permisson : permissions){
          if(activity.checkSelfPermission(permisson) != PackageManager.PERMISSION_GRANTED){
              requires.add(permisson);
          }
      }
      // 승인이 안된 권한이 있을 경우만 승인 요청
      if(requires.size()>0){
          String perms[] = requires.toArray(new String[requires.size()]);
          activity.requestPermissions(perms,req_code);
      }else{
          init();
      }
  }
  ```

  ### 3. 권한 승인 여부 체크
  - `onRequestPermissionsResult` : 사용자 처리후에 실행시킨다.
  - `PackageManager.PERMISSION_GRANTED` : 승인되었음을 알려준다.

  ```java
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      // 3. 권한 승인 여부 체크
      switch (requestCode){
          case REQ_CODE:
              if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                      && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                  // 진행 허용 처리
                  init();
              }
              break;
      }
  }  
  ```

  ### 4. 앱 버전 체크 (호환성 처리)
  - 마시멜로우 버전 이후에 생긴 기능이므로 그 이상 버전일 경우에만 퍼미션 체크를 해준다.

  ```java
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      // 앱 버전 체크 - 호환성 처리
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
          checkPermission();
      } else{
          init();
      }
  }  
  ```

---

## BaseActivity
- Activity 전체에서 반복적으로 실행되는 경우 이를 만들고 다른 Activity에서 상속한다.
- BaseActivity 생성 시 분리가 간편하며 Activity에서 사용되는 코드량이 줄어 보기 편해진다.
- 아래는 사용 예시!

  ### 1. BaseActivity
  - Activity에서 반복적으로 사용되는 코드를 모아논 class
  - 추상 클래스를 적용하여 `init()`을 반드시 실행시킨다.
  - `setContentView`는 MainActivity에서 실행시킨다.

  ```java
  public abstract class BaseActivity extends AppCompatActivity {

      private static final int REQ_CODE = 999;
      private static final String permissions[]={
              Manifest.permission.READ_EXTERNAL_STORAGE,
              Manifest.permission.WRITE_EXTERNAL_STORAGE
      };

      // 추상 변수 사용
      public abstract void init();

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
              checkPermission();
          } else{
              init();
          }
      }

      @TargetApi(Build.VERSION_CODES.M)
      private void checkPermission(){
          //
          if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                  checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
              init();

          } else{
              String permissions[]={
                      Manifest.permission.READ_EXTERNAL_STORAGE,
                      Manifest.permission.WRITE_EXTERNAL_STORAGE
              };
              requestPermissions(permissions, REQ_CODE);
          }
      }

      @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          // 3. 권한 승인 여부 체크
          switch (requestCode){
              case REQ_CODE:
                  if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                          && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                      init();
                  }
                  break;
          }
      }
  }
  ```

  ### 2. MainActivity
  - BaseActivity를 상속
  - setContentView 와 같은 필요한 코드 작성
  - init은 반드시 사용해야 함(인터페이스)

  ```java
  public class MainActivity extends BaseActivity {
      @Override
      public void init() {
          setContentView(R.layout.activity_main);
      }  
  ```

---

## 참고 자료
1. [런타임에 대한 권한요청](https://developer.android.com/training/permissions/requesting.html?hl=ko)

2. [시스템권한_퍼미션 종류 포함](https://developer.android.com/guide/topics/security/permissions.html?hl=ko)
