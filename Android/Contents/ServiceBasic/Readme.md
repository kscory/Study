# Service
  - 서비스에 대해
  - 서비스의 생명주기
  - 서비스 예시

---

## Service 개념
  ### 1. 서비스란
  - Service는 백그라운드에서 오래 실행되는 작업을 수행하게 하며 사용자 인터페이스를 제공하지 않는 애플리케이션 구성 요소
  - 서비스는 본질적으로 두 가지 형식을 취한다. (값을 주고받을 때는 bind서비스 그냥 시작할 때는 start를 주로 사용)
  - `StartService` :
    - 애플리케이션 구성 요소가 startService()를 호출하여 시작
    - 한 번 시작되고 나면 백그라운드에서 무기한으로 실행
  - `BindService` :
    - 애플리케이션 구성 요소가 bindService()를 호출하여 해당 서비스에 바인드되면 실행
    - 클라이언트-서버 인터페이스를 제공하여 구성 요소가 서비스와 상호작용할 수 있도록 함
    - . 여러 개의 구성 요소가 서비스에 한꺼번에 바인드될 수 있지만, 이 모든 것이 바인딩을 해제하면 해당 서비스는 소멸
  - But> 서비스는 두 가지 방식 모두로 작동 가능
  - ※ 주의사항
    - 서비스는 자신의 호스팅 프로세스의 기본 스레드에서 실행 (=> 서비스 내에 새 스레드를 생성하여 해당 작업을 수행해야 함)
    - 즉, 서비스에 무한루프를 돌리면 화면의 버튼클릭이 안되고, 엑티비티에 무한루프를 돌리면 서비스 실행이 안됨 (아래 예시에서 다시 설명)

  ### 2. 서비스 기본사항
  - `onStartCommand()` :  `startService()`를 호출하여 서비스를 시작하도록 요청하면 실행되는 메소드로  `stopSelf()` 또는 `stopService()`를 호출하면 중단
  - `onBind()` : `bindService()`를 호출하여 서비스를 바인드하도록 요청하면 실행되는 메소드,  클라이언트가 서비스와 통신을 주고받기 위해 사용할 인터페이스를 제공해야 하며, `unbindService()`를 호출하면 중단
  - `onCreate()` :  서비스가 처음 생성되어 일회성 설정 절차를 수행 (서비스가 이미 실행 중인 경우, 이 메서드는 호출되지 않음)
  - `onDestroy()` : 서비스를 더 이상 사용하지 않고 소멸 (서비스가 수신하는 마지막 호출)

  ### 3. Foreground
  -  서비스가 포그라운드에서 실행된다고 선언된 경우 Android 시스템이 서비스를 거의 강제 중단시키지 않음
  - `startForeground(Flag값, 알림바)` 로 호출 (flag로 찾아서 이를 stop 할 수 있음)
  - `stopForeground(true)` 호출시 서비스는 유지하면서 포그라운드 상태에서 해제되며 false로 하면 제거하지 않는다

  ### 4. 서비스의 생명주기
  - 서비스도 생명주기를 가진다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/ServiceBasic/picture/service_lifecycle.png)


---

