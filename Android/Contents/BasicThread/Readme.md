# Thread
  - 스레드에 대한 간단한 개념
  - 간단한 사용 예제
  - [스레드 pdf]()

---

## 스레드란

  ### 1. 스레드란
  - Thread는 프로그램에서 실행되는 흐름의 단위
  - 일반적으로 하나의 Thread를 가지고 있지만, 환경에 따라 둘 이상의 Thread를 동시에 사용 가능(멀티쓰레드(multi thread)라고 한다.)
  - 즉, 쓰레드는 데이터 처리를 병렬로 하기 위해서 사용


  ### 2. 안드로이드의 스레드
  - 안드로이드는 mainthread가 한개 존재하며 subThread를 만들 수 있음
  - 안드로이드에서는 두 개 이상의 스레드를 사용할 때 동기화 이슈를 차단하기 위해서 Looper와 Handler, (Message)를 통해 접근한다.
  - 간단한 구조 그림

  </br>

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicThread/picture/thread.png)

  </br>


  ### 3. 스레드 사용시 주의
  - interrupt사용 시 Thread가 실시간 종료되지 않는다. (Flag를 이용할 것.)

---

## 사용예제1 (버튼 회전)
  ### 1. ThreadOneView (화면에 그려주며 쓰레드를 실행)
  - ToggleButton을 이용해 start 및 stop (interrupt 사용하지 않음)
  - Handler를 통해 Thread 실행
  - 반복문을 사용하며 FLAG 값을 이용해 반복문을 제어

  > ThreadOneView.java

  ```java
  public class ThreadOneView extends FrameLayout {

      Button rotObject;
      ToggleButton toggleRot;
      Rotator rotator;

      public static final int ACTION_SET = 999;

      public ThreadOneView(Context context) {
          super(context);
          initView();
          prcess();
      }

      private void initView(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.view_thread_one,null);
          rotObject = (Button) view.findViewById(R.id.rotObject);
          toggleRot = (ToggleButton) view.findViewById(R.id.toggleRot);
          addView(view);
      }

      private void prcess(){
          rotator = new Rotator(handler);
          // 참고 : rotater.setSeekbar(); // 이러면 mainthread에서 실행된다.
          toggleRot.setOnCheckedChangeListener(onCheckedChangeListener);
      }

      // 버튼을 변경하는 Hnadler 작성
      Handler handler = new Handler(){
          @Override
          public void handleMessage(Message msg) {
              //super.handleMessage(msg);
              switch (msg.what){
                  case ACTION_SET:
                      float curRot = rotObject.getRotation();
                      rotObject.setRotation(curRot + 6);
                      break;
              }
          }
      };

      // 토글버튼에 따라 회전 결정
      CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked){
                  rotator.start();
              } else{
                  rotator.setStop();
              }
          }
      };
  }
  ```

  ### 2. Rotator (Thread를 구현)
  - Thread를 상속하여 사용
  - run 메소드는 start가 호출되면 시작
  - `Message` 를 이용해 Handler로 메세지 전송
  - `Handler.sendEmptyMessage(int what)` : Message what(ID) 를 사용할 경우 사용 하는 메서드
  - `Handler.sendMessage(Message msg)` : Message what, arg1, obj 등 ID와 정보등을 같이 사용 하는 메서드
  - `Thread.sleep` 을 통해 Thread의 반복 시간 결정
  - FLAG값을 변경하여 반복문을 제어


  > Rotator.java

  ```java
  public class Rotator extends Thread {
      Handler handler;
      public static boolean ROTATINGFLAG = true;

      public Rotator(Handler handler){
          this.handler=handler;
      }
      // run 함수의 코드만 subThread에서 실행
      public void run(){
          ROTATINGFLAG = true;
          while(ROTATINGFLAG){
              // 핸들러 측으로 메세지 전송
              Message msg = new Message();
              msg.what = ThreadOneView.ACTION_SET;
              handler.sendMessage(msg);
              // 매초 버튼의 회전값을 변경
              try {
                  Thread.sleep(500);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }

      //run 이외의 함수는 subThread에서 실행되지 않는다.
      public void setStop(){
          ROTATINGFLAG = false;
      }
  }
  ```

---

