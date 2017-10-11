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
  - ToggleButton을 이용해 start 및 stop
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
  ### 1. f
  - dd

  > MainActivity.java

  ```java

  ```

---

## 참고 자료

#### 1. [프로세스 및 스레드](https://developer.android.com/guide/components/processes-and-threads.html?hl=ko)
