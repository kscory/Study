# RxJava(RxAndroid) Basic1
  - RxJava 란
  - RxJava 사용법
  - [참고_RxJava pdf](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RxBasic01/pdf/035_RXJava.pdf)

---

## RxJava란
  ### 1. Observer 패턴에서 확장된 디자인 패턴
  - Rx = Observer + complete + error
  - `onNext(), onCompleted(), onError()` 로 구성 (onNext는 notify와 동일)

  ### 2. RxJava의 Main Players
  - Observable은 아이템들을 발행(emit)하고, Subscribers는 아이템들을 소비(consume)한다.
  - Observer로 어떤 이벤트를 Subscriber에 보내면 Subscriber는 그 이벤트들을 처리하는 방식으로 동작한다.
  - Subscriber는 Observable에서 보낸 아이템을 onNext()에서 처리하는데 처리가 끝나면 onComplete를 호출하고 중간에 에러가 발생하면 onError()를 호출하게된다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RxBasic01/picture/rx1.png)

  ### 3. Hot & Cold Observable of RxJava
  - Hot : 값을 중간에서 부터 시퀀스를 받아봄(수정 받영 가능 but> 불안정)
  - Cold : 값을 처음부터 세팅(중간에 서버 수정되어도 반영 불가능 but> 안정적)

  ### 4. RxJava Operator & RX Java Scheduler
  - [pdf참고](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RxBasic01/pdf/035_RXJava.pdf)
  - 주로 사용되는 Scheduler로는 Schedulers.io() / AndroidSchedulers.mainThread() 가 존재

---

## RxJava 사용법
  ### 1. 시작하기
  - gradle에 아래를 추가.

  ```java
  implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
  implementation 'io.reactivex.rxjava2:rxjava:2.1.5'
  ```

  - 참고로 람다식을 쓰기 위해서는 dependencies가 아닌 android에 아래를 추가시켜 준다. (java 1.8 버전이라는 것을 뜻함)

  ```java
  compileOptions{
      targetCompatibility 1.8
      sourceCompatibility 1.8
  }
  ```

  ### 2. Subject를 이용한 예제 (Subject <-> Observer)
  - Subject의 발행자를 생성한다.
  - 옵저버 패턴을 적용하여 변경사항을 반영한다.

  ```java
  public class MainActivity extends AppCompatActivity {

      Subject subject;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          // 발행자 생성 1. Observable, Subject
          subject = new Subject();
          subject.start();
      }

      // 구독자에 id를 부여하기 위한 코드
      int clientIdx = 0;
      // 위에 생성한 발행자에 클라이언트를 클릭할 때마다 등록한다.
      public void addObserver(View view){ // onClick 메서드
          Subject.Observer client = new Client(clientIdx++);
          subject.observers.add(client);
      }

      // 구독자
      class Client implements Subject.Observer{
          String name = "";
          public Client(int idx){
              name = "Client" + idx;
          }
          @Override
          public void notification(String msg) {
              Log.d(name, msg);
          }
      }
  }

  // Subject : 발행자 클래스
  class Subject extends Thread {
      // 옵저버 목록
      List<Observer> observers = new ArrayList<>();
      // 실행코드
      public void run(){
          int count = 0;
          while(true){
              try {
                  Thread.sleep(1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              for(Observer obs : observers){
                  obs.notification("Hello!!! = " + count++);
              }
          }
      }

      // 옵저버 인터페이스
      public interface Observer{
          public void notification(String msg);
      }
  }
  ```

  ### 3. Observable을 이용한 예제 (Observable <-> Subscriber)
  - `from`의 경우 데이터 배열을 넘길 수 있다.
  - `just`의 경우 안에 직접 데이터를 넣는다.
  - `defer`의 경우 Observable을 리턴한다.
  - 사용 방법은 거의 비슷하다.

  ```java
  public class MainActivity extends AppCompatActivity {

      RecyclerView recycler;
      CustomAdapter adapter;

      // 데이터 저장 변수
      List<String> months = new ArrayList<>();

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          recycler = findViewById(R.id.recycler);
          adapter = new CustomAdapter();
          recycler.setAdapter(adapter);
          recycler.setLayoutManager(new LinearLayoutManager(this));

          // 데이터 - 인터넷에서 순차적으로 가져오는 것
          final String data[] = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
          // 1. 발행자 생성 operator from
          Observable<String> observableFrom = Observable.fromArray(data);
          // 1.1. 구독자
          // 코드가 한줄일 때는 {}을 안써주어도 된다.
          // () 호출되는 콜백함수의 파라미터가 없을때 사용
          observableFrom.subscribe(
                  str -> months.add(str) , // onNext 호출
                  throwable -> {},         // onError 호출
                  () -> adapter.setDataAndRefresh(months) // onComplete 호출
          );

          // 2. just
          Observable<String> observableJust = Observable.just("JAN","FEB","MAR");
          observableJust.subscribe(str -> months.add(str));

          // 3. defer
          Observable<String> observableDefer = Observable.defer(new Callable<ObservableSource<? extends String>>() {
              @Override
              public ObservableSource<? extends String> call() throws Exception {
                  return Observable.just("JAN","FEB","MAR");
              }
          });
          observableDefer.subscribe(
                  str -> months.add(str),
                  throwable -> {},
                  () -> {adapter.setDataAndRefresh(months);}
          );
      }
  }
  ```

---
## 실제 사용 예시
  ### 1. 코드

  ### 2. 결과

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RxBasic01/picture/rx.gif)

---

## 참고

  ### 1. [ReactiveX 홈페이지](http://reactivex.io/)

  ### 2. [Observable 설명](http://reactivex.io/documentation/observable.html)

  ### 3. [RxAndroid Git](https://github.com/ReactiveX/RxAndroid)
