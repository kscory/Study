# RxJava Basic3
  - PublishSubject
  - BehaviorSubject
  - ReplaySubject
  - AsyncSubject

---
## 간단한 설명

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RxBasic03/picture/subject.png)

---

## Subject의 종류 및 사용법
  ### 1. PublishSubject
  - 구독 시점부터 데이터를 읽을 수 있다.
  - 이전에 발행된 아이템은 읽을 수 없다.

  > PublishSubject

  ```java
  PublishSubject<String> publish = PublishSubject.create();
  public void doPublish(View view){
      new Thread(
          () -> {
              for(int i=0 ; i<10 ; i++) {
                  publish.onNext("(Publish)SOMETHING... " + i);
                  Log.e("Publish","=========================="+i);
                  try {
                      Thread.sleep(1000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }
      ).start();
  }
  // publish 에서 값 가져와서 세팅
  public void getPublish(View view){
      data.clear();
      publish
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(
              str -> {
                  data.add(str);
                  adapter.setDataAndRefresh(data);
              }
      );
  }
  ```

  ### 2. BehaviorSubject
  - 구독 이전에 발행된 마지막 발행된 아이템부터 구독할 수 있다.

  > BehaviorSubject

  ```java
  BehaviorSubject<String> behavior = BehaviorSubject.create();
  public void doBehavior(View view){
      new Thread(
              () -> {
                  for(int i=0 ; i<10 ; i++) {
                      behavior.onNext("(Behavior)SOMETHING... " + i);
                      Log.e("Behavior","=========================="+i);
                      try { Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
                  }
              }
      ).start();
  }
  public void getBehavior(View view){
      data.clear();
      behavior
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(
                      str -> {
                          data.add(str);
                          adapter.setDataAndRefresh(data);
                      }
              );
  }
  ```

  ### 3. ReplaySubject
  - 발행된 아이템을 모두 구독할 수 있다.

  > ReplaySubject

  ```java
  ReplaySubject<String> replay = ReplaySubject.create();
  public void doReplay(View view){
      new Thread(
              () -> {
                  for(int i=0 ; i<10 ; i++) {
                      replay.onNext("(Replay)SOMETHING... " + i);
                      Log.e("Replay","=========================="+i);
                      try { Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
                  }
              }
      ).start();
  }
  public void getReplay(View view){
      data.clear();
      replay
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(
                      str -> {
                          data.add(str);
                          adapter.setDataAndRefresh(data);
                      }
              );
  }
  ```

  ### 4. AsyncSubject
  - 발행이 완료된 시점에 마지막 데이터만 보여준다.

  > AsyncSubject

  ```java
  AsyncSubject<String> async = AsyncSubject.create();
  public void doAysnc(View view){
      new Thread(
              () -> {
                  for(int i=0 ; i<10 ; i++) {
                      async.onNext("(Async)SOMETHING... " + i);
                      Log.e("Async","=========================="+i);
                      try { Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
                  }
                  async.onComplete(); // Aysnc는 onComplete을 선언해야 구독이 가능
              }
      ).start();
  }
  public void getAysnc(View view){
      data.clear();
      async
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(
                      str -> {
                          data.add(str);
                          adapter.setDataAndRefresh(data);
                      }
              );
  }
  ```

---

## 참고 개념
  ### 1. 람다식 & Thread 사용
  - Thread를 사용할 때... 람다식으로 Runnable을 줄여서 사용한 것
  - Thread를 사용하여 비동기로 연산을 수행

  ```java

  /* 아래를 람다식으로 줄인 것
  new Thread(new Runnable() {
      @Override
      public void run() {

      }
  }).start();
  */

  new Thread(
      () -> {
          for(int i=0 ; i<10 ; i++) {
              publish.onNext("(Publish)SOMETHING... " + i);
              Log.e("Publish","=========================="+i);
              try {
                  Thread.sleep(1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
  ).start();
  ```