## Service 사용 예시
  ### 1. MainActivity
  - 서비스를 실행시키는 엑티비티
  - intnet를 통해 실행시킨다.

  > MainActivity.java

  ```java
  public class MainActivity extends AppCompatActivity {
      Intent intent;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          intent  = new Intent(this,MyService.class);
      }

      // 서비스 시작
      public void start(View view){
          intent.setAction("START");
          startService(intent);
      }
      // 서비스 종료
      public void stop(View view){
          stopService(intent);
          // 항상 해주어야 한다.
          isService = false;
      }

      boolean isService = false;
      // 서비스를 담아두는 변수
      MyService service;
      // 서비스와의 연결 통로
      ServiceConnection con = new ServiceConnection() {
          // 서비스와 연결되는 순간 호출되는 함수
          @Override
          public void onServiceConnected(ComponentName name, IBinder iBinder) {
            // I가 있으면 Interface 따라서 서비스에서 Interface를 구현해야 함.
              isService = true;
              service = ((MyService.CustomBinder)iBinder).getService();
              Toast.makeText(MainActivity.this,"total= " + service.getTotal(),Toast.LENGTH_SHORT).show();
          }

          // 서비스가 중단되거나 연결이 도중에 끊겼을 때 발생한다.
          // 예) 정상적으로 stop이 호출되고, onDestroy가 발생하면 호출되지 않는다.ㅋㅋ;;
          @Override
          public void onServiceDisconnected(ComponentName name) {
              isService = false;
          }
      };

      public void bind(View view){
          bindService(intent, con, Context.BIND_AUTO_CREATE);
          // BindAutoCreate - bind가 없으면 생성, 있으면 불러옴. 거의 얘만 씀
      }

      public void unbind(View view){
          if(isService) {
              unbindService(con);
          }
          isService = false;
      }
  }

  ```

  ### 2. MyServiceExample
  - 서비스를 상속
  - 노티바 생성 등
  - 아래에서 무한루프에 Thread.sleep 을 하면 메인엑티비티의 버튼이 클릭되지 않는다.

  > MyServiceSample.java

  ```java
  public class MyServiceSample extends Service {
      public MyServiceSample() {
      }
      // 컴포넌트는 바인더를 통해 서비스에 접근할 수 있다.
      class CustomBinder extends Binder {
          public CustomBinder(){

          }
          public MyServiceSample getService(){
              return MyServiceSample.this;
          }
      }

      IBinder binder = new MyServiceSample.CustomBinder();

      @Override
      public IBinder onBind(Intent intent) {
          Log.d("MyService", "=====================onBind()");
          return binder;
      }

      @Override
      public boolean onUnbind(Intent intent) {
          Log.d("MyService", "=====================onUnbind()");
          return super.onUnbind(intent);
      }

      public int getTotal(){
          return total;
      }



      private int total=0;
      // 애너테이션 부분은 정해진 flag만 넣도록 쌓여져 있는데 아직 정상동작 안하는것 같음
      @Override
      public int onStartCommand(Intent intent, int flags, int startId) {
          // 포어 그라운드 서비스하기
          startForeground();

          Log.d("MyService", "=====================onStartCommand()");
          // Stop이 눌리지 않는다. 반복문이기 때문!!
          for(int i=0 ; i<1000 ; i++){
              total+=i;
              System.out.println("서비스에서 동작중입니다."+i);
  //            try {
  //                Thread.sleep(500);
  //            } catch (InterruptedException e) {
  //                e.printStackTrace();
  //            }
          }
          return super.onStartCommand(intent, flags, startId);
      }

      // 포어그라운드 서비스 번호
      public static final int FLAG = 17465;

      // 포어 그라운드 서비스하기
      // 포어그라운드 실행 메소드 구현
      private void startForeground(){
          // 포어그라운드 서비스에서 보여질 노티바 만들기
          NotificationCompat.Builder builder = new NotificationCompat.Builder(this); //혹은 getBaseContext

          Notification notification = builder
                  .setSmallIcon(R.mipmap.ic_launcher) // 아이콘
                  .setContentTitle("노티 타이틀") // 타이틀
                  .setContentText("노티 내용")  // 내용
                  .build();

          startForeground(FLAG,notification);

          // 노티바 노출시키기
          // 노티피케이션 매니저를 통해서 노티바를 출력
  //        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
  //        manager.notify(FLAG,notification);
      }

      private void stopForeground(){
          NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
          manager.cancel(FLAG);
      }


      @Override
      public void onCreate() {
          super.onCreate();
          Log.d("MyService", "=====================onCreate()");
      }

      @Override
      public void onDestroy() {
          super.onDestroy();
          stopForeground(true); // 포그라운드 상태에서 해제된다.서비스는 유지 // noti를 false로 하면 제거하지 않는다.
          Log.d("MyService", "=====================onDestroy()");
  //        stopForeground();
      }
  }
  ```

  ### 2. MyService
  - 포어그라운드 심화
  - 그 부분만 퍼옴 (`onStartCommand`, `setNotification`(startForeground) 메소드)

  > MyService.java

  ```java
  public int onStartCommand(Intent intent, int flags, int startId) {
      if(intent != null){
          String action = intent.getAction();
          switch(action){
              case "START":
                  setNotification("PAUSE");
                  break;
              case "PAUSE":
                  setNotification("START");
                  break;
              case "DELETE":
                  stopForeground(true);
                  break;
          }
      }
      return super.onStartCommand(intent, flags, startId);
  }

  // 포어그라운드 서비스 번호
  public static final int FLAG = 17465;

  // 포어 그라운드 서비스하기
  // 포어그라운드 실행 메소드 구현
  private void setNotification(String cmd){
      // 포어그라운드 서비스에서 보여질 노티바 만들기
      NotificationCompat.Builder builder = new NotificationCompat.Builder(this); //혹은 getBaseContext

      builder.setSmallIcon(R.mipmap.ic_launcher) // 스테이터스 바에 나타나는 아이콘
              .setContentTitle("음악제목") // 타이틀
              .setContentText("가수");  // 내용

      Bitmap icon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
      builder.setLargeIcon(icon); // 노티바에 나타나는 큰 아이콘 (icon release 필요)

      // 노티바 전체를 클릭했을 때 발생하는 액션 처리
      Intent deleteIntent = new Intent(getBaseContext(), MyService.class);
      deleteIntent.setAction("DELETE"); // intent.getAction에서 취하는 명령어
      PendingIntent mainIntent = PendingIntent.getService(getBaseContext() , 1 , deleteIntent, 0);
      builder.setContentIntent(mainIntent);

      /**
       * 노티에 나타나는 버튼 처리
      */
      // 클릭을 했을때 noti를 멈추는 명령어를 서비스에서 다시 받아서 처리
      // PendingIntent : noti를 클릭했지만 마치 Activity를 클릭한것 같은 효과를 줄 수 있다.
      Intent pauseIntent = new Intent(getBaseContext(), MyService.class);
      pauseIntent.setAction(cmd); // intent.getAction에서 취하는 명령어
      PendingIntent pendingIntent = PendingIntent.getService(getBaseContext() , 1 , pauseIntent, 0);
      // PendingIntent 생성시 마지막에 들어가는 Flag값
      /*
      FLAG_CANCEL_CURRENT : 이전에 생성한 PendingIntent 는 취소하고 새롭게 만든다.
      FLAG_NO_CREATE : 이미 생성된 PendingIntent 가 없다면 null 을 return 한다. 생성된 녀석이 있다면 그 PendingIntent 를 반환한다. 즉 재사용 전용이다.
      FLAG_ONE_SHOT : 이 flag 로 생성한 PendingIntent 는 일회용이다.
      FLAG_UPDATE_CURRENT : 이미 생성된 PendingIntent 가 존재하면 해당 Intent 의 Extra Data 만 변경한다.
       */

      // 노티피케이션에 들어가는 버튼을 만드는 명령
      int iconId = android.R.drawable.ic_media_pause;
      if(cmd.equals("START"))
          iconId=android.R.drawable.ic_media_play;
      String btnTitle = cmd;
      NotificationCompat.Action pauseAction
              = new NotificationCompat.Action.Builder(iconId,btnTitle,pendingIntent).build();
      builder.addAction(pauseAction);

      Notification notification = builder.build();

      startForeground(FLAG,notification);
  }
  ```

---

## 참고 자료

#### 1. [서비스](https://developer.android.com/guide/components/services.html?hl=ko)
#### 2. [바인드된 서비스](https://developer.android.com/guide/components/bound-services.html?hl=ko#Basics)