## 사용예제2 (RainDrop)
  ### 1. ThreadTwoView
  - view를 연결
  - view의 업데이트 실행
  - 물체의 위치 변경 실행
  - viewpage가 옮겨질시 flag값 변경

  > ThreadTwoView.java

  ```java
  public class ThreadTwoView extends FrameLayout {
      FrameLayout stage;
      Button btnRain;
      RainDropView rainView;
      Context context;
      // 쓰레드는 Flag값을 static으로 선언하면 한번에 관리하기 편하다.
      public static boolean runFlag = true;
      public static boolean viewFlag = true;
      int width;
      int height;
      Random random = new Random();

      public ThreadTwoView(Context context) {
          super(context);
          this.context = context;

          initParameter();
          initView();
          setListener();

      }

      private void initParameter(){
          DisplayMetrics metrics = getResources().getDisplayMetrics();
          width = metrics.widthPixels;
          height = metrics.heightPixels;
      }

      private void initView(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.view_thread_two,null);
          stage = (FrameLayout) view.findViewById(R.id.stage);
          btnRain = (Button) view.findViewById(R.id.btnRain);

          rainView = new RainDropView(context); // 생성자까지 호출
          stage.addView(rainView); // addView가 되면 그 때 onDraw 호출

          addView(view);
      }

      private void setListener(){
          btnRain.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                  // 화면을 지속적으로 다시 그려준다.
                  if(viewFlag){
                      rainView.runStage();
                      viewFlag=false;
                  }
                  runFlag=true;
                  new Thread(){
                      public void run(){
                          while(runFlag){
                              int x = random.nextInt(width);
                              int y = 50 * -1;
                              int speed = random.nextInt(2)+1;
                              int size = random.nextInt(50)+10;
                              RainDrop rainDrop = new RainDrop(x,y,speed,size, Color.CYAN,height);
                              rainView.addRainDrop(rainDrop);

                              try {
                                  Thread.sleep(100);
                              } catch (InterruptedException e) {
                                  e.printStackTrace();
                              }
                          }
                      }
                  }.start();
              }
          });
      }

      public static void stopRain(){
          runFlag=false;
          viewFlag=true;
      }
  }
  ```

  ### 2. RainDrop 클래스
  - 물체의 속성값과 생명주기 관리
  - Thread를 상속받아 직접 떨어지는 모습 구현 가능

  > RainDrop.java

  ```java
  public class RainDrop extends Thread{
      // 속성
      float x;
      float y;
      float speed;
      float size;
      int color;

      // 생명주기 - 바닥에 닿을때까지
      float limit;

      public RainDrop(float x, float y, float speed, float size, int color, float limit){
          this.x = x;
          this.y = y;
          this.speed = speed;
          this.size = size;
          this.color = color;
          this.limit = limit;
      }
      // Thread를 상속받으면 run함수를 가질 수 있다.
  //    public void run(){
  //        while(y < limit){
  //            y += speed;
  //            try {
  //                Thread.sleep(100);
  //            } catch (InterruptedException e) {
  //                e.printStackTrace();
  //            }
  //        }
  //    }

  }

  ```

  ### 3. RainDropView (물체가 그려지는 view)
  - List의 싱크를 맞추기 위해 `CopyOnWriteArrayList` 사용
  - onDraw 메소드 구현
  - 반복문을 돌면서 전체 오브젝트의 좌표값을 갱신(생명주기 완료시 제거)
  - subThread에서 onDraw는 postInvalidate를 이용


  > RainDropView.java

  ```java
  public class RainDropView extends View{

      Paint paint;
      // 싱크를 맞춰주는 List
      List<RainDrop> rainDrops = new CopyOnWriteArrayList<>();

      public RainDropView(Context context) {
          super(context);

          //색 지정
          paint = new Paint();
          paint.setColor(Color.BLUE);
      }

      @Override
      protected void onDraw(Canvas canvas) {
          super.onDraw(canvas);
          if(rainDrops.size() >= 0){
              for(int i=0 ; i<rainDrops.size() ; i++){
                  RainDrop rainDrop = rainDrops.get(i);
                  paint.setColor(rainDrop.color);
                  canvas.drawCircle(rainDrop.x
                          , rainDrop.y
                          , rainDrop.size
                          , paint);
              }
          }
      }

      public void addRainDrop(RainDrop rainDrop){
          rainDrops.add(rainDrop);
          rainDrop.start();
      }

      public void runStage(){
          new Thread(){
              public void run(){
                  while(ThreadTwoView.runFlag){
                      // 반복문을 돌면서 전체 오브젝트의 좌표값을 갱신
                      for(int i=0 ; i<rainDrops.size() ; i++){
                          RainDrop rainDrop = rainDrops.get(i);
                          if(rainDrop.y < rainDrop.limit){
                              rainDrop.y += rainDrop.speed;
                          } else{
                              rainDrops.remove(rainDrop);
                              i--;
                          }
                      }
                      postInvalidate();
                      try {
                          Thread.sleep(10);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
              }
          }.start();
      }
  }
  ```
---
## 추가사항
  ### 1. runOnUiThread
  - runOnUiThread vs Handler
    - ` Handler` : post 방식을 통해 매번 이벤트를 발생
    - `runOnUiThread` : 현재 시점이 UI 스레드이면 바로 실행(=> mainThread에서 실행....조금 더 효율적)
  - 사용 예시 - [Mp3Player예제_작성중](ㅇ)
  - 사용 방법

  ```java
  new Thread(new Runnable() {
      @Override
      public void run() {
          for(i = 0; i<=100; i++) { // 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
              runOnUiThread(new Runnable() {
                  public void run() { // 메시지 큐에 저장될 메시지의 내용
                      textView.setText("runOnUiThread 을 통해 텍스트 설정");
                  }
              });
          }
      }
  }).start();
  ```

---

## 참고 자료

#### 1. [프로세스 및 스레드](https://developer.android.com/guide/components/processes-and-threads.html?hl=ko)

#### 2.[안드로이드 백그라운드 잘 다루기 Thread, Looper, Handler](https://academy.realm.io/kr/posts/android-thread-looper-handler/)
